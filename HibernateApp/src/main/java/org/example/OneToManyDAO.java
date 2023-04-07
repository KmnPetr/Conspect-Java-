package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * получим все заказы человека с id 3
     */
    public void getAllItemOfPersonId_3(){
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

    /**
     * получим человека сделавшаго заказ 5
     */
    public void getPersonOfItemId_5(){
        try {
            session.beginTransaction();

            Item item=session.get(Item.class,5);
            System.out.println(item);
            Person person=item.getOwner();
            System.out.println(person);

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }

    /**
     * сделаем заказ для человека с id 2
     */
    public void makeAnOrderForPersonId_2(){
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,2);
            Item newItem=new Item("Item from hibernate",person);//нужен конструктор с параметром owner
            person.getItems().add(newItem);//необязательный вызов, делается чтобы держать кэш hibernate в актуальном состоянии.Она не порождает sql запроса
            session.persist(newItem);

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }

    /**
     * создадим человека с одним заказом
     */
    public void createPersonWithOneItem(){
        try {
            session.beginTransaction();

            Person person=new Person("Test person",30);
            Item newItem=new Item("Item from hibernate 2",person);
            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));//почемуто он изначально делается неизменяемым,чтобы это исправить делается такая конструкция
            session.persist(person);
            session.persist(newItem);

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }

    /**
     * удалим все заказы у человека 3
     */
    public void deleteAllItemsOfPesonId_3(){
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,3);
            List<Item> items=person.getItems();
            for(Item item:items) {session.remove(item);}
            //не порождает sql нужна для обратной связи с кэшем
            items.clear();
            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }

    /**
     * удалим человека 2 с каскадным удалением его заказа
     */
    public void deletePerson2_withCascade(){
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,2);
            session.remove(person);//вызовет каскадное проставление null с учетом настройки таблицы
            person.getItems().forEach(i-> i.setOwner(null));//для обратной связи с кэшем

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }

    /**
     * Поменяем владельца товара
     */
    public void chengeOwnerOfTheItem(){
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,6);
            Item item=session.get(Item.class,8);
            item.getOwner().getItems().remove(item);//для обратной связи с кешем
            item.setOwner(person);
            person.getItems().add(item);//для обратной связи с кешем

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
    }
}
