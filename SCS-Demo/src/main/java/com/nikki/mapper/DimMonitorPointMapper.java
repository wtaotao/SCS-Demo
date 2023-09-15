 /**
 * DimMonitorPointMapper.java
 * Created at 2022-11-16
 * Created by mybatis generator
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.nikki.common.base.IBaseMapper;
import com.nikki.entity.DimMonitorPointEntity;
import com.nikki.entity.DimMonitorPointEntityExample;

 /**
 * ClassName: DimMonitorPointMapper
 * Description: dim_monitor_point dao class
 * Author: mybatis generator
 * Date: 2022-11-16
 **/
@Mapper
@Repository
public interface DimMonitorPointMapper extends IBaseMapper<DimMonitorPointEntity, DimMonitorPointEntityExample, Integer> {
    /** 
     * 
     * <pre>
     * 根据编号查询监控点
     * </pre>
     *
     * @param id
     * @return
     */
    DimMonitorPointEntity selectByPrimaryKey(Integer monitorPointId);
//    default public DimMonitorPointEntity selectByPrimaryKey(Integer id) {
//        DimMonitorPointEntity dimMonitorPointEntity = this.selectByPrimaryKey(id);
//        return dimMonitorPointEntity;
//    }
}