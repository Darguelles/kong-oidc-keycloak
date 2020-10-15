package com.blitzrok.demo.javaoidc.controller;

import com.blitzrok.demo.javaoidc.controller.dto.AuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClientController {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/home")
    public ResponseEntity Home() {
        return ResponseEntity.ok("Welcome to /home endpoint");
    }

    @GetMapping("/auth")
    public ResponseEntity Auth() {
        return ResponseEntity.ok("This is the auth endpoint");
    }

    @GetMapping("/userinfo")
    public String userinfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        AuthenticationInfo info = new AuthenticationInfo(authorizedClient.getAccessToken().getTokenValue(),
                authorizedClient.getRefreshToken().getTokenValue());
        log.info(info.toString());
        return "ok";
    }
}
