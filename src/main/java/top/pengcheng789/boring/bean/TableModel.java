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

/**
 * @author Cai Pengcheng
 * Create date: 18-1-13
 */
public class TableModel {

    private String name;
    private FieldModel primaryKey;
    private FieldModel[] uniqueFields;
    private FieldModel[] foreignFields;
    private FieldModel[] normalFields;

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(name)
            .append(primaryKey)
            .append(uniqueFields)
            .append(foreignFields)
            .append(normalFields)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TableModel){
            final TableModel other = (TableModel) obj;
            return new EqualsBuilder()
                .append(name, other.name)
                .append(primaryKey, other.primaryKey)
                .append(uniqueFields, other.uniqueFields)
                .append(foreignFields, other.foreignFields)
                .append(normalFields, other.normalFields)
                .isEquals();
        } else{
            return false;
        }
    }

    /**
     * Check whether two array are equal.
     */
    private static boolean isEquals(FieldModel[] arr1, FieldModel[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }

        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldModel getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(FieldModel primaryKey) {
        this.primaryKey = primaryKey;
    }

    public FieldModel[] getUniqueFields() {
        return uniqueFields;
    }

    public void setUniqueFields(FieldModel[] uniqueFields) {
        this.uniqueFields = uniqueFields;
    }

    public FieldModel[] getForeignFields() {
        return foreignFields;
    }

    public void setForeignFields(FieldModel[] foreignFields) {
        this.foreignFields = foreignFields;
    }

    public FieldModel[] getNormalFields() {
        return normalFields;
    }

    public void setNormalFields(FieldModel[] normalFields) {
        this.normalFields = normalFields;
    }
}
