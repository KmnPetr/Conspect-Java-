package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
            Person person=session.get(Person.class,1/*это @Id*/);

            System.out.println(person.getName());
            System.out.println(person.getAge());

            session.getTransaction().commit();//применили транзакцию
        }finally {
            sessionFactory.close();
        }
        //////////////////////////////////////
        try {
            session.beginTransaction();

            Person person1=new Person("Test1",30);
            Person person2=new Person("Test2",40);
            Person person3=new Person("Test3",50);
//            session.save(person1);//устарела
            session.persist(person1);
            session.persist(person2);
            session.persist(person3);

            session.getTransaction().commit();//применили транзакцию
        }finally {
            sessionFactory.close();
        }
        /////////////////////////////////
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,2);//сначала получим чела по id
            person.setName("NewName");
            //hibernate сам вызовет update
            session.getTransaction().commit();//применили транзакцию
        }finally {
            sessionFactory.close();
        }
        //////////////////////////////
        try {
            session.beginTransaction();
            Person person=session.get(Person.class,1);
//            session.delete(person);//устарела
            session.remove(person);//вызовет delete

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
        ////////////////////////////////////
        try {
            session.beginTransaction();

            Person person=new Person("SomePerson",60);
            session.persist(person);

            session.getTransaction().commit();

            System.out.println(person.getId());
        }finally {
            sessionFactory.close();
        }
    }
}
