package com.yixin.alixjob.dto.capital;

import lombok.Data;

/**
 * @Author: xlp
 * @Date: 2019/3/14
 */
@Data
public class ICBCEmailInfoDto {

    private String  id;
    //申请编号
    private String  asqbh;
    //资方编号
    private String  capitalCode;
    //创建时间
    private String  createTime;
    //是否发松
    private String  isSend;
    //发送时间
    private String  sendTime;
    //补息金额
    private String  fillRatesAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsqbh() {
        return asqbh;
    }

    public void setAsqbh(String asqbh) {
        this.asqbh = asqbh;
    }

    public String getCapitalCode() {
        return capitalCode;
    }

    public void setCapitalCode(String capitalCode) {
        this.capitalCode = capitalCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFillRatesAmount() {
        return fillRatesAmount;
    }

    public void setFillRatesAmount(String fillRatesAmount) {
        this.fillRatesAmount = fillRatesAmount;
    }
}
