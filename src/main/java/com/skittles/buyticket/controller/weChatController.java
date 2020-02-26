package com.skittles.buyticket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class weChatController {
    @RequestMapping("/MP_verify_DGrvOPLWkQm9snSi.txt")
    public String returnWechat() {
        return "DGrvOPLWkQm9snSi";
    }
}
