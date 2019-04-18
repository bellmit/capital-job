package com.yixin.alixjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 上传文件系统配置
 *
 * @author wujt
 * @date 2018/1/8 20:21
 */
@Configuration
@Slf4j
public class UploadFileConfiguration {
    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {

        log.info(">>>>>>>>>>>>文件上传配置大小>>>>>>>>>>>>>");
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大:KB,MB
        factory.setMaxFileSize("200MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024MB");
        return factory.createMultipartConfig();
    }
}
