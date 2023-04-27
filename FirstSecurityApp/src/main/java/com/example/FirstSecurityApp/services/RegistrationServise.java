package com.example.FirstSecurityApp.services;

import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServise {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;//внедрится из бина в конфиге
    @Autowired
    public RegistrationServise(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * метод зарегестрирует нового человека в БД
     */
    @Transactional
    public void register(Person person){
        String encodetPassword=passwordEncoder.encode(person.getPassword());
        person.setPassword(encodetPassword);
        person.setRole("ROLE_USER");//по умолчанию ставим роль юзера

        peopleRepository.save(person);
    }
}
