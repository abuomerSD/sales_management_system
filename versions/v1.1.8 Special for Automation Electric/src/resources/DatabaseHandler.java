/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.InvoiceDetails;
import model.InvoiceHeader;
import model.Product;
import java.util.ArrayList;
import java.util.List;
import model.InventoryAdjustment;
import model.ProductMovement;
import model.PurchaseInvoiceDetails;
import model.PurchaseInvoiceHeader;
import model.Supplier;

/**
 *
 * @author eltayeb
 */
public class DatabaseHandler {
    
    static Connection con = null;
    static Statement statement = null;
    static ResultSet rs = null;
    
    
    public static Connection getConnection(){
       
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            
            String url = "jdbc:sqlite:Database.db";
            
            //Class.forName("com.mysql.jdbc.Driver'").newInstance();
//            String url = "jdbc:mysql://localhost/";
//            String username = "root";
//            String password = "";
            con = DriverManager.getConnection(url);
            
        } catch (SQLException ex) {
            //AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }    //catch (ClassNotFoundException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return con;
    }
    
    public static Boolean execUpdate(String sql){
        try
        {
            statement = con.createStatement();
            statement.execute(sql); 
            //con.close();
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
           AlertMaker.showErrorAlert(ex.getMessage());
            return false;
        }
        
        finally{
            //try{con.close();} catch(Exception ex){AlertMaker.showErrorAlert(ex.getMessage());}
            //try{statement.close();} catch(Exception ex){AlertMaker.showErrorAlert(ex.getMessage());}
            //try{rs.close();} catch(Exception ex){AlertMaker.showErrorAlert(ex.getMessage());}
        }
        
        
        //return true;
    }
    
    public static ResultSet execQuery(String sql){
        try
        {
            Statement statement = con.createStatement(); 
            rs = statement.executeQuery(sql);
            //con.close();
        }
        catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
            //AlertMaker.showErrorAlert("can't execute query");
        }
        
