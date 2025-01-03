package com.dzieger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecureController
 *
 * This controller is used to provide secure endpoints.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/secure")
public class SecureController {

    /**
     * Constructor
     */
    public SecureController() {
        // Default constructor
    }

    /**
     * secureData
     *
     * This method is used to provide a secure endpoint.
     *
     * @return String
     */
    @GetMapping("/data")
    public String secureData() {
        return "This is a secure endpoint!";
    }

}
