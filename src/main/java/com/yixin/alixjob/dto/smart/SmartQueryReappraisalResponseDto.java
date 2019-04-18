package com.yixin.alixjob.dto.smart;



import lombok.Data;
import lombok.EqualsAndHashCode;
//返回实体 是否可以复议查询
@Data
@EqualsAndHashCode(callSuper=false)
public class SmartQueryReappraisalResponseDto extends SmartResponseDto{
	private String reappraisalFlag;//是否可以复议（1：可以 0：不可以）
	private String reappraisalDesc;	//不可以复议的原因
}
