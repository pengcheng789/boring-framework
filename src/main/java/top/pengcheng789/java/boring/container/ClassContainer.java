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

package top.pengcheng789.java.boring.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.java.boring.annotation.Bean;
import top.pengcheng789.java.boring.annotation.Controller;
import top.pengcheng789.java.boring.annotation.Service;
import top.pengcheng789.java.boring.config.BoringConfig;
import top.pengcheng789.java.boring.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Manage a set which container all classes.
 *
 * @author pen
 */
public class ClassContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassContainer.class);

    private static final Set<Class<?>> CLASS_SET
            = ClassUtil.getClassSet(BoringConfig.getAppBasePackage());

    static {
        LOGGER.info("Loaded \'" + ClassContainer.class.getCanonicalName() + "\'.");
    }

    /**
     * Get set of loaded classes.
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * Get set of controller classes.
     */
    public static Set<Class<?>> getControllerSet() {
        LOGGER.info("Getting the controller set ...");

        Set<Class<?>> classSet = new HashSet<>();

        CLASS_SET.forEach(cls -> {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        });

        LOGGER.info("Got the controller set.");
        return classSet;
    }

    /**
     * Get set of service classes.
     */
    public static Set<Class<?>> getServiceSet() {
        LOGGER.info("Getting the service set ...");

        Set<Class<?>> classSet = new HashSet<>();

        CLASS_SET.forEach(cls -> {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        });

        LOGGER.info("Got the service set.");
        return classSet;
    }

    /**
     * Get set of bean classes.
     */
    public static Set<Class<?>> getBeanSet() {
        LOGGER.info("Getting the bean set ...");

        Set<Class<?>> classSet = new HashSet<>();

        CLASS_SET.forEach(cls -> {
            if (cls.isAnnotationPresent(Controller.class)
                || cls.isAnnotationPresent(Service.class)
                || cls.isAnnotationPresent(Bean.class)) {
                classSet.add(cls);
            }
        });

        LOGGER.info("Got the bean set.");
        return classSet;
    }

}
