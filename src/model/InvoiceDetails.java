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
public class InvoiceDetails {
    private int invoiceNumber;
    private int colNumber;
    private String productName;
    private double productPrice;
    private double productQuantity;
    private double productTotalSale;

    public InvoiceDetails() {
    }

    public InvoiceDetails(int invoiceNumber, int colNumber, String productName, double productPrice, double productQuantity, double productTotalSale) {
        this.invoiceNumber = invoiceNumber;
        this.colNumber = colNumber;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalSale = productTotalSale;
    }
    
    

    

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }
    
    

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductTotalSale() {
        return productTotalSale;
    }

    public void setProductTotalSale(double productTotalSale) {
        this.productTotalSale = productTotalSale;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" + "invoiceNumber=" + invoiceNumber + ", colNumber=" + colNumber + ", productName=" + productName + ", productPrice=" + productPrice + ", productQuantity=" + productQuantity + ", productTotalSale=" + productTotalSale + '}';
    }
    
    
    
    
}
