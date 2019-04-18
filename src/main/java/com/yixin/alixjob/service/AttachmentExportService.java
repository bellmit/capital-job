package com.yixin.alixjob.service;

import com.jcraft.jsch.SftpException;

import java.io.FileNotFoundException;

/**
 * @Author: lishuang
 * @Date: 2018/11/19 9:12
 */
public interface AttachmentExportService {

    /**
     * 导出附件
     */
    void exportFile() throws FileNotFoundException, SftpException;

}
