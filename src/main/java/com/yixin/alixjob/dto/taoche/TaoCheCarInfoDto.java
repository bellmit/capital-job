package com.yixin.alixjob.dto.taoche;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TaoCheCarInfoDto {

	private String brandName;//品牌名称
	private String audiName;//车系名称
	private String motorcycleTypeName;//车型名称
	private BigDecimal salePrice;//实际销售价格
	private String carPurchasePurpose;//购车目的
	private BigDecimal usedCarKilometers;//二手车公里数（万）
	private BigDecimal usedCarEvaluationPrice;//提报评估价
	private String vin;//车架号（VIN码）
	private String productionDate;//二手车出厂日期
	private String recordDate;//二手车初登日期
	private String usedCarMAEP;//二手车评估机构
	private String carType;//车辆类型
	private BigDecimal usedCarAge;//二手车车龄

		
}
