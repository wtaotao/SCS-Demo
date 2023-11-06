/**
 * ScsUserServiceImpl.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.service.sys.impl;

import com.nikki.sunrise.common.exception.BusiException;
import com.nikki.sunrise.dto.sys.req.ScsUserReqDto;
import com.nikki.sunrise.dto.sys.res.ScsUserResDto;
import com.nikki.sunrise.entity.ScsUserEntity;
import com.nikki.sunrise.mapper.ScsUserMapper;
import com.nikki.sunrise.service.sys.IScsUserService;
import com.nikki.sunrise.service.sys.helper.ScsUserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户表Service实现类
 * @author wangyanjun  2023-11-06 13:42:17
 */
@Service
@Slf4j
public class ScsUserServiceImpl implements IScsUserService {
    /**
     * 用户表Mapper
     */
    @Autowired
    private ScsUserMapper scsUserMapper;

    /**
     * 用户表Helper
     */
    @Autowired
    private ScsUserHelper scsUserHelper;

    @Override
    public ScsUserResDto queryByPrimaryKey(Integer id) {
        log.info("Service selectByPrimaryKey start. primaryKey={}", id);
        ScsUserEntity entity = this.scsUserMapper.selectByPrimaryKey(id);
        ScsUserResDto resDto = this.scsUserHelper.editResDto(entity);
        log.info("Service selectByPrimaryKey end. res={}", resDto);
        return resDto;
    }

    @Override
    public int createScsUser(ScsUserReqDto reqDto) {
        log.info("Service createScsUser start. reqDto={}", reqDto);
        ScsUserEntity entity = this.scsUserHelper.editEntity(reqDto);
        // TODO 添加主键
        // entity.setPkId(UuidUtil.getUuid());
        // entity.setPkId(SnowflakeId.getSfid());
        int ret = this.scsUserMapper.insert(entity);
        log.info("Service createScsUser end. ret={}", ret);
        return ret;
    }

    @Override
    public int updateByPrimaryKey(Integer id, ScsUserReqDto reqDto) {
        log.info("Service updateByPrimaryKey start. pkId={}, reqDto={}", pkId, reqDto);
        if(id == null) {
            throw new BusiException("主键参数为空");
        }
        ScsUserEntity entity = this.scsUserHelper.editEntity(reqDto);
        entity.setId(id);
        int ret = this.scsUserMapper.updateByPrimaryKey(entity);
        log.info("Service updateByPrimaryKey end. ret={}", ret);
        return ret;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        log.info("Service deleteByPrimaryKey start. pkId={}", id);
        if(id == null) {
            throw new BusiException("主键参数为空");
        }
        int ret = this.scsUserMapper.deleteByPrimaryKey(id); 
        log.info("Service deleteByPrimaryKey end. ret={}", ret);
        return ret;
    }
}