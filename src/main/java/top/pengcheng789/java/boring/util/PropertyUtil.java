package top.pengcheng789.java.boring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author pen
 */
public final class PropertyUtil {

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
            createPropersityFileAndLoad(properties, fileName);
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

    /**
     * Create a new file and load it.
     */
    private static void createPropersityFileAndLoad(Properties properties, String fileName) {
        LOGGER.info("Creating \'" + fileName + "\' ...");

        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("Create \'" + fileName + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Created \'" + fileName + "\'.");

        LOGGER.info("Loading \'" + fileName + "\' ...");
        try {
            properties.load(ClassUtil.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            LOGGER.error("Load \'" + fileName + "\' failure!");
            throw new RuntimeException(e);
        }
        LOGGER.info("Loaded \'" + fileName + ".");
    }
}
