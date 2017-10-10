package com.diaspogift.identityandaccess.resources.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class AuthorisationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("mailinglist")
                .authorities("DIASPOGIFT_CLIENT")
                .scopes("read", "write")
                .secret("secret")
                .and()
                .withClient("diaspogift_iam_trusted_frontend_app")
                .authorities("DIASPOGIFT_TRUSTED")
                .scopes("trusted")
                .secret("secret")
                .accessTokenValiditySeconds(600);
    }


}
