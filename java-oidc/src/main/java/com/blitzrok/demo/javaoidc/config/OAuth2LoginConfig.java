package com.blitzrok.demo.javaoidc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class OAuth2LoginConfig {

    @EnableWebSecurity
    public static class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            Set<String> googleScopes = new HashSet<>();
            googleScopes.add(
                    "http://localhost:8180/auth/realms/master/protocol/openid-connect/userinfo.email");
            googleScopes.add(
                    "http://localhost:8180/auth/realms/master/protocol/openid-connect/userinfo.profile");

            OidcUserService googleUserService = new OidcUserService();
            googleUserService.setAccessibleScopes(googleScopes);

            http
                    .authorizeRequests(authorize -> authorize
                            .anyRequest().authenticated()
                    )
                    .oauth2Login(oauthLogin -> oauthLogin
                            .userInfoEndpoint()
                            .oidcUserService(googleUserService));
        }
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.keycloakClientConfiguration());
    }

    private ClientRegistration keycloakClientConfiguration() {
        return ClientRegistration.withRegistrationId("keycloak")
                .clientId("java-app")
                .clientSecret("bf2a0b5c-525b-4706-b705-1f29f68520bd")
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .tokenUri("http://localhost:8180/auth/realms/master/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8180/auth/realms/master/protocol/openid-connect/userinfo")
                .jwkSetUri("http://localhost:8180/auth/realms/master/protocol/openid-connect/certs")
                .authorizationUri("http://localhost:8180/auth/realms/master/protocol/openid-connect/auth")
                .clientName("Keycloak")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }
}
