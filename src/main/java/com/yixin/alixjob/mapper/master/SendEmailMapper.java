package com.yixin.alixjob.mapper.master;

import com.yixin.alixjob.dto.email.AlixSendEmail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SendEmailMapper extends Mapper<AlixSendEmail> {

    List<AlixSendEmail> getAlixSendEmail(@Param("sendStatus") String sendStatus, @Param("maxNum") String maxNum, @Param("type") String type);

    void updateSendEmailStatusById(@Param("id") String id, @Param("sendStatus") String sendStatus);
}
