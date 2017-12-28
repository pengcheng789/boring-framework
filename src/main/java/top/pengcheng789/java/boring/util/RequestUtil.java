package top.pengcheng789.java.boring.util;

import top.pengcheng789.java.boring.bean.FormParam;
import top.pengcheng789.java.boring.bean.Param;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author pen
 */
public final class RequestUtil {

    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();

        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));

        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        Enumeration<String> paramNames = request.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);

            if (ArrayUtil.isNotEmpty(fieldValues)) {
                Object fieldValue;

                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder sb = new StringBuilder("");

                    for (int i = 0; i < fieldValues.length; i++) {
                        sb.append(fieldValues[i]);

                        if (i != fieldValues.length - 1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }

                    fieldValue = sb.toString();
                }

                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }

        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request)
            throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        String body = CodecUtil.decodeURL(
                StreamUtil.getString(request.getInputStream())
        );

        if (StringUtil.isNotEmpty(body)) {
            String[] params = StringUtil.splitString(body, "&");

            if (ArrayUtil.isNotEmpty(params)) {
                for (String param : params) {
                    String[] array = StringUtil.splitString(param, "=");

                    if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new FormParam(fieldName, fieldValue));
                    }
                }
            }
        }

        return formParamList;
    }
}
