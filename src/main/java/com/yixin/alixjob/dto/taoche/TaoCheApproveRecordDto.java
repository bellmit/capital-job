package com.yixin.alixjob.dto.taoche;

import lombok.Data;

@Data
public class TaoCheApproveRecordDto {
	private String recordId;//审批记录ID
	private String nodeName;//审批节点名称
	private String checkOpinion;//审批意见
	private String checkRemark;//审批备注
	private String checkTime;//审批操作时间
		
}
