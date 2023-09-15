/**
 * DimMonitorPointServiceImpl.java
 * Created at 2022-11-16
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.anji.allways.service.sys.impl;

import com.anji.allways.common.exception.BusiException;
import com.anji.allways.dto.sys.req.DimMonitorPointReqDto;
import com.anji.allways.dto.sys.res.DimMonitorPointResDto;
import com.anji.allways.entity.DimMonitorPointEntity;
import com.anji.allways.mapper.DimMonitorPointMapper;
import com.anji.allways.service.sys.IDimMonitorPointService;
import com.anji.allways.service.sys.helper.DimMonitorPointHelper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 监控点维度表Service实现类
 * @author wangyanjun  2022-11-16 14:27:26
 */
@Service
@Slf4j
public class DimMonitorPointServiceImpl implements IDimMonitorPointService, InitializingBean {
    /**
     * 监控点维度表Mapper
     */
    @Autowired
    private DimMonitorPointMapper dimMonitorPointMapper;

    /**
     * 监控点维度表Helper
     */
    @Autowired
    private DimMonitorPointHelper dimMonitorPointHelper;

    @Override
    public DimMonitorPointResDto queryByPrimaryKey(Integer monitorPointId) {
        log.info("Service selectByPrimaryKey start. primaryKey={}", monitorPointId);
        DimMonitorPointEntity entity = this.dimMonitorPointMapper.selectByPrimaryKey(monitorPointId);
        DimMonitorPointResDto resDto = this.dimMonitorPointHelper.editResDto(entity);
        log.info("Service selectByPrimaryKey end. res={}", resDto);
        return resDto;
    }

    @Override
    public int createDimMonitorPoint(DimMonitorPointReqDto reqDto) {
        log.info("Service createDimMonitorPoint start. reqDto={}", reqDto);
        DimMonitorPointEntity entity = this.dimMonitorPointHelper.editEntity(reqDto);
        // TODO 添加主键
        // entity.setPkId(UuidUtil.getUuid());
        // entity.setPkId(SnowflakeId.getSfid());
        int ret = this.dimMonitorPointMapper.insert(entity);
        log.info("Service createDimMonitorPoint end. ret={}", ret);
        return ret;
    }

    @Override
    public int updateByPrimaryKey(Integer monitorPointId, DimMonitorPointReqDto reqDto) {
        log.info("Service updateByPrimaryKey start. pkId={}, reqDto={}", "pkId", reqDto);
        if(monitorPointId == null) {
            throw new BusiException("主键参数为空");
        }
        DimMonitorPointEntity entity = this.dimMonitorPointHelper.editEntity(reqDto);
        entity.setMonitorPointId(monitorPointId);
        int ret = this.dimMonitorPointMapper.updateByPrimaryKey(entity);
        log.info("Service updateByPrimaryKey end. ret={}", ret);
        return ret;
    }

    @Override
    public int deleteByPrimaryKey(Integer monitorPointId) {
        log.info("Service deleteByPrimaryKey start. pkId={}", monitorPointId);
        if(monitorPointId == null) {
            throw new BusiException("主键参数为空");
        }
        int ret = this.dimMonitorPointMapper.deleteByPrimaryKey(monitorPointId); 
        log.info("Service deleteByPrimaryKey end. ret={}", ret);
        return ret;
    }

    /**
     * @throws Exception
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}