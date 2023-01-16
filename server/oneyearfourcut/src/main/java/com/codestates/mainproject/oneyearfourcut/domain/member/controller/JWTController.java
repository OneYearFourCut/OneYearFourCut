package com.codestates.mainproject.oneyearfourcut.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JWTController {    //jwt test를 위한 임시 controller
    @RequestMapping("/receive-token")
    public String index(Model model) {
        return "receive-token";
    }

    @RequestMapping("/sse/1")
    public String sse1() {
        return "sse1";
    }
    @RequestMapping("/sse/2")
    public String sse2() {
        return "sse2";
    }

}
