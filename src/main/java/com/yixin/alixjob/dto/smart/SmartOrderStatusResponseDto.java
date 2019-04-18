package com.yixin.alixjob.dto.smart;



import lombok.Data;
//返回实体 订单状态查询
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class SmartOrderStatusResponseDto extends SmartResponseDto{
	private String statusCode;//状态代码
	private String statusDesc;//状态描述
}
