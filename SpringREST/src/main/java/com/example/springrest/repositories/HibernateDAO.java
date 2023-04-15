package com.example.springrest.repositories;

import com.example.springrest.DAO.DAO;
import com.example.springrest.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class HibernateDAO implements DAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public HibernateDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * метод получает всех Person из БД
     * @return
     */
    @Override
    @Transactional
    public List<Person> index() {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<Person> people=entityManager
                .createQuery("select p from Person p", Person.class)
                .getResultList();
        System.out.println("отработал метод index класса HibernateDAO");
        return people;
    }

    /**
     * найдет одного Person по id
     * @param id
     * @return
     */
    @Override
    public Person show(int id) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("отработал метод show класса HibernateDAO");
        return entityManager.find(Person.class,id);
    }

    /**
     * сохранит в БД нового Person
     * @param person
     */
    @Override
    @Transactional
    public void save(Person person) {
    }
    @Override
    public void update(int id, Person upPerson) {

    }
    @Override
    public void delete(int id) {
    }
    @Override
    public void addAdmin(Person person) {

    }
}