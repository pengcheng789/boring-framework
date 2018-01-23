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

package top.pengcheng789.boring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.boring.util.PropertyUtil;

import java.util.Properties;

/**
 * @author pen
 */
public class BoringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoringConfig.class);

    private static final Properties PROPERTIES
            = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    private static final String DEFAULT_APP_MONGODB_HOST = "127.0.0.1";
    private static final int DEFAULT_APP_MONGODB_PORT = 27017;

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

    /**
     * Return true if enable jdbc driver.
     */
    public static boolean isMongodbEnable() {
        return PropertyUtil.getBoolean(PROPERTIES, ConfigConstant.APP_MONGODB_ENABLE);
    }

    /**
     * Return the mongodb host.
     */
    public static String getAppMongoHost() {
        return PropertyUtil.getString(PROPERTIES,
            ConfigConstant.APP_MONGODB_HOST, DEFAULT_APP_MONGODB_HOST);
    }

    /**
     * Return the mongodb port.
     */
    public static int getAppMongoPort() {
        return PropertyUtil.getInt(PROPERTIES,
            ConfigConstant.APP_MONGODB_PORT, DEFAULT_APP_MONGODB_PORT);
    }

    /**
     * Return the name of specified database in mongodb.
     */
    public static String getAppMongoDatabase() {
        return PropertyUtil.getString(PROPERTIES, ConfigConstant.APP_MONGODB_DATABASE);
    }

    /**
     * Return the mongodb host.
     */
    public static String getAppMongoUsername() {
        return PropertyUtil.getString(PROPERTIES, ConfigConstant.APP_MONGODB_USERNAME);
    }


    /**
     * Return the mongodb password.
     */
    public static String getAppMongoPassword() {
        return PropertyUtil.getString(PROPERTIES, ConfigConstant.APP_MONGODB_PASSWORD);
    }
}
