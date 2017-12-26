package top.pengcheng789.java.boring.helper;

import top.pengcheng789.java.boring.ConfigConstant;
import top.pengcheng789.java.boring.util.PropertyUtil;

import java.util.Properties;

/**
 * @author pen
 */
public class ConfigHelper {

    private static final Properties PROPERTIES
            = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    /**
     * Return the value of 'boring.app.base_package'
     */
    public static String getAppBasePackage() {
        return PropertyUtil.getString(PROPERTIES, ConfigConstant.APP_BASE_PACKAGE);
    }
}
