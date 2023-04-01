package com.example.springrest.DAO;

import com.example.springrest.model.Person;

import java.util.List;

public interface DAO {
    public List<Person> index();
    public Person show(int id);
    public void save(Person person);
    public void update(int id, Person upPerson);
    public void delete(int id);
    void addAdmin(Person person);
}
