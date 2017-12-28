package top.pengcheng789.java.boring.bean;

import top.pengcheng789.java.boring.util.CastUtil;
import top.pengcheng789.java.boring.util.CollectionUtil;
import top.pengcheng789.java.boring.util.StringUtil;

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
        Map<String, Object> fieldMap = new HashMap<String, Object>();

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
