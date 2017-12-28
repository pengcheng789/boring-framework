package top.pengcheng789.java.boring.util;

import top.pengcheng789.java.boring.annotation.Action;

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
