package top.pengcheng789.java.boring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.java.boring.util.PropertyUtil;
import top.pengcheng789.java.boring.util.StringUtil;

import java.util.Properties;

/**
 * @author pen
 */
public class BoringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoringConfig.class);

    private static final Properties PROPERTIES
            = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    /**
     * Return the value of 'boring.app.base_package'.
     */
    public static String getAppBasePackage() {
        String appBasePackage = PropertyUtil.getString(PROPERTIES, ConfigConstant.APP_BASE_PACKAGE);

        LOGGER.info("Set up the app base package as \'" + appBasePackage + "\'.");

        return appBasePackage;
    }

    /**
     * Return the html path.
     */
    public static String getAppHtmlPath() {
        String appHtmlPath =  PropertyUtil.getString(PROPERTIES,
                ConfigConstant.APP_HTML_PATH, "/WEB-INF/templates/");

        LOGGER.info("Set up the app html path as \'" + appHtmlPath + "\'.");

        return appHtmlPath;
    }
}
