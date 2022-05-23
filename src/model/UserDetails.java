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
public class UserDetails {
    
    private int id;
    private String userName;
    private String passWord;
    private int salesPermissions;
    private int purchasePermissions;
    private int inventoryPermissions;
    private int customersAndSuppliersPermissions;

    public UserDetails() {
    }

    public UserDetails(int id, String userName, String passWord, int salesPermissions, int purchasePermissions, int inventoryPermissions, int customersAndSuppliersPermissions) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.salesPermissions = salesPermissions;
        this.purchasePermissions = purchasePermissions;
        this.inventoryPermissions = inventoryPermissions;
        this.customersAndSuppliersPermissions = customersAndSuppliersPermissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getSalesPermissions() {
        return salesPermissions;
    }

    public void setSalesPermissions(int salesPermissions) {
        this.salesPermissions = salesPermissions;
    }

    public int getPurchasePermissions() {
        return purchasePermissions;
    }

    public void setPurchasePermissions(int purchasePermissions) {
        this.purchasePermissions = purchasePermissions;
    }

    public int getInventoryPermissions() {
        return inventoryPermissions;
    }

    public void setInventoryPermissions(int inventoryPermissions) {
        this.inventoryPermissions = inventoryPermissions;
    }

    public int getCustomersAndSuppliersPermissions() {
        return customersAndSuppliersPermissions;
    }

    public void setCustomersAndSuppliersPermissions(int customersAndSuppliersPermissions) {
        this.customersAndSuppliersPermissions = customersAndSuppliersPermissions;
    }
    
    
    
}
