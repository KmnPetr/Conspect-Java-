package com.example.springrest.DAO;

import com.example.springrest.DAO.DAO;
import com.example.springrest.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//It's working
@Repository
public class HibernateDAO implements DAO {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * метод получает всех Person из БД
     * @return
     */
    @Override
    public List<Person> index() {
        List<Person> people=entityManager
                .createQuery("select p from Person p", Person.class)
                .getResultList();
        return people;
    }

    /**
     * найдет одного Person по id
     * @param id
     * @return
     */
    @Override
    public Person show(int id) {
        return entityManager.find(Person.class,id);
    }

    /**
     * сохранит в БД нового Person
     * @param person
     */
    @Override
    @Transactional
    public void save(Person person) {
        entityManager.persist(person);
    }

    /**
     * обновляет Person по id
     * @param id
     * @param upPerson
     */
    @Override
    @Transactional
    public void update(int id, Person upPerson) {
        upPerson.setId(id);
        entityManager.merge(upPerson);
    }

    /**
     * удалит Person по id
     * @param id
     */
    @Override
    @Transactional
    public void delete(int id) {
        entityManager.remove(entityManager.find(Person.class,id));
    }
    @Override
    public void addAdmin(Person person) {
        //реализация сложновата и она есть в DAO_with_Template
    }
}