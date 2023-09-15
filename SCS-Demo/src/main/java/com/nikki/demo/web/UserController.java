/**
 * anji-allways.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.demo.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anji.allways.common.dto.ResponseData;
import com.anji.allways.dto.sys.res.UserResDto;
import com.anji.allways.entity.UserEntity;
import com.anji.allways.service.sys.IUserService;

import io.swagger.annotations.ApiParam;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: UserController.java, v 0.1 2023年9月15日 下午3:41:26 wangyanjun Exp $
 */
@RestController
public class UserController {

    @Resource
    IUserService userService;
    
    @RequestMapping("/user/query/{id}")
//    public ResponseData<UserResDto> queryUserById(@ApiParam(value = "编号", 
//    required = true)@PathVariable Integer id) {
//        UserResDto userResDto = userService.selectUserById(id);
//        return new ResponseData<UserResDto>(userResDto);
//    public ResponseData<UserEntity> queryUserById(@ApiParam(value = "编号", 
//            required = true)@PathVariable Integer id) {
//        UserEntity userResDto = userService.selectUserById(id);
//        return new ResponseData<UserEntity>(userResDto);
        public String queryUserById(@ApiParam(value = "编号", 
                required = true)@PathVariable Integer id) {
            UserEntity userResDto = userService.selectUserById(id);
            return userResDto.toString();
    }
   
    
}
