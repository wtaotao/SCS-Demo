/**
 * nikki.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.nikki.common.dto.ResponseData;
import com.nikki.dto.sys.res.DimMonitorPointResDto;
import com.nikki.entity.DimMonitorPointEntity;
import com.nikki.service.sys.impl.DimMonitorPointServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: DimMonitorPointController.java, v 0.1 2023年9月14日 下午3:31:35 wangyanjun Exp $
 */
@RestController
@Slf4j
@Api(tags="监控点")
@RequestMapping(value = "/dimmonitor")
public class DimMonitorPointController {
    
    @Autowired
    private DimMonitorPointServiceImpl dimMonitorPointService;
    
    /**
     * 查询监控点
     * 
     */
    @ApiOperation(value = "查询")
    @GetMapping(value = "/query/{id}")
    public ResponseData<DimMonitorPointResDto> queryDimMonitorPointById(@ApiParam(value = "编号", 
    required = true)@PathVariable Integer id){
        log.info("DimMonitorPointController queryDimMonitorPointById start. id={}", id);
        
        DimMonitorPointResDto dimMonitorPointResDto = this.dimMonitorPointService.queryByPrimaryKey(id);
        
        log.info("DimMonitorPointController queryDimMonitorPointById end. ");
        return new ResponseData<DimMonitorPointResDto>(dimMonitorPointResDto);
    }
}
