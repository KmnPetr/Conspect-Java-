package com.example.FirstSecurityApp.services;

import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServise {
    private final PeopleRepository peopleRepository;
    @Autowired
    public RegistrationServise(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * метод зарегестрирует нового человека в БД
     */
    @Transactional
    public void register(Person person){
        peopleRepository.save(person);
    }
}
