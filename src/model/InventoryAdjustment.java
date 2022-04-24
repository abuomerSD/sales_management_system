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
public class InventoryAdjustment {
    
    private int id;
    private String date;
    private String productCode;
    private String productName;
    private double adjustmentQTY;
    private double productQtyAfterAdjustment;
    private String details;

    public InventoryAdjustment() {
    }

    public InventoryAdjustment(int id, String date, String productCode, String productName, double adjustmentQTY, double productQtyAfterAdjustment, String details) {
        this.id = id;
        this.date = date;
        this.productCode = productCode;
        this.productName = productName;
        this.adjustmentQTY = adjustmentQTY;
        this.productQtyAfterAdjustment = productQtyAfterAdjustment;
        this.details = details;
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

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getAdjustmentQTY() {
        return adjustmentQTY;
    }

    public void setAdjustmentQTY(double adjustmentQTY) {
        this.adjustmentQTY = adjustmentQTY;
    }

    public double getProductQtyAfterAdjustment() {
        return productQtyAfterAdjustment;
    }

    public void setProductQtyAfterAdjustment(double productQtyAfterAdjustment) {
        this.productQtyAfterAdjustment = productQtyAfterAdjustment;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "InventoryAdjustment{" + "id=" + id + ", date=" + date + ", productCode=" + productCode + ", productName=" + productName + ", adjustmentQTY=" + adjustmentQTY + ", productQtyAfterAdjustment=" + productQtyAfterAdjustment + ", details=" + details + '}';
    }

    
    
    
    
}
