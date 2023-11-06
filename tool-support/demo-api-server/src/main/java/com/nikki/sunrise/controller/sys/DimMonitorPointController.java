/**
 * DimMonitorPointController.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.controller.sys;

import com.nikki.sunrise.common.dto.ResponseData;
import com.nikki.sunrise.dto.sys.req.DimMonitorPointReqDto;
import com.nikki.sunrise.dto.sys.res.DimMonitorPointResDto;
import com.nikki.sunrise.service.sys.IDimMonitorPointService;
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
 * 监控点维度表Controller实现类
 * @author wangyanjun  2023-11-06 13:45:49
 */
@RestController
@Slf4j
@Api(tags="监控点维度表接口")
@RequestMapping(value = "/v1/DimMonitorPointController")
public class DimMonitorPointController {
    /**
     * 监控点维度表Service
     */
    @Autowired
    private IDimMonitorPointService iDimMonitorPointService;

    /**
     * 主键查询方法
     * 
     * @param pkId 主键
     * @return ResponseData<DimMonitorPointResDto>
     */
    @ApiOperation(value = "主键查询监控点维度表")
    @GetMapping(value = "/{monitorPointId}")
    public ResponseData<DimMonitorPointResDto> queryByPrimaryKey(@PathVariable Integer monitorPointId) {
        log.info("Controller queryByPrimaryKey start.");
        DimMonitorPointResDto resDto =  this.iDimMonitorPointService.queryByPrimaryKey(monitorPointId);
        log.info("Controller queryByPrimaryKey end.");
        return new ResponseData<>(resDto);
    }

    /**
     * 基础新增方法
     * 
     * @param reqDto 请求Dto
     * @return ResponseData<Integer>
     */
    @ApiOperation(value = "新增监控点维度表")
    @PostMapping(value = "")
    public ResponseData<Integer> createDimMonitorPoint(@RequestBody @Valid DimMonitorPointReqDto reqDto) {
        log.info("Controller queryByPrimaryKey start.");
        int result = this.iDimMonitorPointService.createDimMonitorPoint(reqDto);
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
    @ApiOperation(value = "主键更新监控点维度表")
    @PutMapping(value = "/{monitorPointId}")
    public ResponseData<Integer> updateByPrimaryKey(@PathVariable Integer monitorPointId, @RequestBody @Valid DimMonitorPointReqDto reqDto) {
        log.info("Controller updateByPrimaryKey start.");
        int result = this.iDimMonitorPointService.updateByPrimaryKey(monitorPointId, reqDto);
        log.info("Controller updateByPrimaryKey end.");
        return new ResponseData<>(result);
    }

    /**
     * 主键删除方法
     * 
     * @param pkId 主键
     * @return ResponseData<Integer>
     */
    @ApiOperation(value = "主键删除监控点维度表")
    @DeleteMapping(value = "/{monitorPointId}")
    public ResponseData<Integer> deleteByPrimaryKey(@PathVariable Integer monitorPointId) {
        log.info("Controller deleteByPrimaryKey start.");
        int result = this.iDimMonitorPointService.deleteByPrimaryKey(monitorPointId);
        log.info("Controller deleteByPrimaryKey end.");
        return new ResponseData<>(result);
    }
}