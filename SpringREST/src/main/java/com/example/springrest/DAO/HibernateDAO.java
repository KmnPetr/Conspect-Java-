package com.example.springrest.DAO;

import com.example.springrest.model.Item;
import com.example.springrest.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
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
    public Person show(int id) {return entityManager.find(Person.class,id);}

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
    //////////////////////////////////////////////////
    //////////тестим проблему N+1/////////////////////
    //////////////////////////////////////////////////
    @Transactional(readOnly = true)
    public void testNPlus1(){
        //1 запрос
        List<Person>people=entityManager.createQuery("select p from Person p", Person.class).getResultList();
        //N запросов
        for (Person person:people) System.out.println("Person: "+person.getName()+" has: "+person.getItems());
    }
    @Transactional(readOnly = true)
    public void testNPlus1_variant2(){
        Session session=entityManager.unwrap(Session.class);
        List<Person>people=session.createQuery("select p from Person p LEFT JOIN FETCH p.items").getResultList();

        for (Person person:people) System.out.println("Person: "+person.getName()+" has: "+person.getItems());
    }
}