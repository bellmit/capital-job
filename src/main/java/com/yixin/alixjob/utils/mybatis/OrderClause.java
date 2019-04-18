package com.yixin.alixjob.utils.mybatis;

/**
 * desc：排序字段
 * 
 * @author wlj
 *
 * @version 1.00.00
 */
public class OrderClause {

	public static final String ORDER_DIR_DESC = "DESC";
	
	public static final String ORDER_DIR_ASC = "ASC";
	
	/*
	 * 排序方向
	 */
	private String orderDir;  //排序方向
	
	/*
	 * 排序属性
	 */
	private String orderAttr; //排序属性
	
	/**
	 * @param colName
	 * @param dir
	 */
	public OrderClause(String colName,String dir){
		this.orderAttr = colName;
		this.orderDir = dir;
	}

	/**
	 * @return Returns the orderDir.
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * @param orderDir The orderDir to set.
	 */
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	/**
	 * @return Returns the orderAttr.
	 */
	public String getOrderAttr() {
		return orderAttr;
	}

	/**
	 * @param orderAttr The orderAttr to set.
	 */
	public void setOrderAttr(String orderAttr) {
		this.orderAttr = orderAttr;
	}
	
	
}
