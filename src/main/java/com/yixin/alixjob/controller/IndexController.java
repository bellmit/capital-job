package com.yixin.alixjob.controller;

import com.yixin.alixjob.dto.email.AlixSendEmail;
import com.yixin.alixjob.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class IndexController {

    private static final String appName = "alix-api";


    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private SendEmailService sendEmailService;


    /**
     *  首页
     * @return
     */
    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(value = "name", required = false,
                               defaultValue = "Guest") String name) {
        model.addAttribute("name", name);
        model.addAttribute("title", appName);
        return "index";
    }


    @GetMapping("/getRedis")
    @ResponseBody
    public String getRedis(Model model,
                       @RequestParam(value = "name", required = false,
                               defaultValue = "Guest") String name) {
        redisTemplate.opsForValue().set("name_001",name);
        String value=(String)redisTemplate.opsForValue().get("name_001");
        return value;
    }


    @GetMapping("/getEmail")
    @ResponseBody
    public List<AlixSendEmail> getEmail(Model model) {
        List<AlixSendEmail> list = sendEmailService.selectListAll();
        return list;
    }

}
