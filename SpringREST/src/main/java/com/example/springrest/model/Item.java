package com.example.springrest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "itemname")
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;

    public Item() {}

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getItemName() {return itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    public Person getOwner() {return owner;}
    public void setOwner(Person owner) {this.owner = owner;}
}
