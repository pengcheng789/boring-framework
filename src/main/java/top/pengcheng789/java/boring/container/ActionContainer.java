package top.pengcheng789.java.boring.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.java.boring.annotation.Action;
import top.pengcheng789.java.boring.bean.Request;
import top.pengcheng789.java.boring.exception.InvalidRequestPathException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manage a map of request and action method.
 *
 * @author pen
 */
public class ActionContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionContainer.class);

    private static final String REQUEST_PATH_REGULAR = "/[A-Za-z0-9_/]*";

    private static final Map<Request, Method> ACTION_MAP = new HashMap<>();

    static {
        LOGGER.info("Initializing a map of request and action method.");

        Set<Class<?>> classSet = ClassContainer.getControllerSet();

        classSet.forEach(cls -> {
            Method[] methods = cls.getDeclaredMethods();
            Arrays.stream(methods).forEach(ActionContainer::addActionMap);
        });

        LOGGER.info("Initialized a map of request and action method.");
    }

    /**
     * Get the method according 'Request'.
     */
    public static Method getMethod(Action.RequestMethod requestMethod, String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }

    /**
     * if the specify method is annotation present 'Action',
     * it will be put in the action map.
     */
    private static void addActionMap(Method method) {
        if (method.isAnnotationPresent(Action.class)) {
            Action action = method.getAnnotation(Action.class);

            if (!action.path().matches(REQUEST_PATH_REGULAR)) {
                LOGGER.error("\'" + action.path() + "\' is invalid request path!");
                throw new InvalidRequestPathException();
            }

            ACTION_MAP.put(new Request(action.method(), action.path()), method);
        }
    }
}