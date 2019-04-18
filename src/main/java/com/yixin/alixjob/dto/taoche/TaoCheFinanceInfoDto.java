package com.yixin.alixjob.dto.taoche;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TaoCheFinanceInfoDto {
	
	private BigDecimal financedAmount;//客户融资额
	private BigDecimal rent;//每期客户租金
	private String financedMaturity;//融资期限
	private BigDecimal DownPaymentRatio;//首付比例（%）
	private BigDecimal firstPayment;//首付金额
	private BigDecimal tailPaymentRatio;//尾付比例（%）
	private BigDecimal tailPayment;//尾付金额
	private String changeMoneyType;//手续费收取方式
	private BigDecimal changeMoney;//手续费
	private BigDecimal customerInterestRate;//客户利率
	private BigDecimal settleInterestRate;//结算利率
	private BigDecimal formalityRate;//手续费率
	private BigDecimal settleFormalityRate;//结算手续费率
	private BigDecimal totalInvestment;//投资总额
	private BigDecimal windControlFinance;//风控融资额
	private BigDecimal riskFinance;//风险融资额
	private BigDecimal thirdPartyFinance;//第三者责任险保费
	
	
}
