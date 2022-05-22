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
    String productCode;
    String prodcutName;
    int salesInvoiceID;
    int purchaseInvoiceID;
    double inQuantity;
    double outQuantity;
    double currentQuantity;
    String details;

    public ProductMovement() {
    }

    public ProductMovement(int id, String date, String ProductCode, String ProdcutName, int salesInvoiceID, int purchaseInvoiceID, double inQuantity, double outQuantity, double currentQuantity, String Details) {
        this.id = id;
        this.date = date;
        this.productCode = ProductCode;
        this.prodcutName = ProdcutName;
        this.salesInvoiceID = salesInvoiceID;
        this.purchaseInvoiceID = purchaseInvoiceID;
        this.inQuantity = inQuantity;
        this.outQuantity = outQuantity;
        this.currentQuantity = currentQuantity;
        this.details = Details;
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
        return productCode;
    }

    public void setProductCode(String ProductCode) {
        this.productCode = ProductCode;
    }

    public String getProdcutName() {
        return prodcutName;
    }

    public void setProdcutName(String ProdcutName) {
        this.prodcutName = ProdcutName;
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
        return details;
    }

    public void setDetails(String Details) {
        this.details = Details;
    }

    @Override
    public String toString() {
        return "ProductMovement{" + "id=" + id + ", date=" + date + ", ProductCode=" + productCode + ", ProdcutName=" + prodcutName + ", salesInvoiceID=" + salesInvoiceID + ", purchaseInvoiceID=" + purchaseInvoiceID + ", inQuantity=" + inQuantity + ", outQuantity=" + outQuantity + ", currentQuantity=" + currentQuantity + ", Details=" + details + '}';
    }
    
    
    
    
    
}
