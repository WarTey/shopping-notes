package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private double price;

    private String image;

    public Item(String name, String image, Double price) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
