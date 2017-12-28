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
