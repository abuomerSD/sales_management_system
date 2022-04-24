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
public class ProductMovement {
    int id;
    String date;
    String ProductCode;
    String ProdcutName;
    int salesInvoiceID;
    int purchaseInvoiceID;
    double inQuantity;
    double outQuantity;
    double currentQuantity;
    String Details;

    public ProductMovement() {
    }

    public ProductMovement(int id, String date, String ProductCode, String ProdcutName, int salesInvoiceID, int purchaseInvoiceID, double inQuantity, double outQuantity, double currentQuantity, String Details) {
        this.id = id;
        this.date = date;
        this.ProductCode = ProductCode;
        this.ProdcutName = ProdcutName;
        this.salesInvoiceID = salesInvoiceID;
        this.purchaseInvoiceID = purchaseInvoiceID;
        this.inQuantity = inQuantity;
        this.outQuantity = outQuantity;
        this.currentQuantity = currentQuantity;
        this.Details = Details;
    }

    public double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(double currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }

    public String getProdcutName() {
        return ProdcutName;
    }

    public void setProdcutName(String ProdcutName) {
        this.ProdcutName = ProdcutName;
    }

    public int getSalesInvoiceID() {
        return salesInvoiceID;
    }

    public void setSalesInvoiceID(int salesInvoiceID) {
        this.salesInvoiceID = salesInvoiceID;
    }

    public int getPurchaseInvoiceID() {
        return purchaseInvoiceID;
    }

    public void setPurchaseInvoiceID(int purchaseInvoiceID) {
        this.purchaseInvoiceID = purchaseInvoiceID;
    }

    public double getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(double inQuantity) {
        this.inQuantity = inQuantity;
    }

    public double getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(double outQuantity) {
        this.outQuantity = outQuantity;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String Details) {
        this.Details = Details;
    }
    
    
    
    
    
}
