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

import top.pengcheng789.boring.annotation.Action;

/**
 * @author pen
 */
public final class CastUtil {

    /**
     * Transfer to 'Action.RequestMethod' from request method.
     */
    public static Action.RequestMethod stringToRequestMethod(String requestMethod) {
        switch (requestMethod.toUpperCase()){
            case "GET": return Action.RequestMethod.GET;
            case "POST": return Action.RequestMethod.POST;
            default: return null;
        }
    }

    public static String castString(Object object){
        return CastUtil.castString(object, "");
    }

    public static String castString(Object object, String defaultValue){
        return object != null ? String.valueOf(object) : defaultValue;
    }

    public static int castInt(Object object){
        return CastUtil.castInt(object, 0);
    }

    public static int castInt(Object object, int defaultValue){
        int intValue = defaultValue;
        if(object != null){
            String strValue = castString(object);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e){
                    intValue = defaultValue;
                }
            }
        }

        return intValue;
    }

    public static boolean castBoolean(Object object){
        return CastUtil.castBoolean(object, false);
    }

    public static boolean castBoolean(Object object, boolean defaultValue){
        boolean booleanValue = defaultValue;
        if(object != null){
            booleanValue = Boolean.parseBoolean(castString(object));
        }
        return booleanValue;
    }
}
