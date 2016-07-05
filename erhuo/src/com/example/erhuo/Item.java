package com.example.erhuo;

/**
 * Created by cxdn on 2016/5/23.
 */
public class Item {
    int id;
    String name;
    String description;
    Double price;
    String type;
    byte[] image;

    public Item(){};

    public Item(int id,String name,String description,Double price,String type,byte[] image)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.price=price;
        this.type=type;
        this.image=image;
    }
}
