package com.ural.authserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AuthorizationServerConfiguration {

    private static final String ROLES_CLAIM = "roles";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

   /* @Autowired
    private MongoAccessTokenRepository mongoAccessTokenRepository;
    @Autowired
    private MongoRefreshTokenRepository mongoRefreshTokenRepository;*/

    @Value("${authorization-api.keyFile}")
    private String keyFile;

    @Value("${authorization-api.password}")
    private String password;

    @Value("${authorization-api.alias}")
    private String alias;

    @Value("${authorization-api.providerUrl}")
    private String providerUrl;

    @Autowired
    private PasswordEncoder passwordEncoder;


  /*  @Bean
    public TokenStore tokenStore() {
        return new MongoTokenStore(mongoAccessTokenRepository, mongoRefreshTokenRepository);
    }*/

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {

        JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
       /* JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);

        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        // You will need to write the Mixin for your class so Jackson can marshall it.
        objectMapper.addMixIn(UserPrincipal.class, UserPrincipalMixin.class);
        rowMapper.setObjectMapper(objectMapper);
        jdbcOAuth2AuthorizationService.setAuthorizationRowMapper(rowMapper);*/

        return jdbcOAuth2AuthorizationService;

    }

    @Bean
    public OAuth2AuthorizationConsentService auth2AuthorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {

        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);

    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults()).build();

    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);

    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        JWKSet jwkSet = buildJWKSet();
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);

    }

    private JWKSet buildJWKSet() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        try (InputStream fis = this.getClass().getClassLoader().getResourceAsStream(keyFile);) {
            keyStore.load(fis, alias.toCharArray());
            return JWKSet.load(keyStore, new PasswordLookup() {

                @Override
                public char[] lookupPassword(String name) {
                    return password.toCharArray();
                }
            });
        }

    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer(providerUrl).build();

    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registredClient = RegisteredClient.withId("couponservice")
                .clientId("couponclientapp")
                .clientSecret(passwordEncoder.encode("9999"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oidcdebugger.com/debug")
                //  .redirectUri("http://127.0.0.1:8080/login/oauth2/code/users-client-oidc")
                .redirectUri("https://spring.io/auth")
                .scope("read").scope("write").scope(OidcScopes.OPENID)
                .tokenSettings(tokenSettings())
                .build();

        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        registeredClientRepository.save(registredClient);
        return registeredClientRepository;

    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(30l))
                .reuseRefreshTokens(true).build();

    }


    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                Authentication principal = context.getPrincipal();
                Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim(ROLES_CLAIM, authorities);
            }
        };

    }

}
