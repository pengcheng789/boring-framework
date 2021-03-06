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

package top.pengcheng789.java.boring;

import top.pengcheng789.java.boring.util.ClassUtil;

import java.util.Set;

/**
 * @author pen
 */
public class ContainerInitializer {

    public static void init() {
        String packageName = "top.pengcheng789.java.boring.container";
        Set<Class<?>> classSet = ClassUtil.getClassSet(packageName);
        classSet.forEach(cls -> ClassUtil.loadClass(cls.getName()));
    }
}
