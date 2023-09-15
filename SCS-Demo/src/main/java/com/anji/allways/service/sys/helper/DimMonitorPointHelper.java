/**
 * DimMonitorPointHelper.java
 * Created at 2022-11-16
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.anji.allways.service.sys.helper;

import com.anji.allways.dto.sys.req.DimMonitorPointReqDto;
import com.anji.allways.dto.sys.res.DimMonitorPointResDto;
import com.anji.allways.entity.DimMonitorPointEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 监控点维度表Service helper类
 * @author wangyanjun  2022-11-16 14:27:26
 */
@Component
public class DimMonitorPointHelper {

    /**
     * Entity转换Dto方法
     * 
     * @param entity Entity对象
     * @return Dto
     */
    public DimMonitorPointResDto editResDto(DimMonitorPointEntity entity) {
        if (entity == null) {
            return null;
        }
        DimMonitorPointResDto resDto = new DimMonitorPointResDto();
        resDto.setMonitorPointId(entity.getMonitorPointId());
        resDto.setMonitorPointName(entity.getMonitorPointName());
        resDto.setMonitorPointAmt(entity.getMonitorPointAmt());
        resDto.setMonitorPointRiskGrade(entity.getMonitorPointRiskGrade());
        resDto.setUpdateTime(entity.getUpdateTime());
        resDto.setFlag(entity.getFlag());
        return resDto;
    }

    /**
     * Entity列表转换Dto列表方法
     * 
     * @param entityList Dto列表
     * @return Dto列表
     */
    public List<DimMonitorPointResDto> editResDtoList(List<DimMonitorPointEntity> entityList) {
        List<DimMonitorPointResDto> resDtoList = new ArrayList<>();
        if (entityList == null || entityList.isEmpty()){
            return resDtoList;
        }
        entityList.forEach(entity -> {
            resDtoList.add(this.editResDto(entity));
        });
        return resDtoList;
    }

    /**
     * Dto转换Entity方法
     * 
     * @param reqDto Dto对象
     * @return Entity
     */
    public DimMonitorPointEntity editEntity(DimMonitorPointReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }
        DimMonitorPointEntity entity = new DimMonitorPointEntity();
//        entity.setMonitorPointId(reqDto.getMonitorPointId());
        entity.setMonitorPointName(reqDto.getMonitorPointName());
        entity.setMonitorPointAmt(reqDto.getMonitorPointAmt());
        entity.setMonitorPointRiskGrade(reqDto.getMonitorPointRiskGrade());
        entity.setUpdateTime(reqDto.getUpdateTime());
        entity.setFlag(reqDto.getFlag());
        return entity;
    }

    /**
     * 监控点维度表Service实现类
     * @author wangyanjun  2022-11-16 14:27:26
     */
    /**
     * Dto列表转换Entity列表方法
     * 
     * @param reqDtoList dto列表
     * @return Entity列表
     */
    public List<DimMonitorPointEntity> editEntityList(List<DimMonitorPointReqDto> reqDtoList) {
        List<DimMonitorPointEntity> entityList = new ArrayList<>();
        if (reqDtoList == null || reqDtoList.isEmpty()){
            return entityList;
        }
        reqDtoList.forEach(reqDto -> {
            entityList.add(this.editEntity(reqDto));
        });
        return entityList;
    }
}