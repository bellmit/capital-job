package com.yixin.alixjob.dto.smart;



import lombok.Data;
import lombok.EqualsAndHashCode;
//返回实体 是否可以催单查询
@Data
@EqualsAndHashCode(callSuper=false)
public class SmartQueryReminderResponseDto extends SmartResponseDto{
	private String reminderFlag;//是否可以催单（1：可以 0：不可以）
}
