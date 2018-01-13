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

import top.pengcheng789.boring.util.CastUtil;
import top.pengcheng789.boring.util.CollectionUtil;
import top.pengcheng789.boring.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pen
 */
public class Param {

    private List<FormParam> formParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();

        if (CollectionUtil.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();

                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR
                            + fieldValue;
                }

                fieldMap.put(fieldName, fieldValue);
            }
        }

        return fieldMap;
    }

    /**
     * 验证参数是否为空
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(formParamList);
    }

    /**
     * 根据参数名获取 String 型参数
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }
}
