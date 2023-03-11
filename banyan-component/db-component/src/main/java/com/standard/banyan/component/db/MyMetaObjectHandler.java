package com.standard.banyan.component.db;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author jigang.duan
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String userName = "internal";
        this.strictInsertFill(metaObject, "createdBy", () -> userName, String.class);
        this.strictInsertFill(metaObject, "modifiedBy", () -> userName, String.class);
        this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "gmtModified", LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userName = "internal";
        this.setFieldValByName("modifiedBy", userName, metaObject);
        this.setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
    }
}
