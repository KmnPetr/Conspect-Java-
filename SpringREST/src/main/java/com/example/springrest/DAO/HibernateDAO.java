package com.example.springrest.DAO;

import com.example.springrest.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.cfg.Configuration;

import java.util.List;

@Component
public class HibernateDAO implements DAO {
    Configuration configuration;
    private final SessionFactory sessionFactory;


    public HibernateDAO() {
        //здесь говнокод
        configuration = new Configuration()
            .addAnnotatedClass(Person.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    //конструктор должен был выглядеть примерно так
    /*@Autowired
    public HibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    @Override
    @Transactional(readOnly = true/*типа только читаем*/)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        //сдесь будет hibernate код
        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();
        return people;
    }

    @Override
    public Person show(int id) {
        return null;
    }

    @Override
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


    /*Configuration configuration=new Configuration()
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(Passport.class)
            .addAnnotatedClass(Item.class);

    SessionFactory sessionFactory=configuration.buildSessionFactory();

    Person person;
        try {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();

        person=session.get(Person.class,3);
        System.out.println("Получили человека");

        session.getTransaction().commit();
        System.out.println("Закрыли сессию");
        //session.close(); вызовется сам
    }finally {
//            sessionFactory.close();
    }*/

