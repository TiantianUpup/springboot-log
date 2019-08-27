package com.h2t.study.po;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * 基本PO字段
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/08/02 16:39
 */
@TableName(value = "base")  //@TableName为表名注解，value为数据表的名字
public class BasePO {
    //TableId为主键注解，value为字段值，type主键ID策略类型
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //使用@TableLogic注解实现逻辑删除
    @TableLogic
    @TableField(value = "deleted", exist = true)
    protected Integer deleted = 0;

    //@TableField为字段注解，value为字段值，exist是否为数据库表字段（默认true存在，false不存在）
    @TableField(value = "gmt_create", exist = true)
    protected Date gmtCreate;

    @TableField(value = "gmt_modified", exist = true)
    protected Date gmtModified;

    public BasePO() {
    }

    public BasePO(Long id, Integer deleted, Date gmtCreate, Date gmtModify) {
        this.id = id;
        this.deleted = deleted;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModify;
    }

    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "BasePO{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
