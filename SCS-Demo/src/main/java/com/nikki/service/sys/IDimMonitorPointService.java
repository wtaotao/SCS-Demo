/**
 * IDimMonitorPointService.java
 * Created at 2022-11-16
 * Created by wangyanjun
 * Copyright (C) 2020 nikki, All rights reserved.
 **/
package com.nikki.service.sys;

import com.nikki.dto.sys.req.DimMonitorPointReqDto;
import com.nikki.dto.sys.res.DimMonitorPointResDto;

/**
 * 监控点维度表Service接口类
 * @author Jesse Wang  2022-11-16 14:27:26
 */
public interface IDimMonitorPointService {
    /**
     * 主键查询方法
     * 
     * @param pkId 主键
     * @return DimMonitorPointResDto
     */
    DimMonitorPointResDto queryByPrimaryKey(Integer monitorPointId);

    /**
     * 基础新增方法
     * 
     * @param reqDto 请求Dto
     * @return 新增数量
     */
    int createDimMonitorPoint(DimMonitorPointReqDto reqDto);

    /**
     * 主键更新方法
     * 
     * @param pkId 主键
     * @param reqDto 请求Dto
     * @return 更新数量
     */
    int updateByPrimaryKey(Integer monitorPointId, DimMonitorPointReqDto reqDto);

    /**
     * 主键删除方法
     * 
     * @param pkId 主键
     * @return 删除数量
     */
    int deleteByPrimaryKey(Integer monitorPointId);
}