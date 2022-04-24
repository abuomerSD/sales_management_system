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
public class PurchaseInvoiceHeader {
    
    private int id;
    private int supplierID;
    private String supplierName;
    private String date;
    private Double purchaeInvoiceTotalCost;

    public PurchaseInvoiceHeader() {
    }

    public PurchaseInvoiceHeader(int id, int supplierID, String supplierName, String date, Double purchaeInvoiceTotalCost) {
        this.id = id;
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.date = date;
        this.purchaeInvoiceTotalCost = purchaeInvoiceTotalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPurchaeInvoiceTotalCost() {
        return purchaeInvoiceTotalCost;
    }

    public void setPurchaeInvoiceTotalCost(Double purchaeInvoiceTotalCost) {
        this.purchaeInvoiceTotalCost = purchaeInvoiceTotalCost;
    }

    @Override
    public String toString() {
        return "PurchaseInvoiceHeader{" + "id=" + id + ", supplierID=" + supplierID + ", supplierName=" + supplierName + ", date=" + date + ", purchaeInvoiceTotalCost=" + purchaeInvoiceTotalCost + '}';
    }
    
    
    
}
