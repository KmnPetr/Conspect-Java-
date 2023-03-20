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
        people.add(new Person(++PEOPLE_COUNT,"Leha",13,"Lexa@mail.com"));
        people.add(new Person(++PEOPLE_COUNT,"Tom",45,"Tom@mail.com"));
        people.add(new Person(++PEOPLE_COUNT,"Bob",67,"Bob@yandex.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Mike",23,"Mike@yandex.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Katy",55,"Katy@gmail.com"));
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
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
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
