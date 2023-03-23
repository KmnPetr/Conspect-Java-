package com.example.springrest.DAO;

import com.example.springrest.model.Person;
import org.springframework.stereotype.Component;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT=0;

    //эти данные должны лежать в проперти файле
    private static final String URL="jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME="postgres";
    private static final String PASSWORD="1234567890";
    //=============================================

    //соединение
    private static Connection connection;
    static {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Person> index(){
        //сюда выгружаем данные
        List<Person> people=new ArrayList<>();
            //этот обьект содержит в себе sql запрос
        try {
            Statement statement = (Statement) connection.createStatement();
            String SQL="SELECT*FROM Person";
            ResultSet resultSet=statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Person show(int id){
//        return people.stream().filter(person -> person.getId()==id).findAny().orElse(null);
    }

    /**
     * добавляем в базу новый обьект
     * @param person
     */
    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    /**
     * метод обновляет поле "name" в одном из обьектов списка "people"
     * @param id обьекта чьи данные нужно обновить
     * @param updatedPerson обьект из которого нужно взять новые данные для обьекта из списка
     */
    public void update(int id, Person updatedPerson) {
//        Person personToBeUpdated= show(id);
//
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    /**
     * метод удалит обьект из списка
     * @param id обьекта на удаление
     */
    public void delete(int id) {
        //removeIf удаляет обьект из списка если значение в параметре true
//        people.removeIf(p->p.getId()==id);
    }
}
