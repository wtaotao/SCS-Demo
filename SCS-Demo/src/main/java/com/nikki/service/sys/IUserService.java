/**
 * nikki.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.service.sys;

import com.nikki.dto.sys.res.UserResDto;
import com.nikki.entity.UserEntity;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Jesse Wang
 * @version $Id: IUserService.java, v 0.1 2023年9月15日 下午3:30:10 Jesse Wang Exp $
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
