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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import top.pengcheng789.boring.annotation.ModelField.FieldType;

/**
 * @author Cai Pengcheng
 * Create date: 18-1-13
 */
public class FieldModel {

    private FieldType fieldType;
    private String name;
    private Class<?> classType;

    public FieldModel(String name, Class<?> classType, FieldType fieldType) {
        this.name = name;
        this.classType = classType;
        this.fieldType = fieldType;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(name)
            .append(classType)
            .append(fieldType)
            .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if(obj instanceof FieldModel){
            final FieldModel other = (FieldModel) obj;
            return new EqualsBuilder()
                .append(name, other.name
                )
                .append(classType, other.classType)
                .append(fieldType, other.fieldType)
                .isEquals();
        } else{
            return false;
        }
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }
}
