/**
 * anji-allways.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.anji.allways.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: UserEntity.java, v 0.1 2023年9月15日 下午2:28:39 wangyanjun Exp $
 */
public class UserEntity implements Serializable {
    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = 8766693667475925748L;

    /**
     * 用户编号
     */
    private Integer id;
    
    /**
     * 用户姓名
     */
    private String name;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 性别（0：男，1：女）
     */
    private Integer sex;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 数据是否有效(Y/N)
     */
    private String flag;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Integer getSex() {
        return sex;
    } 
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getFlag() {
        return flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
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
