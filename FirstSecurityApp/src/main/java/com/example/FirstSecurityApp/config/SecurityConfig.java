package com.example.FirstSecurityApp.config;

import com.example.FirstSecurityApp.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration/*WebSecurityConfigurerAdapter это по уроку так*/{
    private final AuthProviderImpl authProvider;
    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }


    //настраиваем аутентификацию
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authProvider);//TODO
    }
}
