package com.sfbd.serviceforcebd.model;


public class Sd {
 private String des,price,image;

    public Sd() {
    }

    public Sd(String des, String price, String image) {
        this.des = des;
        this.price = price;
        this.image = image;
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
}

