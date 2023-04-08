package org.example;

import org.example.model.Item;
import org.example.model.Passport;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class OneToOneDAO {
    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Session session;

    public OneToOneDAO() {
        //фууу как некрасиво
        this.configuration=new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Passport.class);

        this.sessionFactory=configuration.buildSessionFactory();
        this.session=sessionFactory.getCurrentSession();
    }

    public void createPersonWithPassport(){
        try {
            session.beginTransaction();

            Person person=new Person("Person with passport",18);
            Passport passport=new Passport(person,12345);
            person.setPassport(passport);
            session.persist(person);

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }
    public void checkCascade(){
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,12);
            System.out.println(person.getPassport().getPassport_number());

        System.out.println("////////////////////////////////////");

            Passport passport=session.get(Passport.class,12);
            System.out.println(passport.getPerson().getName());

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }
}
