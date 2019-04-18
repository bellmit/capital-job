package com.yixin.alixjob.utils.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InputContainer {
	
	/*
	 * 查询过滤条件列表
	 */
	private List<FilterClause> filterClauses = new ArrayList<FilterClause>();
	
	/*
	 * 排序条件列表
	 */
	private List<OrderClause> orderClauses = new ArrayList<OrderClause>();
	
	/*
	 * 单个查询对象
	 */
	private Object cVal;
	
	/*
	 * 格式化成Map格式的入参
	 */
	private Map<String,Object> inputMap;
	
	/**
	 * 
	 */
	public InputContainer(){
		
	}
	
	/**
	 * @param customVal
	 * @param filterClauses
	 * @param orderClauses
	 */
	public InputContainer(Object customVal,List<FilterClause> filterClauses,List<OrderClause> orderClauses){
		this.cVal = customVal;
		this.filterClauses = filterClauses;
		this.orderClauses = orderClauses;
		Map<String,Object> inputMap=new HashMap<String,Object>();
		inputMap.put("filterClausesList", listToMap(filterClauses));
		inputMap.put("orderClausesList", listToMap(orderClauses));
		inputMap.put("cVal", customVal);
		this.inputMap=inputMap;
	}

	public List<FilterClause> getFilterClauses() {
		return filterClauses;
	}

	public void setFilterClauses(List<FilterClause> filterClauses) {
		this.filterClauses = filterClauses;
	}

	public List<OrderClause> getOrderClauses() {
		return orderClauses;
	}

	public void setOrderClauses(List<OrderClause> orderClauses) {
		this.orderClauses = orderClauses;
	}

	public Object getcVal() {
		return cVal;
	}

	public void setcVal(Object cVal) {
		this.cVal = cVal;
	}
	
	public Map<String, Object> getInputMap() {
		return inputMap;
	}

	public void setInputMap(Map<String, Object> inputMap) {
		this.inputMap = inputMap;
	}

	/**
	 * 将List列表中的全部对象转换成Map对象(只转换private变量)
	 * @param inList 待转换的List列表
	 * @return 转换后的List列表
	 */
	public List<Map<String,Object>> listToMap(List<?> inList){
		List<Map<String,Object>> outList = new ArrayList<Map<String,Object>>();
		if(inList!=null&&inList.size()>0){
			for(Object obj:inList){
				Map<String,Object> filterClausesMap=objectToMap(obj);
				if(filterClausesMap!=null&&filterClausesMap.size()>0){
					outList.add(filterClausesMap);
				}
				
			}
		}
		return outList;
	}

	/**
	 * 将Object对象转换成Map对象(只转换private变量)
	 * @param obj 待转换的Object对象
	 * @return 转换后的Map对象
	 */
	public Map<String,Object> objectToMap(Object obj) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
	        Class<?> clazz =obj.getClass();
	        Field[] fields = clazz.getDeclaredFields();
	
	        for(Field field : fields){
	        	//private变量
	        	if(field.getModifiers()==2){
	        		field.setAccessible(true);
        			if(field.get(obj) instanceof List){
        				Type type = field.getGenericType();
        				 if(type instanceof ParameterizedType){
        					 	Class<?> clz=(Class<?>)((ParameterizedType) type).getActualTypeArguments()[0];
        					 	if(FilterClause.class.equals(clz.getName())){
        					 		map.put(field.getName(), listToMap((List<?>) field.get(obj)));
        					 	}else{
        					 		map.put(field.getName(), field.get(obj));
        					 	}
        		            }else{
        		            	map.put(field.getName(), field.get(obj));
        		            }
        			}else{
        				map.put(field.getName(), field.get(obj));
        			}
	        	}
	        }
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        return map;
	}
	
}
