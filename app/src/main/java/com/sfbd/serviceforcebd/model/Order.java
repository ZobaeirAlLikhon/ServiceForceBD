package com.sfbd.serviceforcebd.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String userId;
    private String userName;
    private String userAddress;
    private String userContact;
    private String orderItem;
    private String orderDate;
    private String orderTime;
    private String isPlaced;
    private String productName;
    private String productPrice;
    private String noOfProduct;
    private String curreDate;
    private String curreTime;
    private String orderId;

    private String timeStamp;

    private List<CartModel> productlist;


    public Order() {
    }

    public Order(String userId, String userName, String userAddress, String userContact, String orderItem, String orderDate, String orderTime, String isPlaced, String productName, String productPrice, String noOfProduct, String curreDate, String curreTime) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
        this.orderItem = orderItem;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.isPlaced = isPlaced;
        this.productName = productName;
        this.productPrice = productPrice;
        this.noOfProduct = noOfProduct;
        this.curreDate = curreDate;
        this.curreTime = curreTime;
    }

    public Order(String userId, String userName, String userAddress, String userContact, String orderItem,
                 String orderDate, String orderTime, String isPlaced, String productName, String productPrice, String noOfProduct)
    {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
        this.orderItem = orderItem;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.isPlaced = isPlaced;
        this.productName=productName;
        this.productPrice=productPrice;
        this.noOfProduct=noOfProduct;
    }

    public Order(String userId, String userName, String userAddress, String userContact,
                 String orderItem, String orderDate, String orderTime, String isPlaced) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
        this.orderItem = orderItem;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.isPlaced = isPlaced;
    }

    public Order(String userId, String userName, String userAddress,
                 String userContact, String orderItem, String orderDate,
                 String orderTime, String orderId, String isPlaced) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
        this.orderItem = orderItem;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderId = orderId;
        this.isPlaced = isPlaced;
    }


    public Order(String userId, String userName, String userAddress, String userContact, String orderItem, String orderDate,
                 String orderTime, String isPlaced,String productName, String productPrice,
                 String noOfProduct, List<CartModel> productlist) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
        this.orderItem = orderItem;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.isPlaced = isPlaced;
        this.productName = productName;
        this.productPrice = productPrice;
        this.noOfProduct = noOfProduct;
        this.productlist = productlist;
    }

    public String getCurreDate() {
        return curreDate;
    }

    public void setCurreDate(String curreDate) {
        this.curreDate = curreDate;
    }

    public String getCurreTime() {
        return curreTime;
    }

    public void setCurreTime(String curreTime) {
        this.curreTime = curreTime;
    }

    public String getIsPlaced() {
        return isPlaced;
    }

    public void setIsPlaced(String isPlaced) {
        this.isPlaced = isPlaced;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String isPlaced() {
        return isPlaced;
    }

    public void setPlaced(String placed) {
        isPlaced = placed;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getNoOfProduct() {
        return noOfProduct;
    }

    public void setNoOfProduct(String noOfProduct) {
        this.noOfProduct = noOfProduct;
    }

    public List<CartModel> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<CartModel> productlist) {
        this.productlist = productlist;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userContact='" + userContact + '\'' +
                ", orderItem='" + orderItem + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
