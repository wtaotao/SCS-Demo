/**
 * PageInfoDto.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询Dto类
 * 
 * @author xieyingbin
 * 
 */
@Data
public class PageInfoDto {

	/**
	 * Field pageNum: 页码.
	 */
	@ApiModelProperty(value = "页码", required = true)
	private Integer pageNum;

	/**
	 * Field pageSize: 每页显示数量
	 */
	@ApiModelProperty(value = "每页显示数量", required = true)
	private Integer pageSize;

}