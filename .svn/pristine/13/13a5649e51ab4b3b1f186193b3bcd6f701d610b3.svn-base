package com.yixin.demo.web;

import com.yixin.alixjob.SpringbootApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author yaojie
 * @Description: MockMvc 测试web
 * @date 2018/9/299:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
//开启web上下文测试
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = SpringbootApplication.class)
public class DemoControllerTest {

    //注入webApplicationContext
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    //设置mockMvc
    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }



    @Test
    public void  test(){

        try {

            String json="{\"orderIds\":[\"800009\"],\"appId\":\"taochepay\",\"transId\":\"TC20180929120401681628\",\"orderCount\":\"1\",\"reqTime\":\"20180929120401\"}";

            //http://192.168.145.117:8082/alixapi/api/taoche/queryOrderLoanInfo

            mockMvc.perform(MockMvcRequestBuilders.post("/api/taoche/queryOrderLoanInfo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
