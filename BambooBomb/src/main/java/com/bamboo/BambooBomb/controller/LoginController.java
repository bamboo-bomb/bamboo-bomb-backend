package com.bamboo.BambooBomb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @GetMapping("/login/callback")
    public String loginSuccess(
        @RequestParam(value="state", required=false) String state, 
        @RequestParam(value="code", required=false) String code, 
        @RequestParam(value="error", required=false) String error, 
        @RequestParam(value="error_description", required=false) String error_description
        ) {
        if (error != null) {
            return error_description;
        }

        return "Callback 성공! : " + state + code;
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "로그인 실패!";
    }
}
