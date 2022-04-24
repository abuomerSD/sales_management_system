/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author eltayeb
 */
public class PurchaseInvoiceDetails {
    
    private int id;
    private int colNumber;
    private String productCode;
    private String productName;
    private double productQTY;
    private double productCost;
    private double productTotalCost;

    public PurchaseInvoiceDetails() {
    }

    public PurchaseInvoiceDetails(int id, int colNumber, String productCode, String productName, double productQTY, double productCost, double productTotalCost) {
        this.id = id;
        this.colNumber = colNumber;
        this.productCode = productCode;
        this.productName = productName;
        this.productQTY = productQTY;
        this.productCost = productCost;
        this.productTotalCost = productTotalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
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

    public double getProductQTY() {
        return productQTY;
    }

    public void setProductQTY(double productQTY) {
        this.productQTY = productQTY;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public double getProductTotalCost() {
        return productTotalCost;
    }

    public void setProductTotalCost(double productTotalCost) {
        this.productTotalCost = productTotalCost;
    }

   
    
    
    
}
