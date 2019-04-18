package com.yixin.alixjob.utils.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * desc：过滤器
 * 
 * @author wlj7217
 *
 * @version 1.00.00
 */
public class FilterClause {

	//定义比较符
	public static final String COMPARATOR_LIKE_ALL = "LIKE_ALL"; //"%AAA%";
	
	public static final String COMPARATOR_LIKE_LEFT = "LIKE_LEFT"; //"AAA%"
	
	public static final String COMPARATOR_LIKE_RIGHT = "LIKE_RIGHT"; //"%AAA"
	
	public static final String COMPARATOR_LIKE_SELF = "LIKE_SELF";//"AAA"
	
	public static final String COMPARATOR_IN = "IN";
	
	public static final String COMPARATOR_NIN = "NOT IN";
	
	public static final String COMPARATOR_EQ = "=";
	
	public static final String COMPARATOR_LT = "<";
	
	public static final String COMPARATOR_LE = "<=";
	
	public static final String COMPARATOR_GT = ">";
	
	public static final String COMPARATOR_GE = ">=";
	
	public static final String COMPARATOR_NEQ = "!=";
	
	public static final String COMPARATOR_IS_NULL = "IS_NULL";
	
	public static final String COMPARATOR_IS_NOT_NULL = "IS_NOT_NULL";
	
	//定义类型
	public static final String TYPE_STRING = "string";
	
	public static final String TYPE_NUMBER = "number";
	
	public static final String TYPE_LIST = "list";
	
	//配合ibatis2.0使用
	public static final List<String> expandList=new ArrayList<String>(Arrays.asList(COMPARATOR_LIKE_ALL,COMPARATOR_LIKE_LEFT,COMPARATOR_LIKE_RIGHT,COMPARATOR_LIKE_SELF,COMPARATOR_IN,COMPARATOR_NIN,COMPARATOR_IS_NULL,COMPARATOR_IS_NOT_NULL));
	
	private Object value;
	
	private String comparator;
	
	private String colName;
	
	//配合ibatis2.0使用
	private int isExpand;
	
	private List<FilterClause> orList = new ArrayList<FilterClause>();
	
	/**
	 * 构造函数
	 * @param colName
	 * @param comparator
	 * @param value
	 * @param type
	 */
	public FilterClause(String colName,String comparator,Object value){
		this.colName = colName;
		this.value = value;
		this.comparator = comparator;
		this.isExpand=0;
		if(comparator!=null&&expandList.contains(comparator)){
			this.isExpand=1;
		}
		
	}
	
	/**
	 * 构造函数
	 * @param colName
	 * @param comparator
	 * @param value
	 * @param type
	 */
	public FilterClause(FilterClause condition1, FilterClause condition2){
		this.orList.add(condition1);
		this.orList.add(condition2);
	}
	
	/**
	 * 构造函数
	 * @param colName
	 * @param comparator
	 * @param value
	 * @param type
	 */
	public FilterClause(List<FilterClause> filterClauseList){
		this.orList.addAll(filterClauseList);
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return Returns the comparator.
	 */
	public String getComparator() {
		return comparator;
	}

	/**
	 * @return Returns the colName.
	 */
	public String getColName() {
		return colName;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getIsExpand() {
		return isExpand;
	}

	public void setIsExpand(int isExpand) {
		this.isExpand = isExpand;
	}

	public List<FilterClause> getOrList() {
		return orList;
	}

	public void setOrList(List<FilterClause> orList) {
		this.orList = orList;
	}
	
	
	
	
}
