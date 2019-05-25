package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private double price;

    private String image;

    public Item() { }

    @Ignore
    public Item(@NonNull String id, String name, String image, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    @NonNull
    public String getId() { return id; }

    public void setId(@NonNull String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
