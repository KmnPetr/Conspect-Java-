package com.example.springrest.DAO;

import com.example.springrest.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT=0;
    List<Person> people;
    {
        people=new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Leha"));
        people.add(new Person(++PEOPLE_COUNT,"Tom"));
        people.add(new Person(++PEOPLE_COUNT,"Bob"));
        people.add(new Person(++PEOPLE_COUNT,"Mike"));
        people.add(new Person(++PEOPLE_COUNT,"Katy"));
    }
    public List<Person> index(){
        return people;
    }
    public Person show(int id){
        return people.stream().filter(person -> person.getId()==id).findAny().orElse(null);
    }
}
