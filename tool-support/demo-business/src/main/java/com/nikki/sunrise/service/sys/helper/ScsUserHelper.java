/**
 * ScsUserHelper.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.service.sys.helper;

import com.nikki.sunrise.dto.sys.req.ScsUserReqDto;
import com.nikki.sunrise.dto.sys.res.ScsUserResDto;
import com.nikki.sunrise.entity.ScsUserEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 用户表Service helper类
 * @author wangyanjun  2023-11-06 13:42:17
 */
@Component
public class ScsUserHelper {

    /**
     * Entity转换Dto方法
     * 
     * @param entity Entity对象
     * @return Dto
     */
    public ScsUserResDto editResDto(ScsUserEntity entity) {
        if (entity == null) {
            return null;
        }
        ScsUserResDto resDto = new ScsUserResDto();
        resDto.setId(entity.getId());
        resDto.setName(entity.getName());
        resDto.setAge(entity.getAge());
        resDto.setSex(entity.getSex());
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
    public List<ScsUserResDto> editResDtoList(List<ScsUserEntity> entityList) {
        List<ScsUserResDto> resDtoList = new ArrayList<>();
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
    public ScsUserEntity editEntity(ScsUserReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }
        ScsUserEntity entity = new ScsUserEntity();
        entity.setId(reqDto.getId());
        entity.setName(reqDto.getName());
        entity.setAge(reqDto.getAge());
        entity.setSex(reqDto.getSex());
        entity.setUpdateTime(reqDto.getUpdateTime());
        entity.setFlag(reqDto.getFlag());
        return entity;
    }

    /**
     * 用户表Service实现类
     * @author wangyanjun  2023-11-06 13:42:17
     */
    /**
     * Dto列表转换Entity列表方法
     * 
     * @param reqDtoList dto列表
     * @return Entity列表
     */
    public List<ScsUserEntity> editEntityList(List<ScsUserReqDto> reqDtoList) {
        List<ScsUserEntity> entityList = new ArrayList<>();
        if (reqDtoList == null || reqDtoList.isEmpty()){
            return entityList;
        }
        reqDtoList.forEach(reqDto -> {
            entityList.add(this.editEntity(reqDto));
        });
        return entityList;
    }
}