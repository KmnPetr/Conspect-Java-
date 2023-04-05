package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Configuration configuration=new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            session.createQuery("delete Person where age<30" ).executeUpdate();//удалим


            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }

    }
}
