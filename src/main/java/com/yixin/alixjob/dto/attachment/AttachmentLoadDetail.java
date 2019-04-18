package com.yixin.alixjob.dto.attachment;

import lombok.Data;

/**
 * @Author: lishuang
 * @Date: 2018/11/16 17:44
 */
@Data
public class AttachmentLoadDetail {

    private String   id;
    private String  tradeNo;
    private String  asqbh;
    private String  isNewFile;
    private String  fileClassType;
    private String  fileClassSubType;
    private String  fileClassTypeName;
    private String  fileClassSubTypeName;
    private String  filePath;
}
