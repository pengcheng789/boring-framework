/*
 * Copyright (c) 2018 Cai Pengcheng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
