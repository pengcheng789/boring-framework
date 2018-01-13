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

package top.pengcheng789.boring.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author pen
 */
public final class StringUtil {

    /**
     * 字符串分隔符
     */
    public static final String SEPARATOR = String.valueOf((char)29);

    public static boolean isEmpty(String string){
        if(string != null){
            string = string.trim();
        }

        return StringUtils.isEmpty(string);
    }

    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }

    /**
     * 分割字符串
     */
    public static String[] splitString(String string, String separatorChars) {
        return StringUtils.split(string, separatorChars);
    }

    /**
     * Convert to a string with a database table format.
     *
     */
    public static String convertToDbTableName(String string) {
        char[] chars = string.toCharArray();
        StringBuilder builder = new StringBuilder("");

        for (char c : chars) {
            if (c >= 65 && c <= 90) {
                builder.append("_");
                builder.append((char)(c + 32));
            } else {
                builder.append(c);
            }
        }

        String result = builder.toString();
        return result.charAt(0) == '_' ?
            result.substring(1, result.length()) : result;
    }
}
