package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App 
{
    public static void main( String[] args ) {

        //здесь находится образец настройки подключения hibernate к БД
        /*Configuration configuration=new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person=session.get(Person.class,3);
            System.out.println(person);
            List<Item> items=person.getItems();
            System.out.println(items);

            session.getTransaction().commit();

        }finally {
            sessionFactory.close();

        }*/

//        OneToManyDAO dao=new OneToManyDAO();
//        dao.getAllItemOfPersonId_3();
//        dao.getPersonOfItemId_5();
//        dao.makeAnOrderForPersonId_2();
//        dao.createPersonWithOneItem();
//        dao.deleteAllItemsOfPesonId_3();
//        dao.deletePerson2_withCascade();
//        dao.chengeOwnerOfTheItem();
//        dao.hibernateCascade();

        OneToOneDAO dao1=new OneToOneDAO();
        dao1.createPersonWithPassport();
//        dao1.checkCascade();
    }
}
