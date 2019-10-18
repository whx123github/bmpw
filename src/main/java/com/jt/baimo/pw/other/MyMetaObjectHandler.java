package com.jt.baimo.pw.other;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充处理器
 * @author Forever丶诺
 * @since 2019-03-08
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        insertData(metaObject, "addTime", date);
        insertData(metaObject, "updateTime", date);
        insertData(metaObject, "version", 0);
        insertData(metaObject, "isDelete", 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        updateData(metaObject, "updateTime", new Date());
    }

    /**
     * 新增时填充的字段
     *  healthy
     * @param metaObject
     * @param fieldName  字段名,  不是数据库名  是Bean对象的属性
     * @param data
     */
    private void insertData(MetaObject metaObject, String fieldName, Object data) {
        if (getFieldValByName(fieldName, metaObject) == null) {
            setFieldValByName(fieldName, data, metaObject);
        }
    }

    /**
     * 更新时的字段更新
     * @param metaObject
     * @param fieldName
     * @param data
     */
    private void updateData(MetaObject metaObject, String fieldName, Object data) {
        setFieldValByName(fieldName, data, metaObject);
    }

}
