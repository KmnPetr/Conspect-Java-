package com.example.springrest.DAO;

import com.example.springrest.model.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * этот ДАО больше не применим к основному проекту изза устаревания большого количества методов
 */
@Component
public class PersonDAO implements DAO {
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

    @Override
    public List<Person> index(){
        //сюда выгружаем данные
        List<Person> people=new ArrayList<>();
        try {
            //этот обьект содержит в себе sql запрос
            Statement statement = (Statement) connection.createStatement();
            String SQL="SELECT*FROM Person";
            //принимает ответ сбазы данных в строках
            ResultSet resultSet=statement.executeQuery(SQL);
            //выгружаем данные из резулт сета
            while (resultSet.next()){
                Person person=new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }
    @Override
    public Person show(int id){
        Person person=null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT*FROM Person WHERE id=?");

            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();

            resultSet.next();

            person=new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  person;
    }

    /**
     * добавляем в базу новый обьект
     * @param person
     */
    @Override
    public void save(Person person) {
        try {
            //старый небезопасный, небыстрый способ, есть возможность для sql иньекций//test@test@mail.com');DROP TABLE Person;-- запрос в поле емаил который убивает таблицу
            Statement statement=connection.createStatement();
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +"'," + person.getAge() + ",'" + person.getEmail() + "')";
            //INSERT INTO Person VALUES(1,'Tom',18,'tom@mail.com')
            //запрос на изменение в БД
            statement.executeUpdate(SQL);
            //PreparedStatement лучше быстрее безопаснее
            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO Person(name,age,email) VALUES(?,?,?)");

            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * метод обновляет поле "name" в одном из обьектов списка "people"
     * @param id обьекта чьи данные нужно обновить
     * @param upPerson обьект из которого нужно взять новые данные для обьекта из списка
     */
    @Override
    public void update(int id, Person upPerson) {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Person SET name=?,age=?,email=? WHERE id=?");


            preparedStatement.setString(1,upPerson.getName());
            preparedStatement.setInt(2,upPerson.getAge());
            preparedStatement.setString(3,upPerson.getEmail());
            preparedStatement.setInt(4,upPerson.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * метод удалит обьект из списка
     * @param id обьекта на удаление
     */
    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAdmin(Person person) {

    }
}
