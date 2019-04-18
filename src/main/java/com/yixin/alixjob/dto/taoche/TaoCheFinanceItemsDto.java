package com.yixin.alixjob.dto.taoche;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TaoCheFinanceItemsDto {
	private String itemNo;//融资项ID
	private String itemNum;//融资项编号
	private String itemName;//融资项名称
	private BigDecimal customerInvestment;//客户融资额
	private BigDecimal basePrice;//基础价
	private BigDecimal addPrice;//加价
	
}
