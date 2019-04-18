package com.yixin.alixjob.dto.taochepay;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TaoChePayLoanItemDto {

	private String receiptNumber;//收款编号
	private String loanItem;//放款项目
	private BigDecimal loanAmount;//放款金额
	private String payAccountName;//付款账户名称
	private String payBank;//付款银行
	private String payAccountNum;//付款账号
	private String receiptAccountName;//收款账户名
	private String receiptBank;//收款银行
	private String receiptAccountNum;//收款账号
	private String realPayTime;//实际放款时间

}
