package com.diaspogift.identityandaccess.port.adapter.resources.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**").authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/tenants").access("#oauth2.hasScope('write') and #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.GET, "/api/v1/tenants").access("#oauth2.hasScope('read') or #oauth2.hasScope('write') or #oauth2.hasScope('trusted')")
        ;
    }

}
