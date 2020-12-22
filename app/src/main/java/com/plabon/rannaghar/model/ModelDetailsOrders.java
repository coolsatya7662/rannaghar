package com.plabon.rannaghar.model;

public class ModelDetailsOrders {
    public String orderId;
    public String productId;
    public String orderStatus;
    public String productprice;
    public String qty;
    public String orderdate;
    public String productName;
    public String image;

    public ModelDetailsOrders() {
    }

    public ModelDetailsOrders(String orderId, String productId, String orderStatus, String productprice, String qty, String orderdate, String productName, String img) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderStatus = orderStatus;
        this.productprice = productprice;
        this.qty = qty;
        this.orderdate = orderdate;
        this.productName = productName;
        this.image = img;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
