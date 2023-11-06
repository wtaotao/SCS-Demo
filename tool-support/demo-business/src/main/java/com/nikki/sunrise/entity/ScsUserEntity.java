/**
 * ScsUserEntity.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author wangyanjun  2023-11-06
 */
public class ScsUserEntity implements Serializable {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别（0：男，1：女）
     */
    private Boolean sex;

    /**
     * 数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    private Date updateTime;

    /**
     * 数据有效标志(Y/N)
     */
    private String flag;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回字段:编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置字段值:编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 返回字段:姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段值:姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 返回字段:年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置字段值:年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 返回字段:性别（0：男，1：女）
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 设置字段值:性别（0：男，1：女）
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 返回字段:数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置字段值:数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 返回字段:数据有效标志(Y/N)
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置字段值:数据有效标志(Y/N)
     */
    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    /**
     * toString 
     * @return java.lang.String 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", age=").append(age);
        sb.append(", sex=").append(sex);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}