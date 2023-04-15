package com.example.springrest.services;

import com.example.springrest.model.Person;
import com.example.springrest.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * @return вернет всех людей из БД
     */
    public List<Person> findAll(){
        System.out.println("вызван метод findAll из PeopleRepository");
        return peopleRepository.findAll();
    }

    /**
     * вернет человека по id или null
     * @param id
     * @return
     */
    public Person findOne(int id){
        Optional<Person> foundPerson=peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    /**
     * сохранит человека в БД
     * @param person
     */
    @Transactional//кроме чтения разрешит запись
    public void save(Person person){peopleRepository.save(person);}

    /**
     * обновит данные человека по id
     * @param id
     * @param updatedPerson
     */
    @Transactional
    public void update(int id,Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);//для добавления и для обновления используется один и тотже метод
    }

    /**
     * удалит человека из БД по id
     * @param id
     */
    @Transactional
    public void delete(int id){peopleRepository.deleteById(id);}
}
