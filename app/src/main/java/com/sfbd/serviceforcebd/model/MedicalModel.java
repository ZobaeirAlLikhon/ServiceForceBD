package com.sfbd.serviceforcebd.model;

public class MedicalModel {
    String des,name,price;

    public MedicalModel() {
    }

    public MedicalModel(String des, String name, String price) {
        this.des = des;
        this.name = name;
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
