package com.tonyjstudio.cuahangdienthoaionline.model;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    int productPrice;
    String productName;
    String productImage;
    String productDescribe;
    int productTypeId;

    public Product() {
    }

    public Product(int id, int productPrice, String productName, String productImage, String productDescribe, int productTypeId) {
        this.id = id;
        this.productPrice = productPrice;
        this.productName = productName;
        this.productImage = productImage;
        this.productDescribe = productDescribe;
        this.productTypeId = productTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }
}
