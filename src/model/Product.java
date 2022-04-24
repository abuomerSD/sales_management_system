/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author  eltayeb
 * 
 */
public class Product {
    
    private String productCode;
    private String productName;
    private Double productQuantity;
    private Double productCost;
    private Double productPrice;

    public Product(String productCode, String productName, Double productQuantity, Double productCost, Double productPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productCost = productCost;
        this.productPrice = productPrice;
    }

    public Product() {
       
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getProductCost() {
        return productCost;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product{" + "productCode=" + productCode + ", productName=" + productName + ", productQuantity=" + productQuantity + ", productCost=" + productCost + ", productPrice=" + productPrice + '}';
    }
    
    
    
}
