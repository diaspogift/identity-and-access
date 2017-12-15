package com.diaspogift.identityandaccess.port.adapter.resources.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "restservice";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {

            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {


            //TODO review all regexxxxxxx Expressionssss
            //TODO review all regexxxxxxx Expressionssss
            //TODO review all regexxxxxxx Expressionssss

            http.antMatcher("/api/v1/**").authorizeRequests()

                    //Tenant Resource
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and hasRole('ROLE_DG_ADMINISTRATOR')")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and hasRole('ROLE_DG_ADMINISTRATOR')")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/provisions").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and hasRole('ROLE_DG_ADMINISTRATOR')")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/provisions").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and hasRole('ROLE_DG_ADMINISTRATOR')")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/provisions").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/registration-invitations").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/registration-invitations").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/availability-status").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/availability-status").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")

                    //Role Resource
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/roles").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/roles").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/roles/{roleName:(\\w+(\\s+\\w)*)}/groups").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/roles/{roleName:(\\w+(\\s+\\w)*)}/groups").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/roles/{roleName:(\\w+(\\s+\\w)*)}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/roles/{roleName:(\\w+(\\s+\\w)*)}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")


                    //Group Resource

                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups/{groupName:(\\w+(\\s+\\w)*)}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups/{groupName:(\\w+(\\s+\\w)*)}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups/{groupName:(\\w+(\\s+\\w)*)}/members").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups/{groupName:(\\w+(\\s+\\w)*)}/members").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/groups/{groupName:(\\w+(\\s+\\w)*)}/members/{name:(\\w+(\\s+\\w)*)}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")


                    //User Resource

                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/autenticated-with/{password:(\\w+(\\s+\\w)*)}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/password").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/registrations").anonymous()
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/enablement").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/enablement").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/contact").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/contact").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/name").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.POST, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/name").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users/{username:(\\w+(\\s+\\w)*)}/in-role/{roleName}").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read'))")
                    .antMatchers(HttpMethod.GET, "/api/v1/tenants/{tenantId:([A-Z0-9]{8}(-[A-Z0-9]{4}){3}-[A-Z0-9]{12})}/users").access("(#oauth2.hasScope('trusted') or #oauth2.hasScope('read')) and (hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DG_ADMINISTRATOR'))")


                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private DiaspoGiftUserDetailsService userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            clients.inMemory()
                    .withClient("dg-identity-and-access-angular4-client")
                    .authorizedGrantTypes("password", "refresh_token", "client_details")
                    .authorities("DG_WEB_CLIENT")
                    .scopes("trusted")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456")
                    .and()
                    .withClient("dg-collaboration-angular4-client")
                    .authorizedGrantTypes("password", "refresh_token", "client_details")
                    .authorities("DG_APP_CLIENT")
                    .scopes("trusted")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456")
                    .and()
                    .withClient("dg-trusted-client")
                    .authorizedGrantTypes("authorization_code", "password", "refresh_token", "client_details")
                    .authorities("THRID_PARTY_CLIENT")
                    .scopes("trusted", "read")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456");
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

    }

}
