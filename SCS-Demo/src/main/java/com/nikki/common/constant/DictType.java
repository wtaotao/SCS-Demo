/**
 * DictType.java
 * Created at 2020-03-20
 * Created by xieyingbin
 * Copyright (C) 2020 nikki, All rights reserved.
 */
package com.nikki.common.constant;

/**
 * 数据字典公共编码
 *
 * @author xyb
 */
public enum DictType {

    /**
     * 是或否状态 （0：否 1：是）
     */
    YN_STATUS("yesorno_status", "是或否状态"),

    /**
     * 删除状态 （0：未删除 1：删除）
     */
    DELETE_STATUS("delete_status", "删除状态"),

    /**
     * 有效状态 （1：生效，0：无效）
     */
    VALID_STATUS("valid_status", "有效状态"),

    /**
     * 用户类型
     */
    USER_TYPE("user_type", "用户类型"),

    /**
     * 用户类型
     */
    USER_STATUS("user_status", "用户状态"),

    /**
     * 角色状态
     */
    ROLE_STATUS("role_status", "角色状态"),

    /**
     * 权限类型
     */
    PERMISSION_TYPE("permission_type", "权限类型"),

    /**
     * 登录结果
     */
    LOGIN_RESULT("login_result","登录结果");

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典描述
     */
    private String msg;

    /**
     * 构造函数
     *
     * @param code 字典编码
     * @param msg  字典描述
     */
    private DictType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
