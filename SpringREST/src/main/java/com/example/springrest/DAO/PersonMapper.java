package com.example.springrest.DAO;

import com.example.springrest.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Этот RowMapper довольно тривиален,т.к. названия столбцов в таблице и полей в классе Person совпадают
 * то этот RowMapper может заменить new BeanPropertyRowMapper<>(Person.class)
 */
public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person=new Person();

        person.setId(rs.getInt("id"));
        person.setName(rs.getString("name"));
        person.setAge(rs.getInt("age"));
        person.setEmail(rs.getString("email"));

        return person;
    }
}
