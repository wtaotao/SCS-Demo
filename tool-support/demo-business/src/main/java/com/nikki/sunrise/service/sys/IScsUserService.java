/**
 * IScsUserService.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.service.sys;

import com.nikki.sunrise.dto.sys.req.ScsUserReqDto;
import com.nikki.sunrise.dto.sys.res.ScsUserResDto;

/**
 * 用户表Service接口类
 * @author wangyanjun  2023-11-06 13:42:17
 */
public interface IScsUserService {
    /**
     * 主键查询方法
     * 
     * @param pkId 主键
     * @return ScsUserResDto
     */
    ScsUserResDto queryByPrimaryKey(Integer id);

    /**
     * 基础新增方法
     * 
     * @param reqDto 请求Dto
     * @return 新增数量
     */
    int createScsUser(ScsUserReqDto reqDto);

    /**
     * 主键更新方法
     * 
     * @param pkId 主键
     * @param reqDto 请求Dto
     * @return 更新数量
     */
    int updateByPrimaryKey(Integer id, ScsUserReqDto reqDto);

    /**
     * 主键删除方法
     * 
     * @param pkId 主键
     * @return 删除数量
     */
    int deleteByPrimaryKey(Integer id);
}