package com.yixin.alixjob.dto.taochepay;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class TaoChePayOrderLoanDto {

	private String orderId;//申请编号
	private String appId;//应用编号
	private String transId;//交易流水号
	private String productId;//产品ID
	private String productName;//产品名称
	private String dealerCity;//经销商城市
	private String entryOperator;//进件人员
	private String customerName;//客户姓名
	private String companyMainBody;//订单主体
	private BigDecimal aggregateAmount;//付款总额
	private String businessType;//业务类型
	private List<TaoChePayLoanItemDto> loanItems;//放款明细
	
}
