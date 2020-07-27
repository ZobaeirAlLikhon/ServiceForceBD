package com.sfbd.serviceforcebd.model;


public class Sd {
 private String des,price,image,name;

    public Sd() {
    }

    public Sd(String des, String price, String image,String name) {
        this.des = des;
        this.price = price;
        this.image = image;
        this.name=name;
    }


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