        finally{
            //try{con.close();} catch(Exception ex){AlertMaker.showErrorAlert(ex.getMessage());}
            //try{statement.close();} catch(Exception ex){AlertMaker.showErrorAlert(ex.getMessage());}
            //try{rs.close();} catch(Exception ex){AlertMaker.showErrorAlert(ex.getMessage());}
        }
        return rs;
    }
    
    
    public static void createTables(){
        //createDataBase(); for mysql DataBase
        createProductsTable();
        createInvoiceHeaderTable();
        createInvoiceDetailsTable();
        createCustomerTable();
        createProductMovementTable();
        createPurchaseInvoiceHeaderTable();
        createPurchaseInvoiceDetailsTable();
        createSupplierTable();
        createInventoryAdjustmentTable();
        createDollarValueTable();
        insertDefaultDollarValue();
    }
    
    
    // a method to add a single product
    public static Product addProduct(Product product){
        String sql = "INSERT INTO tbProduct (productCode,productName,productQuantity,productCost,productPrice) "
                + "VALUES ('"
                + product.getProductCode() + "','"
                + product.getProductName() + "',"
                + product.getProductQuantity() + ","
                + product.getProductCost() + ","
                + product.getProductPrice() + ");";
        con = getConnection();
        
        
        if(execUpdate(sql)){
         System.out.println("Product Added");
         AlertMaker.showInformationAlert(product.getProductName()+" Added");
        }
        
        
        return product;
    }

    private static void createProductsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbProduct("
                + "productCode VARCHAR(200)  NOT NULL,"
                + "productName VARCHAR(200)  NOT NULL,"
                + "productQuantity DOUBLE,"
                + "productCost DOUBLE,"
                + "productPrice DOUBLE,"
                + "UNIQUE(productCode),"
                + "UNIQUE(productName) ) ;";
        
        con = getConnection();
        execUpdate(sql);
        System.out.println("Product Table Created!");
    }
    
    private static void createInvoiceHeaderTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbInvoiceHeader("
                + "invoiceNumber INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + "date VARCHAR(50),"
                + "customerID INT,"
                + "customerName VARCHAR(200),"
                + "payType VARCHAR(50),"
                + "tax DOUBLE,"
                + "discount DOUBLE,"
                + "invoiceTotal DOUBLE,"
                + "invoiceTotalCost DOUBLE);";
        
        con = getConnection();
        execUpdate(sql);
        System.out.println("InvoiceHeader Table Created!");
    }
    
    private static void createInvoiceDetailsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS 'tbInvoiceDetails' ("
                + "'invoiceNumber'	INT,"
                + "'date'	VARCHAR(50),"
                + "'colNumber'	INTEGER,"
                + "'customerID'	INT,"
                + "'productName'	VARCHAR(200),"
                + "'price'	DOUBLE,"
                + "'quantity'	DOUBLE,"
                + "'productTotalSale'	DOUBLE);";
        
        con = getConnection();
        execUpdate(sql);
        System.out.println("InvoiceDetails Table Created!");
    }
    
    private static void createCustomerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbCustomer("
                + "id INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR(200)  NOT NULL,"
                + "totalPurchase DOUBLE,"
                + "UNIQUE (name));";
        
        con = getConnection();
        System.out.println(sql);
        
        if(execUpdate(sql))
            System.out.println("Customer Table Created!");
        
    }
    
    private static void createProductMovementTable(){
        String sql = "CREATE TABLE IF NOT EXISTS'tbProductMovement' (" +
            "	'id'	INTEGER NOT NULL," +
            "	'date'	VARCHAR(50)," +
            "	'productCode'	VARCHAR(200)," +
            "	'productName'	VARCHAR(200)," +
            "	'salesInvoiceID'	INTEGER," +
            "	'purchaseInvoiceID'	INTEGER," +
            "	'inQuantity'	DOUBLE," +
            "	'outQuantity'	DOUBLE," +
            "	'currentQuantity'	DOUBLE," +
            "	'Details'	VARCHAR(50)," +
            "	PRIMARY KEY('id' AUTOINCREMENT)" +
            ");";
        
        con = getConnection();
        execUpdate(sql);
    }
    
    private static void createPurchaseInvoiceHeaderTable()
    {
        String sql = "CREATE TABLE  IF NOT EXISTS'tbPurchaseInvoiceHeader' ("
                + "'id' INTEGER NOT NULL,"
                + "'SupplierID'	INTEGER,"
                + "'SupplierName'	varchar(100),"
                + "'date'	varchar(50),"
                + "'Total'	double,"
                + "PRIMARY KEY('id' AUTOINCREMENT));";
        
        con = getConnection();
        execUpdate(sql);
    }
    
    private static void createPurchaseInvoiceDetailsTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS'tbPurchaseInvoiceDetails' ("
                + "'id'	INTEGER,"
                + "'colNumber'	INTEGER,"
                + "'productCode'	varchar(200),"
                + "'productName'	varchar(200),"
                + "'productQuantity'	DOUBLE,"
                + "'productCost'	DOUBLE,"
                + "'productTotal'	DOUBLE,"
                + "FOREIGN KEY('id') REFERENCES 'tbPurchaseInvoiceHeader'('id'));";
        con = getConnection();
        execUpdate(sql);
    }
    
    private static void createSupplierTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS 'tbSupplier' ('"
                + "id'	INTEGER NOT NULL,"
                + "'supplierName'	VARCHAR(200)  NOT NULL,"
                + "PRIMARY KEY('id' AUTOINCREMENT),"
                + "UNIQUE(supplierName));";
        con = getConnection();
        execUpdate(sql);
    }
    
    private static void createInventoryAdjustmentTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS 'tbInventoryAdjustment' (" +
                            "	'id'	INTEGER NOT NULL," +
                            "	'date'	VARCHAR(20)," +
                            "	'productCode'	VARCHAR(200)," +
                            "	'productName'	VARCHAR(200)," +
                            "	'adjustmentQTY'	DOUBLE," +
                            "	'productQtyAfterAdjustment'	DOUBLE," +
                            "	'details' VARCHAR(200)," +
                            "	PRIMARY KEY('id' AUTOINCREMENT)" +
                            ");";
        
        con = getConnection();
        execUpdate(sql);
        
    }
    
    private static void createDollarValueTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS 'tbDollarValue' (" +
                        "	'id'	INTEGER NOT NULL," +
                        "	'dollarValue'	Double," +
                        "	PRIMARY KEY('id' AUTOINCREMENT)" +
                        ");";
        con  = getConnection();
        execUpdate(sql);
    }
    
    private static void insertDefaultDollarValue()
    {
        String sql = "INSERT INTO tbDollarValue (dollarValue) Select '1' Where not exists(select * from tbDollarValue where id='1')";
        con = getConnection();
        execUpdate(sql);
    }
    
    public static boolean addCustomer(Customer customer){
        boolean status = false;
        String sql = "INSERT INTO tbCustomer (name, totalPurchase) VALUES ("
                + "'" + customer.getName() + "',"
                + customer.getTotalPurchase()+");";
        System.out.println(sql);
        con = getConnection();
        if(execUpdate(sql)){
            status = true;
        }
        
        
        return status;
    }
    
    public static int getLastInvoiceNumber() throws SQLException{
        int lastInvoiceNumber = 0 ;
        String sql = "SELECT * " +
                      "    FROM    tbInvoiceHeader\n" +
                      "    WHERE   invoiceNumber = (SELECT MAX(invoiceNumber)  FROM tbInvoiceHeader);";
        
        con = getConnection();
        ResultSet rs = execQuery(sql);
        while(rs.next()){
            lastInvoiceNumber = rs.getInt("invoiceNumber");
        }
        
        return lastInvoiceNumber;
    }
    
    public static ObservableList getCusmtomerList(){
        ObservableList customerArrayList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM tbCustomer";
        con = getConnection();
        
        try{
            ResultSet rs = execQuery(sql);
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setTotalPurchase(rs.getDouble("totalPurchase"));
                
                System.out.println(customer.getName());
                customerArrayList.add(customer);
            }
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
     
        
        return customerArrayList;
    }
    
    public static ObservableList getProductsList(){
        ObservableList productsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tbProduct";
        
        con = getConnection();
        
        try{
            ResultSet rs = execQuery(sql);
            
            while(rs.next()){
                Product product = new Product();
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductQuantity(rs.getDouble("productQuantity"));
                product.setProductCost((double)Math.round(rs.getDouble("productCost")));
                product.setProductPrice((double)Math.round(rs.getDouble("productPrice")));
                
                System.out.println(product.getProductName());
                productsList.add(product);
            }
        } 
        catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return productsList;
    }
    
    public static ObservableList getProductsListWithDollarValue(){
        ObservableList productsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tbProduct";
        
        con = getConnection();
        
        try{
            ResultSet rs = execQuery(sql);
            
            while(rs.next()){
                //DecimalFormat decimalFormat = new DecimalFormat("#,###");
                double price = (double) Math.round(rs.getDouble("productPrice") * DatabaseHandler.getDollarVlaue());
                //String priceString = decimalFormat.format(price);
                Product product = new Product();
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductQuantity(rs.getDouble("productQuantity"));
                product.setProductCost((double) Math.round(rs.getDouble("productCost")));
                product.setProductPrice(price);
                
                System.out.println(product.getProductName());
                System.out.println(DatabaseHandler.getDollarVlaue());
                System.out.println(product.getProductName() +" price = " + price);
                productsList.add(product);
            }
        } 
        catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return productsList;
    }
    
    public static void saveInvoice(int number, String date, int customerID, String customerName, String payType, double tax, double discount, double invoiceTotal, double invoiceTotalCost, ObservableList<InvoiceDetails> invoiceDetailsList){
        InvoiceHeader invoiceHeader = new InvoiceHeader(number, null, customerID, customerName, tax, discount, invoiceTotal);
        
        
        String invoiceHeaderSQL = "INSERT INTO tbInvoiceHeader VALUES ("
                                                            + number + ",'" 
                                                            + date +   "'," 
                                                            + customerID + ",'" 
                                                            + customerName + "','" 
                                                            + payType + "',"
                                                            + tax + ","
                                                            + discount + ","
                                                            + invoiceTotal + ","
                                                            + invoiceTotalCost +");";
        
        
        
        try
        {
            con = getConnection();
            System.out.println(invoiceHeaderSQL);
            execUpdate(invoiceHeaderSQL);
            
            for(int i = 0; i<invoiceDetailsList.size();i++)
            {
                InvoiceDetails invoiceDetails = invoiceDetailsList.get(i);
                String invoiceDetailsSQL = "INSERT INTO tbInvoiceDetails VALUES ("
                        + number + ",'"
                        + date + "',"
                        + invoiceDetails.getColNumber() +","
                        + customerID + ",'"
                        + invoiceDetails.getProductName() + "',"
                        + invoiceDetails.getProductPrice() + ","
                        + invoiceDetails.getProductQuantity() +","
                        + invoiceDetails.getProductTotalSale()+ ");";
                System.out.println(invoiceDetailsSQL);
                execUpdate(invoiceDetailsSQL);
            }
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    public static int getCustomerID(String customerName) throws SQLException{
        int id = 0;
        String sql = "SELECT id FROM tbCustomer WHERE name = '"+ customerName +"';";
        con = getConnection();
        ResultSet rs = execQuery(sql);
        
        while (rs.next()) {
            id = rs.getInt("id");
        }
        
        
        return id;
    }
    
    public static void dicreaseProductsQtyFromDataBase(ObservableList productsList){
        double oldProductQTY = 0.0;
        double newProductQTY = 0.0;
        String getProductCurrentQtySQL = "";
        String updateProductQtySQL = "";
        con = getConnection();
        
        try
        {
          for(int i = 0; i< productsList.size(); i++){
            InvoiceDetails product = (InvoiceDetails) productsList.get(i);
            getProductCurrentQtySQL = "SELECT productQuantity FROM tbProduct Where productName = '"
                    + product.getProductName() +"'";
            ResultSet rs  =execQuery(getProductCurrentQtySQL);
            
            while(rs.next()){
                oldProductQTY = rs.getDouble("productQuantity");
                newProductQTY = oldProductQTY - product.getProductQuantity();
                
                
                updateProductQtySQL = "UPDATE tbProduct SET productQuantity = "
                        + newProductQTY + " WHERE productName = '"
                        + product.getProductName() +"';";
                
                execUpdate(updateProductQtySQL);
                
            }
        }  
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }

    private static void createDataBase() {
        //To change body of generated methods, choose Tools | Templates.
        String sql = "CREATE DATABASE IF NOT EXISTS StepSales;";
        con = getConnection();
        execUpdate(sql);
    }
    
    public static ObservableList<InvoiceHeader> getSalesInvoicesList(){
        ObservableList<InvoiceHeader> salesInvoiceList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM tbInvoiceHeader;";
        
        con = getConnection();
        ResultSet rs = execQuery(sql);
        
        try
        {
            while(rs.next()){
                InvoiceHeader invoiceHeader = new InvoiceHeader();
                
                invoiceHeader.setCumstomerID(rs.getInt("customerID"));
                invoiceHeader.setCustomerName(rs.getString("customerName"));
                invoiceHeader.setDate(rs.getString("date"));
                invoiceHeader.setDiscount(rs.getDouble("discount"));
                invoiceHeader.setNumber(rs.getInt("invoiceNumber"));
                invoiceHeader.setPayType(rs.getString("payType"));
                invoiceHeader.setTax(rs.getDouble("tax"));
                invoiceHeader.setTotal(rs.getDouble("invoiceTotal"));
                invoiceHeader.setTotalCost(rs.getDouble("invoiceTotalCost"));
                
                salesInvoiceList.add(invoiceHeader);
            }
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return salesInvoiceList;
    }
    
    public static List<InvoiceDetails> getSalesInvoiceDetailsList(int salesInvoiceID){
//        InvoiceDetails invoiceDetails = null;
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
        String sql = "SELECT * FROM tbInvoiceDetails WHERE invoiceNumber = "+ salesInvoiceID + " ;";
        con = getConnection();
        try
        {
            rs = execQuery(sql);
            while(rs.next())
            {
                InvoiceDetails invoiceDetails = new InvoiceDetails();
                invoiceDetails.setInvoiceNumber(rs.getInt("invoiceNumber"));
                invoiceDetails.setColNumber(rs.getInt("colNumber"));
                invoiceDetails.setProductName(rs.getString("productName"));
                invoiceDetails.setProductPrice(rs.getDouble("price"));
                invoiceDetails.setProductQuantity(rs.getDouble("quantity"));
                invoiceDetails.setProductTotalSale(rs.getDouble("productTotalSale"));
                invoiceDetailsList.add(invoiceDetails);
            }
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return invoiceDetailsList;
    }

    public static void deleteSalesInvoice(int salesInvoiceID) {
        String deleteHeaderSQL = "DELETE from tbInvoiceHeader WHERE invoiceNumber = "+ salesInvoiceID + ";";
        String deleteDetailsSQL = "DELETE from tbInvoiceDetails WHERE invoiceNumber = " + salesInvoiceID + ";";
        
        // get the products current quantity
        
        String getInvoiceDetailsItems = "SELECT * FROM tbInvoiceDetails WHERE invoiceNumber = " + salesInvoiceID + ";";
        
        
        try
        {
            con = getConnection();
            ResultSet rs = execQuery(getInvoiceDetailsItems);
            while(rs.next())
            {
               InvoiceDetails invoiceDetails = new InvoiceDetails(); 
               invoiceDetails.setInvoiceNumber(rs.getInt("invoiceNumber"));
               invoiceDetails.setProductName(rs.getString("productName"));
               invoiceDetails.setProductPrice(rs.getDouble("price"));
               invoiceDetails.setProductQuantity(rs.getDouble("quantity"));
               invoiceDetails.setProductTotalSale(rs.getDouble("productTotalSale"));
               
               double oldValue = 0;
               ResultSet oldValueRs = execQuery("SELECT * FROM tbProduct WHERE productName = '"+ invoiceDetails.getProductName() + "';");
               
               while(oldValueRs.next())
               {
                   oldValue = oldValueRs.getDouble("productQuantity");
                   double newValue = oldValue + invoiceDetails.getProductQuantity();
               
                    String updateItemQuantitySQL = "update tbProduct SET productQuantity = " + newValue + " WHERE productName = '"
                            + invoiceDetails.getProductName() + "' ;";
                     System.out.println(updateItemQuantitySQL);
                     execUpdate(updateItemQuantitySQL);
               }
               
               
                
               
               
            }
            
            if(execUpdate(deleteHeaderSQL) & execUpdate(deleteDetailsSQL))
            {
                AlertMaker.showInformationAlert("تم حذف الفاتورة رقم"+ salesInvoiceID);
            }
            else
            {
                AlertMaker.showErrorAlert("خطأ في حذف الفاتورة");
            }
            
            
            
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void updateProductValues(String productName){
        
    }
    
    

    public static Integer getLastPurchaseInvoiceNumber() {
        int lastInvoiceNumber = 0 ;
        String sql = "SELECT * FROM tbPurchaseInvoiceHeader WHERE id = (SELECT MAX(id)  FROM tbPurchaseInvoiceHeader)"; 
        
        con = getConnection();
        try{
            ResultSet rs = execQuery(sql);
        while(rs.next()){
            lastInvoiceNumber = rs.getInt("id");
        }
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return lastInvoiceNumber;
    }

    public static ObservableList getSuppliersList() {
        ObservableList supplierArrayList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM tbSupplier";
        con = getConnection();
        
        try{
            ResultSet rs = execQuery(sql);
            while(rs.next()){
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("supplierName"));
                
                
                System.out.println(supplier.getName());
                supplierArrayList.add(supplier);
            }
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        return supplierArrayList;
    }
    
    
    public static void savePurchaseInvoice(int invoiceID, String date,int supplierID, String supplierName, double purchaseInvoiceTotal, ObservableList<PurchaseInvoiceDetails> purchaseInvoiceDetailsList)
    {
        
        // inserting data to purchase invoice header table
        
        //int supplierID = getSupplierID(supplierName);
        PurchaseInvoiceHeader purchaseInvoiceHeader = new PurchaseInvoiceHeader(invoiceID, supplierID, supplierName, date, purchaseInvoiceTotal);
        
        String headerSQL = "INSERT INTO tbPurchaseInvoiceHeader (SupplierID,SupplierName,date,Total) VALUES ("
                + supplierID +",'"
                + supplierName + "','"
                + date +"',"
                + purchaseInvoiceTotal +");";
        
        System.out.println(headerSQL);
        con = getConnection();
        execUpdate(headerSQL);
        
        // inserting data to purchase invoice details table
        
        for(int i = 0; i<purchaseInvoiceDetailsList.size(); i++)
        {
            PurchaseInvoiceDetails purchaseInvoiceDetails = purchaseInvoiceDetailsList.get(i);
            purchaseInvoiceDetails.setProductCode(getProductCode(purchaseInvoiceDetails.getProductName())); //adding the  product code here because it is not defined at purchase controller
            String detailsSQL = "INSERT INTO tbPurchaseInvoiceDetails VALUES ("
                + invoiceID + ","
                + purchaseInvoiceDetails.getColNumber() +",'"
                + purchaseInvoiceDetails.getProductCode() + "','"
                + purchaseInvoiceDetails.getProductName() +"',"
                + purchaseInvoiceDetails.getProductQTY() + ","
                + purchaseInvoiceDetails.getProductCost() + ","
                + purchaseInvoiceDetails.getProductTotalCost()
                + ") ;";
            
            System.out.println(detailsSQL);
            
            execUpdate(detailsSQL);
            
            // update products table and set the  new quantities
 
            double oldQTY = getProductQTY(purchaseInvoiceDetails.getProductName());
            double newQTY = oldQTY + purchaseInvoiceDetails.getProductQTY();
        
            String updateQTySQl = "Update tbProduct SET productQuantity = "
                    + newQTY 
                    + " WHERE productName = '"
                    + purchaseInvoiceDetails.getProductName() 
                    +"';"; 
            System.out.println(updateQTySQl);
            execUpdate(updateQTySQl);
            
            // update product cost with the  new value
            
            double oldProductQTY = getProductQTY(purchaseInvoiceDetails.getProductName());
            
            double oldCost = getProductCost(purchaseInvoiceDetails.getProductName());
            System.out.println("oldCost = "+ oldCost);
            double oldCostSum = oldProductQTY * oldCost;
            System.out.println("oldCostSum = "+ oldCostSum);
            
            
            double newCostFromPurchaseInvoice = purchaseInvoiceDetails.getProductCost();
            double newProductQtyFromPurchaseInvoice = purchaseInvoiceDetails.getProductQTY();
            double newCostSum = newCostFromPurchaseInvoice * newProductQtyFromPurchaseInvoice;
            System.out.println("newCostSum = "+ newCostSum);
            
            
            System.out.println("current product Cost = " +purchaseInvoiceDetails.getProductCost());
            
            double newCost = (oldCostSum + newCostSum) / (oldProductQTY + newProductQtyFromPurchaseInvoice) ;  
            System.out.println("newCost = "+ newCost);
            
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            
            String updateCostSQL = "UPDATE tbProduct SET productCost = " +
                    decimalFormat.format(newCost) +" WHERE productName = '"
                    + purchaseInvoiceDetails.getProductName()
                    + "' ;";
            
            System.out.println(updateCostSQL);
            execUpdate(updateCostSQL);
            
        }
        
        
        
        
    }
    
    
    public static int getSupplierID(String name)
    {
        int id= 0;
        String sql = "SELECT * FROM tbSupplier";
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
            {
                id = rs.getInt("id");
            }
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return id;
    }
    
    public static double getProductQTY(String productName)
    {
        double qty=0; 
        String sql = "SELECT * FROM tbProduct WHERE productName = '"+ productName +"';";
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
                qty = rs.getDouble("productQuantity");
        } 
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        return qty;
    }
    
    public static double getProductPrice(String productName)
    {
        double price=0; 
        String sql = "SELECT * FROM tbProduct WHERE productName = '"+ productName +"';";
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
                price = rs.getDouble("productPrice");
        } 
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        return price;
    }
    
    public static String getproductName(String productCode)
    {
        String name = "";
        String sql = "SELECT * from tbProduct WHERE productCode ='"+ productCode+"';";
        
        con = getConnection();
        try
        {
            ResultSet rs1 = execQuery(sql);
            while(rs1.next())
                name = rs.getString("productName");
        } 
        catch(Exception ex)
        {
           AlertMaker.showErrorAlert(ex.getMessage());
           ex.printStackTrace();
        }
        
        return name;
        
    }
    
    public static double getProductCost(String productName)
    {
        double cost = 0;
        
        String sql = "SELECT * FROM tbProduct WHERE productName = '"+ productName +"' ;";
        
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
                cost = rs.getDouble("productCost");
        } 
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return cost;
    }
    
    public static String getProductCode(String productName)
    {
        String code = "";
        
        String sql = "SELECT * FROM tbProduct WHERE productName = '"+productName+"';";
        
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
                code = rs.getString("productCode");
        } 
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return code;
    }
    
    public static void deleteAllProducts()
    {
        String sql = "DELETE FROM tbProduct;";
        
        con = getConnection();
        if(execUpdate(sql))
            AlertMaker.showInformationAlert("تم حذف جميع الاصناف بنجاح");
        
    }
    
    /**
     * this method adds the product movement details to product movement table
     * using the ProductMovement Object.
     * it used at:
     * 1- add initial product quantity .
     * 2- add initial multi products quantity .
     * 3- sales invoice after save .
     * 4- purchase invoice after save.
     * @param productMovement 
     */
    
    public static void addProductMovement(ProductMovement productMovement)
    {
     String sql = "INSERT INTO tbProductMovement (date,productCode,productName,salesInvoiceID,purchaseInvoiceID,inQuantity,outQuantity,currentQuantity,Details) VALUES ("
             + "'" + productMovement.getDate() +"',"
             + "'" + productMovement.getProductCode() + "',"
             + "'" + productMovement.getProdcutName() + "',"
             + productMovement.getSalesInvoiceID() + ","
             + productMovement.getPurchaseInvoiceID() + ","
             + productMovement.getInQuantity() + ","
             + productMovement.getOutQuantity() + ","
             + productMovement.getCurrentQuantity() + ",'"
             + productMovement.getDetails() + "');";
     
     con = getConnection();
        execUpdate(sql);
    }
    
    public static void addSupplier(String supplierName)
    {
        String sql = "INSERT INTO tbSupplier (supplierName) VALUES ('" + supplierName + "');";
        
        con = getConnection();
        execUpdate(sql);
    }
    
    public static void addInventoryAdjustment(InventoryAdjustment inventoryAdjustment)
    {
        String sql = "INSERT INTO tbInventoryAdjustment (date, productCode, productName, adjustmentQTY, productQtyAfterAdjustment, details) VALUES ('"
                + inventoryAdjustment.getDate() +"','"
                + inventoryAdjustment.getProductCode() +"','"
                + inventoryAdjustment.getProductName() +"',"
                + inventoryAdjustment.getAdjustmentQTY() +","
                + inventoryAdjustment.getProductQtyAfterAdjustment() +",'"
                + inventoryAdjustment.getDetails() +"');";
        
        String updateProductTableSQL = "UPDATE tbProduct SET productQuantity = "
                + inventoryAdjustment.getProductQtyAfterAdjustment() +" WHERE"
                + " productCode = '"+inventoryAdjustment.getProductCode()+"';";
        
        con = getConnection();
        execUpdate(sql);
        execUpdate(updateProductTableSQL);
     
    }
    
    public static void editProduct(double cost, double price, String code)
    {
        String sql = "UPDATE tbProduct SET productCost = " + cost +","
                + "productPrice = " + price + " WHERE productCode = '"
                + code + "' ;";
        
        con = getConnection();
        execUpdate(sql);
    }
    
    public static void updateDollarValue(double dollarValue)
    {
        String sql = "UPDATE tbDollarValue SET dollarValue = " + dollarValue +
                " WHERE id = 1;";
        System.out.println(sql);
        con = getConnection();
        execUpdate(sql);
    }

    private static double getDollarVlaue() {
        double dollarValue = 0;
        
        String sql = "SELECT * FROM tbDollarValue WHERE id = 1";
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
                dollarValue = rs.getDouble("dollarValue");
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return dollarValue;
    }
}
