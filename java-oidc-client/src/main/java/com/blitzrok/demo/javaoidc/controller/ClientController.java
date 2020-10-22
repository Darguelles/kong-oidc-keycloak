package com.blitzrok.demo.javaoidc.controller;

import com.blitzrok.demo.javaoidc.controller.dto.AuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;


@RestController
public class ClientController {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplate restTemplate;

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
                Objects.requireNonNull(authorizedClient.getRefreshToken()).getTokenValue());
        log.debug(info.toString());
        return "Check the console to get authentication info";
    }

    @GetMapping("/oidc-principal")
    public OidcUser getOidcUserPrincipal(
            @AuthenticationPrincipal OidcUser principal) {
        log.info(principal.toString());
        return principal;
    }

    @GetMapping("/get-mocks")
    public ResponseEntity GetMocks(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity res = restTemplate.exchange("http://localhost:8000/mock", HttpMethod.GET, entity, String.class);
        res.getHeaders().forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
        return res;
    }
}
