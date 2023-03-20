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
        Person personToBeUpdated= show(id);
        personToBeUpdated.setName(updatedPerson.getName());
    }

    /**
     * метод удалит обьект из списка
     * @param id обьекта на удаление
     */
    public void delete(int id) {
        //removeIf удаляет обьект из списка если значение в параметре true
        people.removeIf(p->p.getId()==id);
    }
}
