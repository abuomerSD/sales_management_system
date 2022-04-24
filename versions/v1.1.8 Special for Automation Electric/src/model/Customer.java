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
public class Customer {
    private String id;
    private String name;
    private double totalPurchase;

    public Customer(String id, String name, double totalPurchase) {
        this.id = id;
        this.name = name;
        this.totalPurchase = totalPurchase;
    }

    public Customer() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }
    
    
    
}
