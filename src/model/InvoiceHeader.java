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
public class InvoiceHeader {
    private int number;
    private String date;
    private int cumstomerID;
    private String customerName;
    private String payType;
    private double tax;
    private double discount;
    private double total;
    private double totalCost;

    public InvoiceHeader(int number, String date, int cumstomerID, String customerName, double tax, double discount, double total) {
        this.number = number;
        this.date = date;
        this.cumstomerID = cumstomerID;
        this.customerName = customerName;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }

    public InvoiceHeader(int number, String date, int cumstomerID, String customerName, String payType, double tax, double discount, double total, double totalCost) {
        this.number = number;
        this.date = date;
        this.cumstomerID = cumstomerID;
        this.customerName = customerName;
        this.payType = payType;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.totalCost = totalCost;
    }
    
    

    public InvoiceHeader() {
        
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCumstomerID() {
        return cumstomerID;
    }

    public void setCumstomerID(int cumstomerID) {
        this.cumstomerID = cumstomerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "number=" + number + ", date=" + date + ", cumstomerID=" + cumstomerID + ", customerName=" + customerName + ", payType=" + payType + ", tax=" + tax + ", discount=" + discount + ", total=" + total + ", totalCost=" + totalCost + '}';
    }
    
    
    
}
