package com.sfbd.serviceforcebd.model;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String catagory;
    private String productPrice;

    private String productName;
    private String noOfProduct;
    private String pushId;

    public CartModel() {
    }

    public CartModel(String catagory, String productPrice, String productName, String noOfProduct,String pushId) {
        this.catagory = catagory;
        this.productPrice = productPrice;
        this.productName = productName;
        this.noOfProduct = noOfProduct;
        this.pushId=pushId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNoOfProduct() {
        return noOfProduct;
    }

    public void setNoOfProduct(String noOfProduct) {
        this.noOfProduct = noOfProduct;
    }


}
