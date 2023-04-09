package org.example;

import org.example.model.Item;
import org.example.model.Passport;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App 
{
    public static void main( String[] args ) {

        //здесь находится образец настройки подключения hibernate к БД
        Configuration configuration=new Configuration()
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
        }
        try{
            Session session=sessionFactory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Внутри второй транзакции");
            person=session.merge(person);//пристегнули обьект к hibernate
            Hibernate.initialize(person.getItems());

            session.getTransaction().commit();
            System.out.println("Закрыли 2ю сессию");
        }finally {
            sessionFactory.close();
        }
        System.out.println(person.getItems());

//        OneToManyDAO dao=new OneToManyDAO();
//        dao.getAllItemOfPersonId_3();
//        dao.getPersonOfItemId_5();
//        dao.makeAnOrderForPersonId_2();
//        dao.createPersonWithOneItem();
//        dao.deleteAllItemsOfPesonId_3();
//        dao.deletePerson2_withCascade();
//        dao.chengeOwnerOfTheItem();
//        dao.hibernateCascade();

//        OneToOneDAO dao1=new OneToOneDAO();
//        dao1.createPersonWithPassport();
//        dao1.checkCascade();

//        ManyToManyDAO dao=new ManyToManyDAO();
//        dao.pushMovieAndActors();
//        dao.getMovieWithActors();
//        dao.pushMovieWithOldActors();
//        dao.deleteActorInMovie();
    }
}
