package com.example.serviceforcebd.model;

public class Order {
    private String userId;
    private String userName;
    private String userAddress;
    private String userContact;
    private String orderItem;
    private String orderDate;
    private String orderTime;
    private String orderId;
    private boolean isPlaced;



    public Order() {
    }

    public Order(String userId, String userName, String userAddress, String userContact, String orderItem, String orderDate, String orderTime, boolean isPlaced) {
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
                 String userContact, String orderItem,
                 String orderDate, String orderTime, String orderId, boolean isPlaced) {
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

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
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
