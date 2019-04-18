package com.yixin.alixjob.service.erp.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yixin.alixjob.common.ERPContstants;
import com.yixin.alixjob.dto.erp.ERPBaseDTO;
import com.yixin.alixjob.mapper.master.erp.ERPMapper;
import com.yixin.alixjob.service.erp.ERPService;
import com.yixin.alixjob.utils.http.HttpResult;
import com.yixin.alixjob.utils.http.OKHttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ERPServiceImpl implements ERPService {

    @Resource
    private ERPMapper mapper;

    @Value("${erp.url}")
    private String ERPURL;

    @Value("${erp.querystatus.url}")
    private String erpQueryStatusUrl;

    @Override
    public void resendFailData() {
        List<Map<String,Object>> mapList = mapper.queryFailDataList();
        HttpResult httpResult = new HttpResult();
        for(Map<String,Object> map:mapList){
            String batchNum = (String) map.get("batchNum");
            String interfaceName = (String) map.get("interfaceName");
            map.put("sourceSystem",ERPContstants.SOURCESYSTEM_ALIX);
            String queryStatus = this.queryERPStatus(map);
            if("S".equals(queryStatus)){
                //判断状态为S不重发
                map.put("status",queryStatus);
                continue;
            }
            switch (interfaceName) {
                case ERPContstants.INTERFACECODE_CONTRACTEFFECT:
                    httpResult = this.resendContractEffect(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_CONTRACTCANCEL:
                    httpResult = this.resendContractCancel(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_CONTRACTNOTEFFECT:
                    httpResult = this.resendContractNotEffect(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_CONTRACTTOOWN:
                    httpResult = this.resendContractTOOWN(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_LOANDFROMBANK:
                    httpResult = this.resendLOANDFROMBANK(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_LOANFROMYIXIN:
                    httpResult = this.resendLOANFROMYIXIN(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_REPAYPLAN:
                    httpResult = this.resendREPAYPLAN(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_TRANSFERCANCEL:
                    httpResult = this.resendTRANSFERCANCEL(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_YXLOANDFROMBANK:
                    httpResult = this.resendYXLOANDFROMBANK(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_BSGUARANTEEFEE:
                    httpResult = this.resendBSGUARANTEEFEE(batchNum);
                    break;
                case ERPContstants.INTERFACECODE_XWPAYMENT:
                    httpResult = this.resendXWPAYMENT(batchNum);
                    break;
                default:
                    break;
            }
            String status = this.handleResponse(httpResult);
            map.put("status",status);
        }
        if(mapList.size()>0){
            mapper.updateResendStatus(mapList);
        }
    }

    private HttpResult resendXWPAYMENT(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryXWPAYMENTByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendBSGUARANTEEFEE(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryBSGUARANTEEFEEByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private String queryERPStatus(Map<String, Object> map) {
        HttpResult httpResult = OKHttpUtils.doPostJSON(erpQueryStatusUrl,map);
        return this.handleResponse1(httpResult);
    }

    private HttpResult resendYXLOANDFROMBANK(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryYXLOANDFROMBANKByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendTRANSFERCANCEL(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryTRANSFERCANCELByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendREPAYPLAN(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryREPAYPLANByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendLOANFROMYIXIN(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryLOANFROMYIXINByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendLOANDFROMBANK(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryLOANDFROMBANKByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendContractTOOWN(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryContractTOOWNByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendContractNotEffect(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryContractNotEffectByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }

    private HttpResult resendContractCancel(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryContractCancelByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }



    private HttpResult resendContractEffect(String batchNum) {
        List<ERPBaseDTO> dtoList = mapper.queryContractEffectByBatchNum(batchNum);
        String requestJson = JSONObject.toJSONString(dtoList);
        return OKHttpUtils.doPostJSON(ERPURL,requestJson);
    }


    private String handleResponse(HttpResult httpResult) {
        String status = "E";
        String responseData = httpResult.getData();
        if(StringUtils.isEmpty(responseData)){
            return status;
        }
        JSONArray jsonArray = JSONObject.parseArray(responseData);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        if( jsonObject != null && "S".equals(jsonObject.get("syncStatus")) ){
            status = "S";
        }
        return status;
    }


    private String handleResponse1(HttpResult httpResult) {
        String status = "E";
        String responseData = httpResult.getData();
        if(StringUtils.isEmpty(responseData)){
            return status;
        }
        JSONObject jsonObject = JSONObject.parseObject(responseData);
        if( jsonObject != null && "S".equals(jsonObject.get("syncStatus")) ){
            status = "S";
        }
        return status;
    }
}
