package com.example.mercadocomadmin.Model;

public class OrderModel {

    private String ProductImageUrl;
    private String ProductName;
    private String ProductDescription;
    private String ProductPrice;
    private String ProductType;
    private String ProductStoke;
    private String ShopownerId;
    private String ProductId;
    private String UserId;
    private String ProductQty;

    private String house;
    private String area;
    private String city;
    private String district;
    private String pincode;
    private String contName;
    private String contNumber;
    private String contAltNumber;
    private String totalPrice;

    private String status;
    private String DeliveryBoyId;
    private String orderId;
    private String orderDate;
    private String orderDeliveryDate;
    private String temp1;
    private String temp2;

    public OrderModel(){}

    public OrderModel(String productImageUrl, String productName, String productDescription, String productPrice, String productType, String productStoke, String shopownerId, String productId, String userId, String productQty, String house, String area, String city, String district, String pincode, String contName, String contNumber, String contAltNumber, String totalPrice, String status, String deliveryBoyId, String orderId, String orderDate, String orderDeliveryDate, String temp1, String temp2) {
        ProductImageUrl = productImageUrl;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
        ProductType = productType;
        ProductStoke = productStoke;
        ShopownerId = shopownerId;
        ProductId = productId;
        UserId = userId;
        ProductQty = productQty;
        this.house = house;
        this.area = area;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
        this.contName = contName;
        this.contNumber = contNumber;
        this.contAltNumber = contAltNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        DeliveryBoyId = deliveryBoyId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderDeliveryDate = orderDeliveryDate;
        this.temp1 = temp1;
        this.temp2 = temp2;
    }

    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getProductStoke() {
        return ProductStoke;
    }

    public void setProductStoke(String productStoke) {
        ProductStoke = productStoke;
    }

    public String getShopownerId() {
        return ShopownerId;
    }

    public void setShopownerId(String shopownerId) {
        ShopownerId = shopownerId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProductQty() {
        return ProductQty;
    }

    public void setProductQty(String productQty) {
        ProductQty = productQty;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContName() {
        return contName;
    }

    public void setContName(String contName) {
        this.contName = contName;
    }

    public String getContNumber() {
        return contNumber;
    }

    public void setContNumber(String contNumber) {
        this.contNumber = contNumber;
    }

    public String getContAltNumber() {
        return contAltNumber;
    }

    public void setContAltNumber(String contAltNumber) {
        this.contAltNumber = contAltNumber;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryBoyId() {
        return DeliveryBoyId;
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        DeliveryBoyId = deliveryBoyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }
}
