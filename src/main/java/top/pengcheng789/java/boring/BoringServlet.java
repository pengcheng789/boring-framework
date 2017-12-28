package top.pengcheng789.java.boring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import top.pengcheng789.java.boring.annotation.Action;
import top.pengcheng789.java.boring.bean.Data;
import top.pengcheng789.java.boring.bean.Handle;
import top.pengcheng789.java.boring.bean.Param;
import top.pengcheng789.java.boring.bean.View;
import top.pengcheng789.java.boring.config.BoringConfig;
import top.pengcheng789.java.boring.config.ConfigConstant;
import top.pengcheng789.java.boring.container.ActionContainer;
import top.pengcheng789.java.boring.container.BeanContainer;
import top.pengcheng789.java.boring.util.CastUtil;
import top.pengcheng789.java.boring.util.JsonUtil;
import top.pengcheng789.java.boring.util.ReflectionUtil;
import top.pengcheng789.java.boring.util.RequestUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * The core servlet dispatcher.
 *
 * @author pen
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class BoringServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoringServlet.class);

    private static final String SITE_ICON_REQUEST_PATH = "/favicon.ico";

    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println(ConfigConstant.TITLE_ASCII_ART);

        ContainerInitializer.init();

        ServletContext servletContext = servletConfig.getServletContext();
        templateEngine = configureTemplateResolver(servletContext);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        Action.RequestMethod requestMethod
                = CastUtil.stringToRequestMethod(request.getMethod());
        String requestPath = request.getPathInfo();

        if (requestPath == null) {
            requestPath = "/";
        }

        if (requestPath.equals(SITE_ICON_REQUEST_PATH)) {
            return;
        }

        Handle handle = ActionContainer.getHandle(requestMethod, requestPath);
        if (handle != null) {
            Method actionMethod = handle.getMethod();
            Class<?> controllerClass = handle.getControllerClass();
            Object controllerBean = BeanContainer.getBeanInstance(controllerClass);

            Param param = RequestUtil.createParam(request);
            Object result;
            // 若请求参数为空，则不需要传入参数
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean,
                        actionMethod, param);
            }

            if (result instanceof View) {
                processViewResult((View)result, request, response);
            } else if (result instanceof Data) {
                processDateResult((Data)result, response);
            }
        } else {
            // 没有匹配的 URL 路径时，返回404页面
            response.setStatus(404);
            PrintWriter writer = response.getWriter();
            writer.write("404");
            writer.flush();
            writer.close();
        }
    }

    /**
     * Configure the template resolver.
     */
    public TemplateEngine configureTemplateResolver(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver
                = new ServletContextTemplateResolver(servletContext);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(BoringConfig.getAppHtmlPath());
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    /**
     * Process the view.
     */
    private void processViewResult(View view, HttpServletRequest request,
                                   HttpServletResponse response)
            throws IOException {
        String path = view.getPath();

        if (path.startsWith("/")) {
            response.sendRedirect(request.getContextPath() + path);
            return;
        }

        WebContext webContext = new WebContext(request, response,
                request.getServletContext(), request.getLocale());
        webContext.setVariables(view.getModel());

        templateEngine.process(path, webContext, response.getWriter());
    }

    /**
     * Process the date
     */
    private void processDateResult(Data data, HttpServletResponse response) throws IOException{
        Object model = data.getModel();

        if (model != null) {
            response.setCharacterEncoding("utf-8");

            PrintWriter writer = response.getWriter();
            if (model instanceof String) {
                writer.write((String)model);
            } else {
                response.setContentType("application/json");
                writer.write(JsonUtil.toJson(model));
            }

            writer.flush();
            writer.close();
        }
    }
}
