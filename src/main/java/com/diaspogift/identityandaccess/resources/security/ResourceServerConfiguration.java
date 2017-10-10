package com.diaspogift.identityandaccess.resources.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/swagger-ui.html").authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .and()
                .csrf().disable();

        http.antMatcher("/api/**").authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.GET, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.PUT, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.PATCH, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.HEAD, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .antMatchers(HttpMethod.TRACE, "/api/v1/tenants/**").access("#oauth2.hasScope('write') or #oauth2.hasScope('read') or #oauth2.hasScope('trusted')")
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }
}
