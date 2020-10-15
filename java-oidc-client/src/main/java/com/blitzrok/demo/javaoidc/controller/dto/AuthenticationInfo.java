package com.blitzrok.demo.javaoidc.controller.dto;

public class AuthenticationInfo {
    private String authorizationToken;
    private String refreshToken;

    public AuthenticationInfo(String authorizationToken, String refreshToken) {
        this.authorizationToken = authorizationToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AuthenticationInfo{" +
                "authorizationToken='" + authorizationToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
