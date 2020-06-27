package com.sfbd.serviceforcebd.model;

public class Order {
    private String userId;
    private String userName;
    private String userAddress;
    private String userContact;
    private String orderItem;
    private String orderDate;
    private String orderTime;
    private String orderId;
    private String isPlaced;
    private String timeStamp;

    public Order() {
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
