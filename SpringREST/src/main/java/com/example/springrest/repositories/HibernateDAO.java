package com.example.springrest.repositories;

import com.example.springrest.DAO.DAO;
import com.example.springrest.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//ЗДЕСЬ ЕСТЬ ОШИБКИ
@Service
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
    public List<Person> index() {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
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
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        return entityManager.find(Person.class,id);
    }

    /**
     * сохранит в БД нового Person
     * @param person
     */
    @Override
    public void save(Person person) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    /**
     * обновляет Person по id
     * могут быть ошибки
     * @param id
     * @param upPerson
     */
    @Override
    public void update(int id, Person upPerson) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        upPerson.setId(id);
        entityManager.merge(upPerson);

        entityManager.getTransaction().commit();
    }

    /**
     * удалит Person по id
     * @param id
     */
    @Override
    public void delete(int id) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.remove(entityManager.find(Person.class,id));

        entityManager.getTransaction().commit();
        //многократное применение этого метода привили к перегрузке сервера я ничего не понимаю
    }
    @Override
    public void addAdmin(Person person) {
        //реализация сложновата и она есть в DAO_with_Template
    }
}