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
            createPropertyFileAndLoad(properties, fileName);
        }

        LOGGER.info("Loaded \'" + fileName + "\'.");
        return properties;
    }

    /**
     * Return the string type value of specified key.
     * If the key not existed, it will return empty string.
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
     * Return the boolean type value of specified key.
     * If the key not existed, it will return false.
     */
    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    /**
     * Return the boolean type value of specified key,
     * and you can specify the default value.
     */
    public static boolean getBoolean(Properties properties,
                                     String key, Boolean defaultValue) {
        return properties.contains(key) ?
            CastUtil.castBoolean(properties.get(key)) : defaultValue;
    }

    /**
     * Return the int type value of specified key,
     * If the key not existed, it will be return 0.
     */
    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    /**
     * Return the int type value of specified key,
     * and you can specify the default value.
     */
    public static int getInt(Properties properties, String key, int defaultValue) {
        return properties.contains(key) ?
            Integer.valueOf(properties.getProperty(key)) : defaultValue;
    }

    /**
     * Create a new file and load it.
     */
    private static void createPropertyFileAndLoad(Properties properties, String fileName) {
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
