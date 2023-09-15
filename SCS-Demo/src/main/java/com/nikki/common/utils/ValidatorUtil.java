/**
 * ValidatorUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.nikki.common.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.nikki.common.exception.BusiException;

/**
 * 对象校验工具类
 * 
 * @author xieyingbin
 *
 */
public class ValidatorUtil {
	private static Validator validator;
	
	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public static void validateDto(Object obj,Class<?>... groups) {
		Set<ConstraintViolation<Object>> set = validator.validate(obj, groups);
		if(!set.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for(ConstraintViolation<Object> cv : set) {
				msg.append(cv.getMessage()).append(" ");
			}
			throw new BusiException(msg.toString());
		}
	}
}
