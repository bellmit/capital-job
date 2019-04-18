package com.yixin.alixjob.mapper.master.erp;

import com.yixin.alixjob.dto.erp.ERPBaseDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ERPMapper {

    List<Map<String,Object>> queryFailDataList();

    List<ERPBaseDTO> queryContractEffectByBatchNum(@Param("batchNum") String batchNum);

    List<ERPBaseDTO> queryContractCancelByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryContractNotEffectByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryContractTOOWNByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryLOANDFROMBANKByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryLOANFROMYIXINByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryREPAYPLANByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryTRANSFERCANCELByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryYXLOANDFROMBANKByBatchNum(@Param("batchNum")String batchNum);

    List<ERPBaseDTO> queryBSGUARANTEEFEEByBatchNum(String batchNum);

    List<ERPBaseDTO> queryXWPAYMENTByBatchNum(String batchNum);

    void updateResendStatus(List<Map<String, Object>> mapList);


}
