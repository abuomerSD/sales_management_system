/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
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
import model.UserDetails;

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
            
            if(con == null)
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
        }   
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
        createUsersDetailsTable();
        insertDefaultUser();
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
                + "phone VARCHAR(50),"
                + "UNIQUE (name));";
        
        con = getConnection();
        System.out.println(sql);
        
        if(execUpdate(sql))
            System.out.println("Customer Table Created!");
        
    }
    
    private static void createProductMovementTable(){
        String sql = "CREATE TABLE IF NOT EXISTS'tbProductMovement' (" +
            "	'id'	INTEGER," +
            "	'date'	VARCHAR(50)," +
            "	'productCode'	VARCHAR(200)," +
            "	'productName'	VARCHAR(200)," +
            "	'salesInvoiceID'	INTEGER," +
            "	'purchaseInvoiceID'	INTEGER," +
            "	'inQuantity'	DOUBLE," +
            "	'outQuantity'	DOUBLE," +
            "	'currentQuantity'	DOUBLE," +
            "	'Details'	VARCHAR(50)" +
            ");";

        
        con = getConnection();
        execUpdate(sql);
    }
    
    private static void createPurchaseInvoiceHeaderTable()
    {
        String sql = "CREATE TABLE  IF NOT EXISTS'tbPurchaseInvoiceHeader' ("
                + "'id' INTEGER,"
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
                + "phone VARCHAR(50),"
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
        String sql = "INSERT INTO tbCustomer (name, totalPurchase,phone) VALUES ("
                + "'" + customer.getName() + "',"
                + customer.getTotalPurchase() + ",'"
                + customer.getPhone() +"');";
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
                customer.setPhone(rs.getString("phone"));
                
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
                DecimalFormat decimalFormat = new DecimalFormat("#.0000");
                String cost = String.valueOf(decimalFormat.format(rs.getDouble("productCost")));
                String price = String.valueOf(decimalFormat.format(rs.getDouble("productPrice")));
                
                Product product = new Product();
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductQuantity(rs.getDouble("productQuantity"));
                product.setProductCost(Double.valueOf(cost));
                product.setProductPrice(Double.valueOf(price));
                
                //System.out.println(product.getProductName());
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
                     
                     // adding product Movement
                     ProductMovement productMovement = new ProductMovement();
                     productMovement.setCurrentQuantity(newValue);
                     productMovement.setDate(LocalDate.now().toString());
                     productMovement.setDetails("حذف فاتورة مبيعات رقم : "+ salesInvoiceID);
                     productMovement.setInQuantity(invoiceDetails.getProductQuantity());
                     productMovement.setOutQuantity(0);
                     productMovement.setProdcutName(invoiceDetails.getProductName());
                     productMovement.setProductCode(getProductCode(invoiceDetails.getProductName()));
                     productMovement.setPurchaseInvoiceID(0);
                     productMovement.setSalesInvoiceID(0);
                     addProductMovement(productMovement);
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
    
    public static void updateProductValues(String productName, double newQTY, double newCost){
        String qtySQL = "UPDATE tbProduct set productQuantity = "+ newQTY + " WHERE productName = '" + productName + "';";
        try
        {
            System.out.println(qtySQL);
            con = getConnection();
            if(execUpdate(qtySQL))
                //AlertMaker.showInformationAlert("تم تعديل العدد بنجاح");
                System.out.println("update done");
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    

    public static int getLastPurchaseInvoiceNumber() {
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
                supplier.setPhone(rs.getString("phone"));
                
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
        
        String headerSQL = "INSERT INTO tbPurchaseInvoiceHeader (id,SupplierID,SupplierName,date,Total) VALUES ("
                + invoiceID +","
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
            
            
            Product product = new Product();
            product.setProductName(purchaseInvoiceDetails.getProductName());
            product.setProductCode(purchaseInvoiceDetails.getProductCode());
            
            
            // fixing Zero Qty Products cost bug
            double newCost;
            if(isProductQtyZero(product))
                newCost = purchaseInvoiceDetails.getProductCost();
            else
                newCost = (oldCostSum + newCostSum) / (oldProductQTY + newProductQtyFromPurchaseInvoice) ;
                
            System.out.println("newCost = "+ newCost);
            
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            
            String updateCostSQL = "UPDATE tbProduct SET productCost = " +
                    decimalFormat.format(newCost) +" WHERE productName = '"
                    + purchaseInvoiceDetails.getProductName()
                    + "' ;";
            
            System.out.println(updateCostSQL);
            execUpdate(updateCostSQL);
            
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
            
            
            
        }
        
        
        
        
    }
    
    
    public static int getSupplierID(String name)
    {
        int id= 0;
        String sql = "SELECT * FROM tbSupplier where supplierName = '"+name+"';";
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
    
    public static void addSupplier(Supplier supplier)
    {
        String sql = "INSERT INTO tbSupplier (supplierName,phone) VALUES ('" 
                + supplier.getName() +"','"
                + supplier.getPhone()
                + "');";
        
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
        
        ProductMovement productMovement = new ProductMovement();
        productMovement.setDate(inventoryAdjustment.getDate());
        productMovement.setProductCode(inventoryAdjustment.getProductCode());
        productMovement.setProdcutName(inventoryAdjustment.getProductName());
        productMovement.setSalesInvoiceID(0);
        productMovement.setPurchaseInvoiceID(0);
        if (inventoryAdjustment.getAdjustmentQTY() > 0) {
            productMovement.setInQuantity(inventoryAdjustment.getAdjustmentQTY());
            productMovement.setOutQuantity(0);
        }
        else {
            productMovement.setInQuantity(0);
            productMovement.setOutQuantity(inventoryAdjustment.getAdjustmentQTY());
        }
        productMovement.setCurrentQuantity(inventoryAdjustment.getProductQtyAfterAdjustment());
        productMovement.setDetails(inventoryAdjustment.getDetails());
        
        con = getConnection();
        execUpdate(sql);
        execUpdate(updateProductTableSQL);
        addProductMovement(productMovement);
     
    }
    
    public static void editProduct(double cost,String name, double price, String code)
    {
        String sql = "UPDATE tbProduct SET productCost = " + cost +","
                + "productPrice = " + price + ", productName = '" + name 
                +"' WHERE productCode = '"
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

    public static double getDollarVlaue() {
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
    
    public static ObservableList getPurchaseInvoicesList()
    {
        ObservableList invoicesList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM tbPurchaseInvoiceHeader";
        
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next()){
                PurchaseInvoiceHeader purchaseInvoiceHeader = new PurchaseInvoiceHeader();
                purchaseInvoiceHeader.setDate(rs.getString("date"));
                purchaseInvoiceHeader.setId(rs.getInt("id"));
                purchaseInvoiceHeader.setPurchaeInvoiceTotalCost(rs.getDouble("Total"));
                purchaseInvoiceHeader.setSupplierID(rs.getInt("SupplierID"));
                purchaseInvoiceHeader.setSupplierName(rs.getString("SupplierName"));
                invoicesList.add(purchaseInvoiceHeader);
            }
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return invoicesList;
    }
    
    public static List<PurchaseInvoiceDetails> getPurchaseInvoicesDetails(int id)
    {
        List<PurchaseInvoiceDetails> invoicesDetailsList = new ArrayList<PurchaseInvoiceDetails>();
        String sql = "SELECT * FROM tbPurchaseInvoiceDetails WHERE id = "+ id + " ;";
        con = getConnection();
        System.out.println(sql);
        try
        {
            
            ResultSet rs = execQuery(sql);
            while(rs.next()){
                PurchaseInvoiceDetails purchaseInvoiceDetails = new PurchaseInvoiceDetails();
                purchaseInvoiceDetails.setColNumber(rs.getInt("colNumber"));
                purchaseInvoiceDetails.setId(rs.getInt("id"));
                purchaseInvoiceDetails.setProductCode(rs.getString("productCode"));
                purchaseInvoiceDetails.setProductCost(rs.getDouble("productCost"));
                purchaseInvoiceDetails.setProductName(rs.getString("productName"));
                purchaseInvoiceDetails.setProductQTY(rs.getDouble("productQuantity"));
                purchaseInvoiceDetails.setProductTotalCost(rs.getDouble("productTotal"));
                invoicesDetailsList.add(purchaseInvoiceDetails);
                
            }
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        return invoicesDetailsList;
    }

    public static void deletePurchaseInvoice(int id) {
        String headerSQL = "DELETE FROM tbPurchaseInvoiceHeader WHERE id = " + id + " ;";
        String detailsSQL = "DELETE FROM tbPurchaseInvoiceDetails WHERE id = " + id + " ;";
        try
        {
         con = getConnection();
         if(execUpdate(detailsSQL) && execUpdate(headerSQL)){
             AlertMaker.showInformationAlert("تم حذف الفاتورة بنجاح");
         }
         else{
             AlertMaker.showErrorAlert("خطأ في حذف الفاتورة");
         }
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static List<InvoiceHeader> getAllSalesInvoicesHeaders(){
        List<InvoiceHeader> list = new ArrayList();
        String sql = "SELECT * from tbInvoiceHeader;";
        
        con = getConnection();
        try{
            ResultSet rs = execQuery(sql);
            while(rs.next())
            {
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
                
                list.add(invoiceHeader);
            }
        } catch(Exception ex){
            ex.printStackTrace();
            AlertMaker.showErrorAlert(ex.getMessage());
        }
        
        
        return list;
    }
    
    
    public static List<InvoiceDetails> getAllSalesInvoicesDetails(){
        List<InvoiceDetails> list = new ArrayList<>();
        String sql = "SELECT * FROM tbInvoiceDetails";
        
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
            {
                InvoiceDetails invoiceDetails = new InvoiceDetails();
                invoiceDetails.setColNumber(rs.getInt("colNumber"));
                invoiceDetails.setInvoiceNumber(rs.getInt("invoiceNumber"));
                invoiceDetails.setProductName(rs.getString("productName"));
                invoiceDetails.setProductPrice(rs.getDouble("price"));
                invoiceDetails.setProductQuantity(rs.getDouble("quantity"));
                invoiceDetails.setProductTotalSale(rs.getDouble("productTotalSale"));
                
                list.add(invoiceDetails);
            }
        } 
        catch(Exception ex)
        {
            
        }
        return list;
    }
    
    
    public static List<InvoiceHeader> getAllSalesInvoicesHeadersBySQL(String sql){
        List<InvoiceHeader> list = new ArrayList();
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
            {
                InvoiceHeader invoiceHeader = new InvoiceHeader();
                invoiceHeader.setCustomerName(rs.getString("customerName"));
                invoiceHeader.setDate(rs.getString("date"));
                invoiceHeader.setDiscount(rs.getDouble("discount"));
                invoiceHeader.setNumber(rs.getInt("invoiceNumber"));
                invoiceHeader.setPayType(rs.getString("payType"));
                invoiceHeader.setTax(rs.getDouble("tax"));
                invoiceHeader.setTotal(rs.getDouble("invoiceTotal"));
                invoiceHeader.setTotalCost(rs.getDouble("invoiceTotalCost"));
                invoiceHeader.setCumstomerID(rs.getInt("customerID"));
                list.add(invoiceHeader);
            }
        }
        catch (Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public static List<PurchaseInvoiceHeader> getAllPurchaseInvoicesHeadersBySQL(String sql){
        List<PurchaseInvoiceHeader> list = new ArrayList();
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(sql);
            while(rs.next())
            {
                PurchaseInvoiceHeader invoiceHeader = new PurchaseInvoiceHeader();
                invoiceHeader.setSupplierName(rs.getString("SupplierName"));
                invoiceHeader.setDate(rs.getString("date"));
                invoiceHeader.setId(rs.getInt("id"));
                invoiceHeader.setPurchaeInvoiceTotalCost(rs.getDouble("Total"));
                invoiceHeader.setSupplierID(rs.getInt("SupplierID"));
                list.add(invoiceHeader);
            }
        }
        catch (Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public static void updateCustomerName(Customer customer)
    {
        String sql = "UPDATE tbCustomer SET name = ?,phone = ? WHERE id = ?;";
        try
        {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getId());
            
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            AlertMaker.showErrorAlert(ex.getMessage());
        }
    }
    
     public static void updateSupplierName(Supplier supplier)
    {
        String sql = "UPDATE tbSupplier SET supplierName = ?,phone = ? WHERE id = ? ;";
        try
        {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getPhone());
            ps.setInt(3, supplier.getId());
            
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            AlertMaker.showErrorAlert(ex.getMessage());
        }
    }
     
     public static boolean isProductQtyZero(Product product)
     {
         boolean check = false;
         
         String sql = "SELECT productQuantity FROM tbProduct Where productName = '" + product.getProductName() + "';";
         
         try
         {
            con = getConnection();
             System.out.println(sql);
            ResultSet rs = execQuery(sql);
            
            while(rs.next())
            {
                double qty = rs.getDouble("productQuantity");
                if(qty == 0)
                {
                    check = true;
                }
            }
         }
         
         catch(Exception ex)
         {
             ex.printStackTrace();
             AlertMaker.showErrorAlert(ex.getMessage());
         }
         
         
         return check;
     }
     
     public static void editSalesInvoice(InvoiceHeader header, List<InvoiceDetails> newProductList)
     {
         List<InvoiceDetails> oldProductList = new ArrayList();
         
         
         String getOldInvoiceProductsSQL = "SELECT * FROM tbInvoiceDetails WHERE invoiceNumber = "+ header.getNumber();
         con = getConnection();
         ResultSet resultSet = execQuery(getOldInvoiceProductsSQL);
         
         try
         {
             while(resultSet.next())
                {
                    InvoiceDetails invoiceDetails = new InvoiceDetails();
                    invoiceDetails.setColNumber(resultSet.getInt("colNumber"));
                    invoiceDetails.setInvoiceNumber(resultSet.getInt("invoiceNumber"));
                    invoiceDetails.setProductName(resultSet.getString("productName"));
                    invoiceDetails.setProductPrice(resultSet.getDouble("price"));
                    invoiceDetails.setProductQuantity(resultSet.getDouble("quantity"));
                    invoiceDetails.setProductTotalSale(resultSet.getDouble("productTotalSale"));
                    
                    oldProductList.add(invoiceDetails);
                }
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
         
         for(int i = 0; i<oldProductList.size(); i++)
         {
             // adding adjustment after invoice edited
             InvoiceDetails invoiceDetails = oldProductList.get(i);
             
             InventoryAdjustment adjustment = new InventoryAdjustment();
             
             adjustment.setAdjustmentQTY(invoiceDetails.getProductQuantity());
             adjustment.setDate(header.getDate());
             adjustment.setDetails("إرجاع قيم الفاتورة رقم  "+ header.getNumber() + " للمخزن");
             adjustment.setId(header.getNumber());
             adjustment.setProductCode(getProductCode(invoiceDetails.getProductName()));
             adjustment.setProductName(invoiceDetails.getProductName());
             adjustment.setProductQtyAfterAdjustment(getProductQTY(invoiceDetails.getProductName()) + invoiceDetails.getProductQuantity());
             
             addInventoryAdjustment(adjustment);
         }
         


         for (int i = 0; i< newProductList.size() ; i++)
         {
             InvoiceDetails invoiceDetails = newProductList.get(i);
             String invoiceDetailsSQL = "INSERT INTO tbInvoiceDetails VALUES ("
                                                                + header.getNumber() + ",'"
                                                                + header.getDate() + "',"
                                                                + invoiceDetails.getColNumber() +","
                                                                + header.getCumstomerID() + ",'"
                                                                + invoiceDetails.getProductName() + "',"
                                                                + invoiceDetails.getProductPrice() + ","
                                                                + invoiceDetails.getProductQuantity() +","
                                                                + invoiceDetails.getProductTotalSale()+ ");";
                System.out.println(invoiceDetailsSQL);
                execUpdate(invoiceDetailsSQL);         
                
//                Product product = new Product();
//                product.setProductCode(getProductCode(invoiceDetails.getProductName()));
//                product.setProductCost(invoiceDetails.get);
                
                ProductMovement productMovement = new ProductMovement();
                productMovement.setCurrentQuantity(getProductQTY(invoiceDetails.getProductName()));
                productMovement.setDate(header.getDate());
                productMovement.setDetails("تعديل الفاتورة رقم "+ header.getNumber());
                productMovement.setInQuantity(0);
                productMovement.setOutQuantity(invoiceDetails.getProductQuantity());
                productMovement.setProdcutName(invoiceDetails.getProductName());
                productMovement.setProductCode(getProductCode(invoiceDetails.getProductName()));
                productMovement.setPurchaseInvoiceID(0);
                productMovement.setSalesInvoiceID(header.getNumber());
                
                addProductMovement(productMovement);
                
                double productOldQTY = getProductQTY(invoiceDetails.getProductName());
                double productNewQTY = productOldQTY - invoiceDetails.getProductQuantity();
                
                String updateProductTableQty = "UPDATE tbProduct SET productQuantity = "
                        +  productNewQTY + " WHERE productName = '" +invoiceDetails.getProductName()+"' ;";
                
                execUpdate(updateProductTableQty);
                
                
                
         }
         
         
         
         String deleteItemSql = "DELETE FROM tbInvoiceDetails WHERE invoiceNumber = "+ header.getNumber();
         execUpdate(deleteItemSql);
         
         String headerSQL = "UPDATE tbInvoiceHeader SET date = ?, "
                                                    + "customerID = ?,"
                                                    + "customerName = ?, "
                                                    + "payType = ?,"
                                                    + "tax = ?,"
                                                    + "discount = ?,"
                                                    + "invoiceTotal = ?,"
                                                    + "invoiceTotalCost = ?   WHERE invoiceNumber = ? ;";
         try
         {
             con = getConnection();
             PreparedStatement ps = con.prepareStatement(headerSQL);
             ps.setString(1, header.getDate());
             ps.setInt(2, header.getCumstomerID());
             ps.setString(3, header.getCustomerName());
             ps.setString(4, header.getPayType());
             ps.setDouble(5, header.getTax());
             ps.setDouble(6, header.getDiscount());
             ps.setDouble(7, header.getTotal());
             ps.setInt(8, header.getNumber());
             ps.execute();
             System.out.println(ps.toString());
             System.out.println(header.getNumber());
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
         System.out.println(headerSQL);
         
         execUpdate(headerSQL);
     }
     
     public static int getCustomersCount()
     {
         int count = 0;
         
         String sql = "SELECT count(id) from tbCustomer";
         con = getConnection();
         ResultSet rs = execQuery(sql);
         
        try {
            while(rs.next())
            {
                count = rs.getInt("count(id)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
         
         return count;
     }
     
     public static int getSalesInvoicesCount()
     {
         int count = 0;
         
         String sql = "SELECT count(invoiceNumber) from tbInvoiceHeader";
         con = getConnection();
         ResultSet rs = execQuery(sql);
         
        try {
            while(rs.next())
            {
                count = rs.getInt("count(invoiceNumber)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
         
         return count;
     }
     
     public static int getProductsCount()
     {
         int count = 0;
         
         String sql = "SELECT count(productCode) from tbProduct";
         con = getConnection();
         ResultSet rs = execQuery(sql);
         
        try {
            while(rs.next())
            {
                count = rs.getInt("count(productCode)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
         
         return count;
     }
     
     public static int getPurchaseInvoicesCount()
     {
         int count = 0;
         
         String sql = "SELECT count(id) from tbPurchaseInvoiceHeader";
         con = getConnection();
         ResultSet rs = execQuery(sql);
         
        try {
            while(rs.next())
            {
                count = rs.getInt("count(id)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
         
         return count;
     }
     
     public static int getSuppliersCount()
     {
         int count = 0;
         
         String sql = "SELECT count(id) from tbSupplier";
         con = getConnection();
         ResultSet rs = execQuery(sql);
         
        try {
            while(rs.next())
            {
                count = rs.getInt("count(id)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            ex.getMessage();
        }
         
         return count;
     }    
     
     public static int getPreviousPurchaseInvoiceId(String ProductName)
     {
      int id = 0;
      
      String sql = "SELECT max(id) from tbPurchaseInvoiceDetails where productName = '"
                                                                +ProductName +"';";
      
      try
      {   
          con = getConnection();
          ResultSet rs = execQuery(sql);
          while(rs.next())
          {
              id = rs.getInt("max(id)");
          }
      }
      catch(SQLException ex)
      {
          ex.printStackTrace();
      }
      return id;
     }
     
     public static void updateProductCost(Product product)
     {
         String sql = "UPDATE tbProduct SET productCost = ? WHERE productName = ?";
         con = getConnection();
         try
         {
             PreparedStatement ps = con.prepareStatement(sql);
             ps.setDouble(1, product.getProductCost());
             ps.setString(2, product.getProductName());
             ps.executeUpdate();
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
     }
     
     public static double getPreviousProductCost(int purchaseInvoiceID, String productName)
     {
         double cost = 0;
         String sql = "SELECT * FROM tbPurchaseInvoiceDetails WHERE id = ? AND productName = ?";
         con = getConnection();
         try
         {
             PreparedStatement ps = con.prepareStatement(sql);
             ps.setDouble(1, purchaseInvoiceID);
             ps.setString(2, productName);
             ResultSet rs = ps.executeQuery();
             while(rs.next())
             {
                 cost = rs.getDouble("productCost");
             }
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
         return cost;
     }
     
     public static List<InventoryAdjustment> getInventoryAdjustmentsBySQL(String sql)
     {
         List<InventoryAdjustment> list = new ArrayList(); ;
         try
         {
             con = getConnection();
             ResultSet rs = execQuery(sql);
             while(rs.next())
             {
                 InventoryAdjustment adjustment = new InventoryAdjustment();
                 adjustment.setAdjustmentQTY(rs.getDouble("adjustmentQTY"));
                 adjustment.setDate(rs.getString("date"));
                 adjustment.setDetails(rs.getString("details"));
                 adjustment.setId(rs.getInt("id"));
                 adjustment.setProductCode(rs.getString("productCode"));
                 adjustment.setProductName(rs.getString("productName"));
                 adjustment.setProductQtyAfterAdjustment(rs.getDouble("productQtyAfterAdjustment"));
                 list.add(adjustment);
             }
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
         return list;
     }
     
     public static boolean isProductCodeExisits(String productCode)
     {
         boolean exisits = false;
         
         String sql = "SELECT * FROM tbProduct WHERE productCode = '"+ productCode + "';";
         
         try
         {
            con = getConnection();
            ResultSet rs = execQuery(sql);
             while (rs.next()) 
             {                 
                 exisits = true;
             }
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
         
         return exisits;
     }
     
     public static List<ProductMovement> getProductMovementListBySQL(String sql)
     {
      List<ProductMovement> list = new ArrayList<>();
      
      try
      {
          con = getConnection();
          ResultSet rs = execQuery(sql);
          while(rs.next())
          {
              ProductMovement movement = new ProductMovement();
              movement.setCurrentQuantity(rs.getDouble("currentQuantity"));
              movement.setDate(rs.getString("date"));
              movement.setDetails(rs.getString("Details"));
              movement.setId(rs.getInt("id"));
              movement.setInQuantity(rs.getDouble("inQuantity"));
              movement.setOutQuantity(rs.getDouble("outQuantity"));
              movement.setProductCode(rs.getString("productCode"));
              movement.setProdcutName(rs.getString("productName"));
              movement.setPurchaseInvoiceID(rs.getInt("purchaseInvoiceID"));
              movement.setSalesInvoiceID(rs.getInt("salesInvoiceID"));
              list.add(movement);
          }
      }
      catch(SQLException ex)
      {
          ex.printStackTrace();
      }
      
      return list;
     }

    private static void createUsersDetailsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS'tbUserDetails' (" +
                                            "	'id'	INTEGER NOT NULL," +
                                            "	'username'	VARCHAR(100)," +
                                            "	'password'	VARCHAR(100)," +
                                            "	'salesPermissions'	INTEGER," +
                                            "	'purchasePermissions'	INTEGER," +
                                            "	'inventoryPermissions'	INTEGER," +
                                            "	'customersAndSuppliersPermissions'	INTEGER," +
                                            "	PRIMARY KEY('id' AUTOINCREMENT)" +
                                            ");";
        
        con = getConnection();
        execUpdate(sql);
    }
    
    private static void insertDefaultUser()
    {
        String sql = "INSERT INTO " +
                                "tbUserDetails (username,password,salesPermissions,purchasePermissions,inventoryPermissions," +
                                "customersAndSuppliersPermissions)" +
                                "VALUES " +
                                "('admin','admin2022',1,1,1,1);";
        
        String getUserSql = "SELECT * FROM tbUserDetails WHERE id = 1";
        con = getConnection();
        try
        {
            ResultSet rs = execQuery(getUserSql);
            if (rs.next()) {
                System.out.println("user already added");
            }
            else {
                if (execUpdate(sql)) {
                        System.out.println("inserted the default user");
               }
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static ObservableList<UserDetails> getAllUsers(){
        ObservableList<UserDetails> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tbUserDetails";
        
        con = getConnection();
        ResultSet rs = execQuery(sql);
        try{
            while (rs.next()) {                
                UserDetails user = new UserDetails();
                user.setCustomersAndSuppliersPermissions(rs.getInt("customersAndSuppliersPermissions"));
                user.setId(rs.getInt("id"));
                user.setInventoryPermissions(rs.getInt("inventoryPermissions"));
                user.setPassWord(rs.getString("password"));
                user.setPurchasePermissions(rs.getInt("purchasePermissions"));
                user.setSalesPermissions(rs.getInt("salesPermissions"));
                user.setUserName(rs.getString("username"));
                list.add(user);
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public static void addUser(UserDetails user)
    {
        String sql = "INSERT INTO " +
                                "tbUserDetails (username,password,salesPermissions,purchasePermissions,inventoryPermissions," +
                                "customersAndSuppliersPermissions)" +
                                "VALUES " +
                                "(?,?,?,?,?,?);";
        
        con = getConnection();
        
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassWord());
            ps.setInt(3, user.getSalesPermissions());
            ps.setInt(4, user.getPurchasePermissions());
            ps.setInt(5, user.getInventoryPermissions());
            ps.setInt(6, user.getCustomersAndSuppliersPermissions());
            if (ps.execute()) {
                System.out.println("user added successfully");
                AlertMaker.showInformationAlert("تم إضافة المستخدم بنجاح");
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void updateUser(UserDetails user)
    {
        String sql = "UPDATE "
                + "tbUserDetails "
                + "SET "
                + "username=?,password=?,salesPermissions=?,purchasePermissions=?"
                + ",inventoryPermissions=?,customersAndSuppliersPermissions=? "
                + "WHERE "
                + "id = ?;";
        
        con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassWord());
            ps.setInt(3, user.getSalesPermissions());
            ps.setInt(4, user.getPurchasePermissions());
            ps.setInt(5, user.getInventoryPermissions());
            ps.setInt(6, user.getCustomersAndSuppliersPermissions());
            ps.setInt(7, user.getId());
            if(ps.execute())
            {
                AlertMaker.showInformationAlert("تم تحديث المستخدم بنجاح");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
