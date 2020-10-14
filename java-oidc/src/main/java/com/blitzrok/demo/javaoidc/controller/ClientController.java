package com.blitzrok.demo.javaoidc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @GetMapping("/home")
    public ResponseEntity Home() {
        return ResponseEntity.ok("Welcome to /home endpoint");
    }

    @GetMapping("/auth")
    public ResponseEntity Auth() {
        return ResponseEntity.ok("This is the auth endpoint");
    }

}
