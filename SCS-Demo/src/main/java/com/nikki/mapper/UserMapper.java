/**
 * anji-allways.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.nikki.entity.UserEntity;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: UserMapper.java, v 0.1 2023年9月15日 下午3:14:03 wangyanjun Exp $
 */
@Mapper
@Repository
public interface UserMapper {
    //根据主键查询学生对象
    UserEntity selectUserById(Integer id);
}
