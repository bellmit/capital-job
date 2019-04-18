package com.yixin.alixjob.dto.taoche;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class TaoCheRequestDto {
	@NotEmpty(message="请求订单不能为空")
	@Size(min=1,max=100,message="订单列表长度需要大于0小于等于100")
	private List<String> orderIds;
	
	@NotNull(message="订单数量不能为空")
	@Min(value=1,message="订单数量不能小于1")
	@Max(value=100,message="订单数量不能超过100")
	private BigDecimal orderCount;
	
	@NotBlank(message="应用编号不能为空")
	private String appId;
	
	@NotBlank(message="请求时间不能为空")
	@Length(min=14,max=14,message="请求时间长度不匹配")
	private String reqTime;
	
	@NotBlank(message="交易流水号不能为空")
	@Length(min=22,max=22,message="请求流水号长度不匹配")
	private String transId;
	

	private String sign;
	
	//请求类型
	private String reqType;
	
}
