package com.dzieger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @GetMapping("/data")
    public String secureData() {
        return "This is a secure endpoint!";
    }

}
