package com.sfbd.serviceforcebd.model;

public class DocOrder {
    private String userId;
    private String userName;
    private String userAddress;
    private String userContact;
    private String orderItem;
    private String orderDate;
    private String orderTime;
    private String isPlaced;
    private String DocName;
    private String Visit;

    private String curreDate;
    private String curreTime;
    private String orderId;

    public DocOrder() {
    }

    public DocOrder(String userId, String userName, String userAddress, String userContact, String orderItem, String orderDate, String orderTime, String isPlaced, String docName, String visit, String curreDate, String curreTime) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
        this.orderItem = orderItem;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.isPlaced = isPlaced;
        DocName = docName;
        Visit = visit;
        this.curreDate = curreDate;
        this.curreTime = curreTime;

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

    public String getIsPlaced() {
        return isPlaced;
    }

    public void setIsPlaced(String isPlaced) {
        this.isPlaced = isPlaced;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getVisit() {
        return Visit;
    }

    public void setVisit(String visit) {
        Visit = visit;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
