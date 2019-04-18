package com.yixin.alixjob.mapper.master;

import com.yixin.alixjob.dto.attachment.AttachmentLoadDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lishuang
 * @Date: 2018/11/19 9:17
 */
public interface AttachmentExportMapper {


    /**
     * 查询当前需要处理的流水号
     * @return
     */
    String selectOneTradeNo();

    /**
     * 查询需要导出附件的申请编号
     * @return
     */
    List<AttachmentLoadDetail> selectExportNewAttachmentDetails(@Param("tradeNo")  String tradeNo);

    /**
     * 查询需要导出附件的申请编号
     * @return
     */
    List<AttachmentLoadDetail> selectExportOldAttachmentDetails(@Param("tradeNo")  String tradeNo);

    /**
     * 更新导出记录
     * @param tradeNo  流水号
     * @param status 状态
     * @param tarPath  tar包路径
     * @param tarName  tar包名称
     */
    void updateAttachmentExportStatus(@Param("tradeNo") String tradeNo, @Param("status") String status,
                                      @Param("tarPath") String tarPath, @Param("tarName") String tarName);

}
