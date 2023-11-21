/**
 * PageInfoDto.java
 * Created at 2021-08-26
 * Created by xyb
 * Copyright (C) 2020 nikki, All rights reserved.
 **/
package com.nikki.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询Dto类
 * 
 * @author xyb
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