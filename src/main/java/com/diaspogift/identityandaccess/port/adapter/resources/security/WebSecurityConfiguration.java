package com.diaspogift.identityandaccess.port.adapter.resources.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final static int PASSWORD_ENCODER_STRENGTH = 10;


    @Autowired
    private DiaspoGiftUserDetailsService diaspoGiftUserDetailsService;

    @Autowired
    private DiaspoGiftAuthenticationProvider diaspoGiftAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(diaspoGiftAuthenticationProvider).userDetailsService(diaspoGiftUserDetailsService).passwordEncoder(bcrypEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder bcrypEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
    }


}
