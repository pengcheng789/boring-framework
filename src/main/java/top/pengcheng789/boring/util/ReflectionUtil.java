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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * a tool to create a new instance or invoke method.
 *
 * @author pen
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * Create a new instance.
     */
    public static Object newInstance(Class<?> cls) {
        LOGGER.info("Creating a instance of \'" + cls.getCanonicalName() + "\' ...");

        Object instance;

        try {
            instance = cls.getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException
            | InvocationTargetException e) {

            LOGGER.error("Create a instance of \'" + cls.getCanonicalName() + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Created a instance of \'" + cls.getCanonicalName() + "\'.");
        return instance;
    }

    /**
     * Create a new instance according class name.
     */
    public static Object newInstance(String className) {
        Class<?> cls = ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * Invoke method.
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        LOGGER.info("Invoking \'" + method.getDeclaringClass() +  "." + method.getName()
                + "\' ...");

        Object result;

        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Invoke \'" + method.getDeclaringClass() +  "." + method.getName()
                + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Invoked \'" + method.getDeclaringClass() +  "." + method.getName()
            + "\'.");
        return result;
    }

    /**
     * Set the value of field.
     */
    public static void setField(Object obj, Field field, Object value) {
        LOGGER.info("Setting the value of \'"+ field.getDeclaringClass() + "."
            + field.getName() + "\' ...");

        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("Set the value of \'" + field.getDeclaringClass() + "."
                + field.getName() + "\' failure .", e);

            throw new RuntimeException(e);
        }

        LOGGER.info("Set the value of \'" + field.getDeclaringClass() + "."
            + field.getName() + "\' done.");
    }
}
