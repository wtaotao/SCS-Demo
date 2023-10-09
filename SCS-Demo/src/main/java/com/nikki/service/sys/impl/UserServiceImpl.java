/**
 * nikki.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nikki.dto.sys.res.UserResDto;
import com.nikki.entity.UserEntity;
import com.nikki.mapper.UserMapper;
import com.nikki.service.sys.IUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: UserService.java, v 0.1 2023年9月15日 下午3:32:15 wangyanjun Exp $
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    
    /**
     * @param id
     * @return
     * @see com.nikki.service.sys.IUserService#selectUserById(java.lang.Integer)
     */
    @Override
//    public UserResDto selectUserById(Integer id) {
//        
//        log.info("Service selectUserById start. id={}", id);
//        UserEntity userEntity = userMapper.selectUserById(id);
//        log.info("Service selectUserById end. res={}", userEntity);
//        UserResDto userResDto = editResDto(userEntity);
//        return userResDto;
//    }
    public UserEntity selectUserById(Integer id) {
        
        log.info("Service selectUserById start. id={}", id);
        UserEntity userEntity = userMapper.selectUserById(id);
        log.info("Service selectUserById end. res={}", userEntity);
        return userEntity;
    }

    private UserResDto editResDto(UserEntity userEntity) {
        
        if(userEntity == null) return null;
        
        UserResDto userResDto = new UserResDto();
        userResDto.setId(userEntity.getId());
        userResDto.setName(userEntity.getName());
        userResDto.setAge(userEntity.getAge());
        userResDto.setSex(userEntity.getSex());
        userResDto.setUpdateTime(userEntity.getUpdateTime());
        userResDto.setFlag(userEntity.getFlag());
        return userResDto;
    }
}
