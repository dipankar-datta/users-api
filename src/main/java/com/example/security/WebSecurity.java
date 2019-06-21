package com.example.security;

import com.example.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment environment;

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.environment = environment;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/**")
                .hasIpAddress(environment.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthenticationFilter());
        httpSecurity.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.path"));
        return authenticationFilter;
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
