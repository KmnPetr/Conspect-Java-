package org.example.model;



import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//указывает хибернету не трогать колонку
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @OneToOne(mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Passport passport;
    @OneToMany(mappedBy = "owner",cascade = CascadeType.PERSIST/*сохранит связанные товары*/)
    List<Item> items;//еще геттеры и сеттеры

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public List<Item> getItems() {return items;}

    public void setItems(List<Item> items) {this.items = items;}

    @Override
    public String toString() {
        return "Person: "+"id=" + id + ", name='" + name +'\'' +", age=" + age;
    }

    public void addItem(Item item){
        if(items ==null)items=new ArrayList<>(Collections.singletonList(item));
        else items.add(item);}

    public Passport getPassport() {return passport;}

    public void setPassport(Passport passport) {this.passport = passport;}
}
