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

package top.pengcheng789.boring.orm;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.boring.bean.FieldModel;
import top.pengcheng789.boring.bean.Model;
import top.pengcheng789.boring.container.ClassContainer;
import top.pengcheng789.boring.container.DbContainer;
import top.pengcheng789.boring.orm.exception.ModelDismatchException;
import top.pengcheng789.boring.orm.exception.ObjectNotBelongsToClassException;
import top.pengcheng789.boring.util.ReflectionUtil;
import top.pengcheng789.boring.util.StringUtil;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Cai Pengcheng
 * Create date: 18-1-12
 */
public final class CrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrudHelper.class);

    private static final MongoClient mongoClient = DbContainer.getMongoClient();
    private static final MongoDatabase mongoDatabase = DbContainer.getMongoDatabase();

    /**
     * 单行插入。
     */
    public static void insert(Class cls, Object obj) {
        checkObjectBelongsToClass(cls, obj);

        MongoCollection<Document> collection = mongoDatabase
                .getCollection(StringUtil.convertToDbTableName(cls.getSimpleName()));

        Document document = new Document();
    }

    /**
     * 检查参数obj是否为参数cls的实例。
     * 如果不是，则抛出ObjectNotBelongsToClassException异常。
     */
    private static void checkObjectBelongsToClass(Class cls, Object obj) {
        try {
            if (obj.getClass() != cls) {
                throw new ObjectNotBelongsToClassException("The \'" + obj.getClass().getCanonicalName()
                        + "\' and \'"
                        + cls.getCanonicalName()
                        + "\' are not the same.");
            }
        } catch (ObjectNotBelongsToClassException e) {
            LOGGER.error("Insert data to database failure.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理一般字段。
     * 返回该字段的值的字符串。
     */
    private String processNormalKey(FieldModel) {
        // TODO 处理一般字段。

        return "";
    }

    /**
     * 处理外键字段。
     * 如果外键字段的类型在模型中不存在，则抛出ModelDismatchException异常。
     * 如果符合则返回该对象的主键。
     */
    private String processForeignKey(FieldModel fieldModel, Object object) {
        Set<Class<?>> modelClassSet = ClassContainer.getModelSet();

        boolean flag = false;

        for (Class<?> cls : modelClassSet) {
            if (fieldModel.getClassType() == cls) {
                flag = true;
            }
        }

        if (!flag) {
            try {
                throw new ModelDismatchException("\'" + fieldModel.getClassType()
                        .getCanonicalName() + "\' is not a Model.");
            } catch (ModelDismatchException e) {
                LOGGER.error("\'" + fieldModel.getClassType()
                        .getCanonicalName() + "\' is not a Model.", e);
                throw new RuntimeException(e);
            }
        }

        Method method;
        try {
            method = object.getClass().getDeclaredMethod("get"
                    + StringUtil.convertToFirstUppercase(fieldModel.getName()));
        } catch (NoSuchMethodException e) {
            LOGGER.error("Can not find the getting method of \'" + fieldModel.getName() + "\'", e);
            throw new RuntimeException(e);
        }
        Object foreignField = ReflectionUtil.invokeMethod(object, method);

        Method method2;
        try {
            method2 = foreignField.getClass().getDeclaredMethod("getId");
        } catch (NoSuchMethodException e) {
            LOGGER.error("Can not find the getting method of \'getId\'", e);
            throw new RuntimeException(e);
        }

        return (String)ReflectionUtil.invokeMethod(foreignField, method2);
    }
}
