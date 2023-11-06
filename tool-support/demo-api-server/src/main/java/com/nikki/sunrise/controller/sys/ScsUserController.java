/**
 * ScsUserController.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.controller.sys;

import com.nikki.sunrise.common.dto.ResponseData;
import com.nikki.sunrise.dto.sys.req.ScsUserReqDto;
import com.nikki.sunrise.dto.sys.res.ScsUserResDto;
import com.nikki.sunrise.service.sys.IScsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户表Controller实现类
 * @author wangyanjun  2023-11-06 13:42:17
 */
@RestController
@Slf4j
@Api(tags="用户表接口")
@RequestMapping(value = "/v1/ScsUserController")
public class ScsUserController {
    /**
     * 用户表Service
     */
    @Autowired
    private IScsUserService iScsUserService;

    /**
     * 主键查询方法
     * 
     * @param pkId 主键
     * @return ResponseData<ScsUserResDto>
     */
    @ApiOperation(value = "主键查询用户表")
    @GetMapping(value = "/{id}")
    public ResponseData<ScsUserResDto> queryByPrimaryKey(@PathVariable Integer id) {
        log.info("Controller queryByPrimaryKey start.");
        ScsUserResDto resDto =  this.iScsUserService.queryByPrimaryKey(id);
        log.info("Controller queryByPrimaryKey end.");
        return new ResponseData<>(resDto);
    }

    /**
     * 基础新增方法
     * 
     * @param reqDto 请求Dto
     * @return ResponseData<Integer>
     */
    @ApiOperation(value = "新增用户表")
    @PostMapping(value = "")
    public ResponseData<Integer> createScsUser(@RequestBody @Valid ScsUserReqDto reqDto) {
        log.info("Controller queryByPrimaryKey start.");
        int result = this.iScsUserService.createScsUser(reqDto);
        log.info("Controller queryByPrimaryKey end.");
        return new ResponseData<>(result);
    }

    /**
     * 主键更新方法
     * 
     * @param pkId 主键
     * @param reqDto 请求Dto
     * @return ResponseData<Integer>
     */
    @ApiOperation(value = "主键更新用户表")
    @PutMapping(value = "/{id}")
    public ResponseData<Integer> updateByPrimaryKey(@PathVariable Integer id, @RequestBody @Valid ScsUserReqDto reqDto) {
        log.info("Controller updateByPrimaryKey start.");
        int result = this.iScsUserService.updateByPrimaryKey(id, reqDto);
        log.info("Controller updateByPrimaryKey end.");
        return new ResponseData<>(result);
    }

    /**
     * 主键删除方法
     * 
     * @param pkId 主键
     * @return ResponseData<Integer>
     */
    @ApiOperation(value = "主键删除用户表")
    @DeleteMapping(value = "/{id}")
    public ResponseData<Integer> deleteByPrimaryKey(@PathVariable Integer id) {
        log.info("Controller deleteByPrimaryKey start.");
        int result = this.iScsUserService.deleteByPrimaryKey(id);
        log.info("Controller deleteByPrimaryKey end.");
        return new ResponseData<>(result);
    }
}