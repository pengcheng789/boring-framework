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
import top.pengcheng789.boring.annotation.ModelField;
import top.pengcheng789.boring.bean.FieldModel;
import top.pengcheng789.boring.util.CollectionUtil;
import top.pengcheng789.boring.util.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Cai Pengcheng
 * Create Date: 2018-03-12
 */
public class MongoModelContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoModelContainer.class);

    /**
     * 获取每个Model的字段集合。
     */
    public static Map<Class<?>, List<FieldModel>> getModelMap() {
        LOGGER.info("Getting the model map ...");

        Set<Class<?>> modelSet = ClassContainer.getModelSet();

        if (CollectionUtil.isEmpty(modelSet)) {
            LOGGER.info("The project don't have any model, return null!");
            return null;
        }

        Map<Class<?>, List<FieldModel>> modelMap = new HashMap<>();
        modelSet.forEach(cls -> {
            LOGGER.info("Processing the model of \'" + cls.getCanonicalName() + "\' ...");
            modelMap.put(cls, getFieldModels(cls));
            LOGGER.info("Processed the model of \'" + cls.getCanonicalName() + "\'.");
        });

        LOGGER.info("Got the model map.");
        return modelMap;
    }

    /**
     * 返回一个Model的字段列表。
     */
    private static List<FieldModel> getFieldModels(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        List<FieldModel> fieldList = new ArrayList<>();

        Arrays.stream(fields).forEach(field -> {
            if (field.isAnnotationPresent(ModelField.class)) {
                fieldList.add(getFieldModel(field));
            }
        });

        return fieldList;
    }

    /**
     * 根据Field返回FieldModel。
     */
    private static FieldModel getFieldModel(Field field) {
        LOGGER.info("Creating a field model of \'" + field.getName() + "\' ...");

        String fieldName = StringUtil.convertToDbTableName(field.getName());
        Class<?> classType = field.getType();

        ModelField modelField = field.getAnnotation(ModelField.class);
        ModelField.FieldType fieldType = modelField.type();

        LOGGER.info("Created a field model of \'" + field.getName() + "\'.");
        return new FieldModel(fieldName, classType, fieldType);
    }
}
