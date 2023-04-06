package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class OneToManyDAO {
    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Session session;

    public OneToManyDAO() {
        //фууу как некрасиво
        this.configuration=new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        this.sessionFactory=configuration.buildSessionFactory();
        this.session=sessionFactory.getCurrentSession();
    }

    public void getItemForPersonId(){
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,3);
            System.out.println(person);
            List<Item> items=person.getItems();
            System.out.println(items);

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }
}
