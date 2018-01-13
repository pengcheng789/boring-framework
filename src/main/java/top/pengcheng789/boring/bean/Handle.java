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

package top.pengcheng789.boring.bean;

import java.lang.reflect.Method;

/**
 * Package the controller class and method of 'Action'.
 *
 * @author pen
 */
public class Handle {

    private Class<?> controllerClass;
    private Method method;

    public Handle(Class<?> cls, Method method) {
        this.controllerClass = cls;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
