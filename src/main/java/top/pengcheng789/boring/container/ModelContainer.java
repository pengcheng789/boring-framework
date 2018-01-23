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
import top.pengcheng789.boring.annotation.ModelField.FieldType;
import top.pengcheng789.boring.bean.FieldModel;
import top.pengcheng789.boring.bean.Model;
import top.pengcheng789.boring.bean.TableModel;
import top.pengcheng789.boring.config.BoringConfig;
import top.pengcheng789.boring.util.StringUtil;

import java.util.*;

/**
 * @author Cai Pengcheng
 * Create date: 18-1-11
 */
@Deprecated
public class ModelContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelContainer.class);

    private static final Set<TableModel> APP_TABLE_SET = new HashSet<>();

    static {
        if (isNeedToInitialize()) {
            initAppTableSet();
            if (isEqual(APP_TABLE_SET, generateDbTableSet())) {
                // TODO: Initialize model container.
            }
        }
    }

    /**
     * If 'boring.app.jdbc.enable' is true in boring.config,
     * and the database connection is no problem, it will return true.
     */
    private static boolean isNeedToInitialize() {
        if (!BoringConfig.isJdbcEnable()) {
            LOGGER.info("The Jdbc Driver is not enable.");
            return false;
        }

        // TODO: Need to check the database connection.
        return false;
    }

    /**
     * Initialize a set of class which inherit by 'Model'.
     */
    private static void initAppTableSet() {
        Set<Class<?>> classSet = new HashSet<>();

        ClassContainer.getClassSet().forEach(cls -> {
            if (Model.class.isAssignableFrom(cls)) {
                classSet.add(cls);
            }
        });

        classSet.forEach(cls -> APP_TABLE_SET.add(generateAppTableModel(cls)));
    }

    /**
     * Generate a set of Model from database.
     */
    private static Set<TableModel> generateDbTableSet() {
        // TODO: Generate a set of Model from database.
        return null;
    }

    /**
     * Generate a TableModel from model class.
     */
    private static TableModel generateAppTableModel(Class<?> cls) {
        TableModel tableModel = new TableModel();
        tableModel.setName(StringUtil.convertToDbTableName(cls.getSimpleName()));

        List<FieldModel> primaryKeyList = new ArrayList<>();
        List<FieldModel> uniqueFieldList = new ArrayList<>();
        List<FieldModel> foreignFieldList = new ArrayList<>();
        List<FieldModel> normalFieldList = new ArrayList<>();

        Arrays.stream(cls.getDeclaredFields()).forEach(field -> {
            if (!field.isAnnotationPresent(ModelField.class)) {
                return;
            }

            ModelField modelFieldAnnotation = field.getAnnotation(ModelField.class);
            if (modelFieldAnnotation.type().equals(FieldType.PRIMARY)) {
                primaryKeyList.add(new FieldModel(field.getName(),
                    field.getType(), FieldType.PRIMARY));
                return;
            }

            if (modelFieldAnnotation.type().equals(FieldType.UNIQUE)) {
                uniqueFieldList.add(new FieldModel(field.getName(),
                    field.getType(), FieldType.UNIQUE));
                return;
            }

            if (modelFieldAnnotation.type().equals(FieldType.FOREIGN)) {
                foreignFieldList.add(new FieldModel(field.getName(),
                    field.getType(), FieldType.FOREIGN));
                return;
            }

            normalFieldList.add(new FieldModel(field.getName(),
                field.getType(), FieldType.NORMAL));
        });

        if (primaryKeyList.size() > 1) {
            primaryKeyList.forEach(primaryKey -> {
                if (!primaryKey.getName().equals("id")) {
                    tableModel.setPrimaryKey(primaryKey);
                }
            });
        }

        FieldModel[] arr = new FieldModel[foreignFieldList.size()];
        tableModel.setForeignFields(foreignFieldList.toArray(arr));

        arr = new FieldModel[uniqueFieldList.size()];
        tableModel.setUniqueFields(uniqueFieldList.toArray(arr));

        arr = new FieldModel[normalFieldList.size()];
        tableModel.setNormalFields(normalFieldList.toArray(arr));

        return tableModel;
    }

    /**
     * Check whether two 'Set<TableModel>' are equal.
     */
    private static boolean isEqual(Set<TableModel> set1, Set<TableModel> set2) {
        return set1.containsAll(set2) && set2.containsAll(set1);
    }
}
