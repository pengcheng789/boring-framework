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
import top.pengcheng789.java.boring.annotation.Action;
import top.pengcheng789.java.boring.bean.Handle;
import top.pengcheng789.java.boring.bean.Request;
import top.pengcheng789.java.boring.exception.InvalidRequestPathException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manage a map of request and action method.
 *
 * @author pen
 */
public class ActionContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionContainer.class);

    private static final String REQUEST_PATH_REGULAR = "/[A-Za-z0-9_/]*";

    private static final Map<Request, Handle> ACTION_MAP = new HashMap<>();

    static {
        LOGGER.info("Initializing a map of request and action method.");

        Set<Class<?>> classSet = ClassContainer.getControllerSet();

        classSet.forEach(cls -> {
            Method[] methods = cls.getDeclaredMethods();
            Arrays.stream(methods).forEach(method -> addActionMap(new Handle(cls, method)));
        });

        LOGGER.info("Initialized a map of request and action method.");
    }

    /**
     * Get the 'Handle' according 'Request'.
     */
    public static Handle getHandle(Action.RequestMethod requestMethod, String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }

    /**
     * if the specify method is annotation present 'Action',
     * it will be put in the action map.
     */
    private static void addActionMap(Handle handle) {
        if (handle.getMethod().isAnnotationPresent(Action.class)) {
            Action action = handle.getMethod().getAnnotation(Action.class);

            if (!action.path().matches(REQUEST_PATH_REGULAR)) {
                LOGGER.error("\'" + action.path() + "\' is invalid request path!");
                throw new InvalidRequestPathException();
            }

            ACTION_MAP.put(new Request(action.method(), action.path()), handle);
        }
    }
}
