package org.example.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "person_id",insertable=false, updatable=false)//тут добавились послед.2 параметра не показанных в видео без них не заводилось
    private int person_id;
    @Column(name = "item_name")
    private String item_name;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;//создадим еще геттеры и сеттеры

    public Item() {}
    public Item(String item_name,Person owner) {
        this.item_name = item_name;
        this.owner=owner;
    }



    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getPerson_id() {return person_id;}

    public void setPerson_id(Integer person_id) {this.person_id = person_id;}

    public String getItem_name() {return item_name;}

    public void setItem_name(String item_name) {this.item_name = item_name;}

    public Person getOwner() {return owner;}

    public void setOwner(Person owner) {this.owner = owner;}

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", person_id=" + person_id +
                ", item_name='" + item_name + '\'' +
                '}';
    }
}
