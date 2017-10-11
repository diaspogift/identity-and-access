package com.diaspogift.identityandaccess.resources.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // do not call super, otherwise you'll get BeanCreationException: Error creating bean with name 'authenticationManagerBean' - org.springframework.beans.FatalBeanException: A dependency cycle was detected when trying to resolve the AuthenticationManager. Please ensure you have configured authentication.
        final String sysadminPassword = "secret";
        auth.inMemoryAuthentication()
                .withUser("sysadmin")
                .password(sysadminPassword)
                .roles("SYSADMIN", "ADMIN", "USER");
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManagerBean();
    }
}
