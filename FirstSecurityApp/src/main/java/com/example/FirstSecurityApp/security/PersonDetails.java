package com.example.FirstSecurityApp.security;

import com.example.FirstSecurityApp.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {this.person = person;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;//коллекция прав пользователя
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//не заблочен
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//пароль не просрочен
    }

    @Override
    public boolean isEnabled() {
        return true;//аккаунт включен и работает
    }

    /**
     * нужен чтобы получать данные аутентифицированного пользователя
     * @return
     */
    public Person getPerson(){return this.person;}
}
