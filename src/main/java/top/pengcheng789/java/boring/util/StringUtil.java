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

import org.apache.commons.lang3.StringUtils;

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
}
