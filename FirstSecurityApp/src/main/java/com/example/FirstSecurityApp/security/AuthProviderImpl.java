package com.example.FirstSecurityApp.security;

import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * пока не будем использовать этот класс
 * пока у нас не появится много серверов и аутентификация не будет происходить на удаленном сервере
 * а так здесь тривиальная логика спринг ее сам реализует
 */
@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonDetailsService personDetailsService;
    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //в случае успешной аутентификации он вернет PersonDetail
    @Override
    public Authentication authenticate(Authentication authentication/*спринг передаст сам*/) throws AuthenticationException {
        String username= authentication.getName();
        UserDetails userDetails=personDetailsService.loadUserByUsername(username);
        String password=authentication.getCredentials().toString();
        if (!password.equals(userDetails.getPassword()))throw new BadCredentialsException("Incorrect password");
        return new UsernamePasswordAuthenticationToken(userDetails,
                password,
                Collections.emptyList()/*список прав пока нет их*/);
    }

    //на случай если в приложении несколько провайдеров
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
