package com.zerobase.fastlms.main_controller;

import com.zerobase.fastlms.component.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class MainContoller {

    private final MailComponents mailComponents;

    @RequestMapping("/")
    public String index(){

//        String email = "ktw5026@naver.com";
//        String subject = "인텔리제이로부터";
//        String text = "잘 갔지?";
//
//        mailComponents.sendMail(email,subject,text);

        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied(){
        return "error/denied";
    }
}
