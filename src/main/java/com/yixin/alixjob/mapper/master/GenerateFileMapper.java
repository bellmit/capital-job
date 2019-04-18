package com.yixin.alixjob.mapper.master;

import java.util.List;

public interface GenerateFileMapper {

	List<String> selectBusiDataByIndex();

	List<String> selectRepayDataByIndex();
    
    int getBusinessDataTotalCount();
    
    int getRepayDataTotalCount();
    
    void deleteBusiData();
    
    void deleteRepayData();
    
    void insertBusiDataToTemp();
    
    void insertRepayDataToTemp();
}
