package top.pengcheng789.java.boring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author pen
 */
public class PropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * Load the properties file.
     */
    public static Properties loadProperties(String fileName) {
        LOGGER.info("Loading \'" + fileName + "\' ...");

        InputStream inputStream = ClassUtil.getClassLoader().getResourceAsStream(fileName);

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Load \'" + fileName + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Loaded \'" + fileName + "\'.");
        return properties;
    }

    /**
     * Return the string type value of specified key.
     * If the key not existed, it will be return empty string.
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    /**
     * Return the string type value of specified key,
     * and you can specify the default value.
     */
    public static String getString(Properties properties, String key, String defaultValue) {
        LOGGER.info("Load the value of \'" + key + "\'.");
        return properties.containsKey(key) ? properties.getProperty(key) : defaultValue;
    }
}
