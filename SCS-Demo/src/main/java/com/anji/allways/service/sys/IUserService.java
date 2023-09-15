/**
 * anji-allways.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.anji.allways.service.sys;

import com.anji.allways.dto.sys.res.UserResDto;
import com.anji.allways.entity.UserEntity;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: IUserService.java, v 0.1 2023年9月15日 下午3:30:10 wangyanjun Exp $
 */
public interface IUserService {
    /**
     * 
     * <pre>
     *  通过编号获取用户信息
     * </pre>
     *
     * @param id
     * @return
     */
//    UserResDto selectUserById(Integer id);
    UserEntity selectUserById(Integer id);
}
