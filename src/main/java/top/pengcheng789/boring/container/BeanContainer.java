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

package top.pengcheng789.boring.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.boring.annotation.Inject;
import top.pengcheng789.boring.bean.NullClass;
import top.pengcheng789.boring.exception.BeanInstanceNotFoundException;
import top.pengcheng789.boring.util.ArrayUtil;
import top.pengcheng789.boring.util.CollectionUtil;
import top.pengcheng789.boring.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manage a map of class and instance.
 *
 * @author pen
 */
public class BeanContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanContainer.class);

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        LOGGER.info("Initializing a map of class and instance ...");

        Set<Class<?>> classSet = ClassContainer.getBeanSet();
        classSet.forEach(BeanContainer::addBeanMap);

        LOGGER.info("Initialized a map of class and instance.");

        LOGGER.info("Injecting the values for beans ...");
        injectValue(BEAN_MAP);
        LOGGER.info("Injected the values for beans done.");
    }

    /**
     * Return the instance of class.
     */
    public static Object getBeanInstance(Class<?> cls) {
        return BEAN_MAP.get(cls);
    }

    /**
     * Instantiation a class and put in the 'BEAN_MAP' map.
     */
    private static void addBeanMap(Class<?> cls) {
        LOGGER.info("Adding \'" + cls.getCanonicalName() + "\' to the bean map.");
        BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
        LOGGER.info("Added \'" + cls.getCanonicalName() + "\' to the bean map.");
    }

    /**
     * Inject the value of field.
     */
    private static void injectValue(Map<Class<?>, Object> beanMap) {
        if (CollectionUtil.isEmpty(beanMap)) {
            return;
        }

        beanMap.entrySet().forEach(entry -> {
            Class<?> cls = entry.getKey();
            Object instance = entry.getValue();

            Field[] fields = cls.getDeclaredFields();
            if (ArrayUtil.isEmpty(fields)) {
                return;
            }

            Arrays.stream(fields).forEach(field -> injectValueForOne(instance, field));
        });
    }

    /**
     * Inject the value of field (just for one field).
     */
    private static void injectValueForOne(Object instance, Field field) {
        if (!field.isAnnotationPresent(Inject.class)) {
            return;
        }

        Inject injectAnnotation = field.getAnnotation(Inject.class);
        Class<?> fieldClass;
        if (injectAnnotation.type().equals(NullClass.class)) {
            fieldClass = field.getType();
        } else {
            fieldClass = injectAnnotation.type();
        }

        Object value = getBeanInstance(fieldClass);
        if (value == null) {
            LOGGER.error("Inject dependency value of \'"
                + field.getDeclaringClass() + "." + field.getName()
                + "\' failure! Not have the bean of \'"
                + fieldClass.getCanonicalName() + "\'!");
            throw new BeanInstanceNotFoundException("Not have the bean of \'"
                + fieldClass.getCanonicalName() + "\'.");
        }

        ReflectionUtil.setField(instance, field, value);
    }
}
