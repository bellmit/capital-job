package com.yixin.alixjob.dto.smart;


import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
//请求实体
@Data
public class SmartRequestDto {
	@NotBlank(message="申请编号不能为空")
	private String asqbh;
	
	@NotBlank(message="用户名不能为空")
	private String userName;

}
