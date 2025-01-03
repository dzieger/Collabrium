package com.dzieger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PublicController
 *
 * This controller is used to provide public endpoints.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/public")
public class PublicController {

    /**
     * Constructor
     */
    public PublicController() {
        // Default constructor
    }

    /**
     * welcome
     *
     * This method is used to provide a welcome message to the public API.
     *
     * @return String
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the public API!";
    }

}
