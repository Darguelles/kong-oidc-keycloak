package com.blitzrok.demo.javaoidc.config;

/**
 * You can override the default configuration in application.yml
 * with this class.
 */

// @Configuration
public class OAuth2LoginConfig {

//    @EnableWebSecurity
//    public static class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            Set<String> googleScopes = new HashSet<>();
//            googleScopes.add(
//                    "http://localhost:8180/auth/realms/master/protocol/openid-connect/userinfo.email");
//            googleScopes.add(
//                    "http://localhost:8180/auth/realms/master/protocol/openid-connect/userinfo.profile");
//
//            OidcUserService googleUserService = new OidcUserService();
//            googleUserService.setAccessibleScopes(googleScopes);
//
//            http
//                    .authorizeRequests(authorize -> authorize
//                            .anyRequest().authenticated()
//                    )
//                    .oauth2Login(oauthLogin -> oauthLogin
//                            .userInfoEndpoint()
//                            .oidcUserService(googleUserService));
//        }
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(this.keycloakClientConfiguration());
//    }
//
//    private ClientRegistration keycloakClientConfiguration() {
//        return ClientRegistration.withRegistrationId("keycloak")
//                .clientId("<your-client-id>")
//                .clientSecret("<your-client-secret>")
//                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .tokenUri("http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/token")
//                .userInfoUri("http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/userinfo")
//                .jwkSetUri("http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/certs")
//                .authorizationUri("http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/auth")
//                .clientName("Keycloak")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .build();
//    }
}
