package com.blitzrok.demo.javaoidc.controller;

import com.blitzrok.demo.javaoidc.controller.dto.AuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@RestController
public class ClientController {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/home")
    public ResponseEntity Home() {
        return ResponseEntity.ok("Welcome to /home endpoint");
    }

    @GetMapping("/auth-info")
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

    @GetMapping("/get-mocks")
    public String GetMocks(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ authorizedClient.getAccessToken().getTokenValue());
        log.info("Authorization token set: " + authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        String result = restTemplate.getForObject("http://localhost:8000/mock", String.class, entity);
        return result;
    }
}
