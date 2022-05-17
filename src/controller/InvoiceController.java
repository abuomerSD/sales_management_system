/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import resources.AlertMaker;
import resources.DatabaseHandler;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.InvoiceDetails;
import model.Product;
import model.ProductMovement;
import resources.ReportViewer;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class InvoiceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInvoiceNumber();
        setInvoiceDate();
        setCustomersList();
        setSearchTableItems();
        filterSearchTable();
        resetAllTotalTextFields();
        addSalesInvoiceTypes();
        disableControls();
        addCustomerChoiceBoxListener();
        addPayTypeChoiceBoxListener();
        setDiscountAndTaxLabels();
   }    
    
    @FXML
    public JFXButton btnGetProductData;
    
    @FXML
    public JFXButton btnAddProductToInvoice;
    
    @FXML
    public JFXButton btnSaveInvoice;
    
    @FXML
    public JFXButton btnPrintInvoice;
    
    @FXML
    public JFXButton btnNewInvoice;
    
    
     @FXML
    public ChoiceBox<String> choiceBoxCustomerName;
     
    @FXML
    public TableView<InvoiceDetails> tbInvoice;

    @FXML
    public TableColumn<InvoiceDetails, Double> colInvoiceProductPrice;

    @FXML
    public TextField txtDiscount;

    @FXML
    public DatePicker txtDate;

    @FXML
    public TextField txtProductQuantity;

    @FXML
    public TextField txtInvoiceTotal;

    @FXML
    public TextField txtProductName;
    
    @FXML
    public TableView<Product> tbSearchProduct;

    @FXML
    public TableColumn<Product, String> colSearchProductName;
    
    @FXML
    public TableColumn<Product, Double> colSearchProductPrice;

    @FXML
    public TableColumn<InvoiceDetails, String> colInvoiceProductName;

    @FXML
    public TableColumn<InvoiceDetails, Double> colInvoiceProductQTY;

    @FXML
    public TextField txtTax;

    @FXML
    public TextField txtInvoiceNumber;

    @FXML
    public TableColumn<Product, Double> colSearchProductQTY;

    @FXML
    public TextField txtProductPrice;

    @FXML
    public TableColumn<InvoiceDetails, Double> colInvoiceProductTotal;
    
    @FXML
    public Label lbdiscount;
    
    @FXML
    public Label lbtax;
    
    @FXML
    public TableColumn<InvoiceDetails, Integer> colInvoiceColumnNumber;
    
    @FXML
    ChoiceBox<String> choiceBoxSalesInvoiceType;
    
    ObservableList<InvoiceDetails> invoiceDetailsList = FXCollections.observableArrayList();
    
    // creating a Product Object for the  search feature
    public Product product;

    @FXML
    public void addProductToTableView(ActionEvent event) {
        
        try{
            
            DecimalFormat decimalFormat = new DecimalFormat("#.0000");
         
        //Product product = (Product) tbSearchProduct.getSelectionModel().getSelectedItem();
            System.out.println(product.getProductQuantity().toString());
        if(product.getProductQuantity() < Double.valueOf(txtProductQuantity.getText())){
                AlertMaker.showErrorAlert("العدد اكبر من المخزون");
                txtProductQuantity.requestFocus();
                
            }
        
        else{
            
            
            int colNumber = invoiceDetailsList.size() + 1;
            Double productPrice = Double.valueOf(decimalFormat.format(Double.valueOf(txtProductPrice.getText())));
            Double productQty = Double.valueOf(decimalFormat.format(Double.valueOf(txtProductQuantity.getText())));
            Double tax = Double.valueOf(decimalFormat.format(Double.valueOf(txtTax.getText())));
            Double discount = Double.valueOf(decimalFormat.format(Double.valueOf(txtDiscount.getText())));
            Double total = Double.valueOf(decimalFormat.format(productPrice*productQty));
            
            //DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            

            InvoiceDetails invoiceDetails 
                    = new InvoiceDetails(Integer.valueOf(txtInvoiceNumber.getText()),
                                                                               colNumber,
                                                                               txtProductName.getText(),
                                                                               productPrice,
                                                                               productQty ,
                                                                               total );

            colInvoiceProductName.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, String>("productName"));
            colInvoiceProductQTY.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Double>("productQuantity"));
            colInvoiceProductPrice.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Double>("productPrice"));
            colInvoiceProductTotal.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Double>("productTotalSale"));
            colInvoiceColumnNumber.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Integer>("colNumber"));

            invoiceDetailsList.add(invoiceDetails);
            tbInvoice.setItems(invoiceDetailsList);

            txtProductName.setDisable(false);
            txtProductName.clear();

            double oldInvoiceTotalValue = Double.valueOf(txtInvoiceTotal.getText());
            Double newInvoiceTotalValue = Double.valueOf(decimalFormat.format(oldInvoiceTotalValue + total));
            txtInvoiceTotal.setText(newInvoiceTotalValue.toString());

            txtProductPrice.clear();
            txtProductQuantity.clear();
            txtProductName.requestFocus(); 
            
            // clearing main product values
            product = null;
        }
        
        
        
            
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        

    }

    @FXML
    public void saveInvoice(ActionEvent event) throws SQLException {
        
        if(invoiceDetailsList.isEmpty()){
            AlertMaker.showErrorAlert("اختر بعض الاصناف");
        }
        
        else{
            disableControls();
        int invoiceNumber  = Integer.valueOf(txtInvoiceNumber.getText());
        String date = txtDate.getValue().toString();
        String customername = (String) choiceBoxCustomerName.getValue(); ;
        int customerID = DatabaseHandler.getCustomerID(customername);
        double tax = Double.valueOf(lbtax.getText());
        double discount = Double.valueOf(lbdiscount.getText());
        double invoiceTotal = Double.valueOf(txtInvoiceTotal.getText());
        String payType = choiceBoxSalesInvoiceType.getValue().toString();
        
        double totalCost = 0.0;
        double costForEveryProduct;
        for(int i = 0; i<invoiceDetailsList.size(); i++)
        {
            InvoiceDetails invoiceDetails = (InvoiceDetails) invoiceDetailsList.get(i);
            costForEveryProduct = DatabaseHandler.getProductCost(invoiceDetails.getProductName()) * invoiceDetails.getProductQuantity();
            totalCost = totalCost + costForEveryProduct;
            
        }
        
        
        DatabaseHandler.saveInvoice(invoiceNumber, date, customerID, customername, payType, tax, discount, invoiceTotal, totalCost,invoiceDetailsList);
        dicreaseProductsQtyFromDataBase();
        addProductMovement();
        setSearchTableItems();
        
        
        }
        

    }

    @FXML
    public void printInvoice(ActionEvent event) {
        
        String reportURL = "D:\\Step Sales\\Reports\\sales_invoice.jrxml";
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("invoiceNumber", Integer.valueOf(txtInvoiceNumber.getText()));
        parameters.put("customerName", (String) choiceBoxCustomerName.getValue());
        parameters.put("tax", Double.valueOf(txtTax.getText()));
        parameters.put("discount", Double.valueOf(txtDiscount.getText()));
        parameters.put("total", Double.valueOf(txtInvoiceTotal.getText()));
        
        InvoiceDetails invoiceDetails;
        
        List<InvoiceDetails> list = new ArrayList<InvoiceDetails>();
        
        for(int i = 0 ; i < invoiceDetailsList.size() ; i++){
            invoiceDetails = (InvoiceDetails) invoiceDetailsList.get(i);
            list.add(invoiceDetails);
        }
        
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
//        
//        
//        
//        try
//        {
//          JasperReport report = JasperCompileManager.compileReport(reportURL);
//          JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
//          JasperViewer.viewReport(print, false);
//        } 
//        catch(Exception ex)
//        {
//            AlertMaker.showErrorAlert(ex.getMessage());
//            ex.printStackTrace();
//        }
        
        ReportViewer reportViewer = new ReportViewer();
        reportViewer.showReport("sales_invoice.jrxml", parameters, list);
        

    }

    @FXML
    public void newInvoice(ActionEvent event) {
//        enableControls();
//        resetAllTotalTextFields();
//        setInvoiceNumber();
//        setInvoiceDate();
//        invoiceDetailsList.clear();
//        disableControls();
        //choiceBoxCustomerName.getSelectionModel().clearSelection();
        
        Stage oldStage = (Stage) txtDiscount.getParent().getScene().getWindow();
        oldStage.close();
        
        StageShower stageShower = new StageShower();
        stageShower.show("/view/invoice.fxml", "فاتورة مبيعات", true);

    }

    @FXML
    public void getProductDetails(ActionEvent event) {
        try{
            product = tbSearchProduct.getSelectionModel().getSelectedItem();
                      
            
                txtProductName.setDisable(false);
                txtProductName.clear();
                txtProductName.setText(product.getProductName());
                txtProductPrice.setText(product.getProductPrice().toString());

                txtProductName.setDisable(true);
                txtProductQuantity.requestFocus();
            
            
        } catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        

    }
    
    
    @FXML
    public void addTax(){
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        Double tax = Double.valueOf(decimalFormat.format(Double.valueOf(txtTax.getText())));
        double oldInvoiceTolal = Double.valueOf(txtInvoiceTotal.getText());
        double discount = Double.valueOf(decimalFormat.format(Double.valueOf(txtDiscount.getText())));
        Double newInvoiceTotal = (oldInvoiceTolal + tax) - discount;
        txtInvoiceTotal.setText(decimalFormat.format(newInvoiceTotal));
        txtTax.setDisable(true);
        txtTax.setText("0");
        lbtax.setText(tax.toString());
    }
    
    @FXML
    public void addDiscount(){
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        double oldInvoiceTolal = Double.valueOf(decimalFormat.format(Double.valueOf(txtInvoiceTotal.getText())));
        Double discount = Double.valueOf(decimalFormat.format(Double.valueOf(txtDiscount.getText())));
        Double newInvoiceTotal = (oldInvoiceTolal + Double.valueOf(decimalFormat.format(Double.valueOf(txtTax.getText())))) - discount;
        txtInvoiceTotal.setText(decimalFormat.format(newInvoiceTotal));
        txtDiscount.setDisable(true);
        txtDiscount.setText("0");
        //Double discount = Double.valueOf(txtDiscount.getText());
        lbdiscount.setText(discount.toString());
    }
    
    
    @FXML
    public void deleteProductFromInvoiceTable(){
        InvoiceDetails invoiceDetails = tbInvoice.getSelectionModel().getSelectedItem();
        invoiceDetailsList.remove(invoiceDetails);
        double oldInvoiceTotal = Double.valueOf(txtInvoiceTotal.getText());
        Double newInvoiceTotal = oldInvoiceTotal - invoiceDetails.getProductTotalSale();
        txtInvoiceTotal.setText(newInvoiceTotal.toString());
    }

    public void setInvoiceNumber() {
        Integer lastInvoiceNumber = 0;
        try {
            // TODO

            lastInvoiceNumber = DatabaseHandler.getLastInvoiceNumber();
        } catch (SQLException ex) {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        Integer newInvoiceNumber = lastInvoiceNumber + 1 ;
        txtInvoiceNumber.setText(newInvoiceNumber.toString());
    }
    
    
    public void setInvoiceDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println( simpleDateFormat.format(date) );
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(simpleDateFormat.format(date), formatter);
        txtDate.setValue(localDate);
    }
    
    public void setCustomersList(){
        ObservableList customersObjects = DatabaseHandler.getCusmtomerList();
        ObservableList customersNames = FXCollections.observableArrayList();
        
        for(int i = 0; i<customersObjects.size();i++){
            Customer customer = (Customer) customersObjects.get(i);
            customersNames.add(customer.getName());
            
        }
        
        choiceBoxCustomerName.setItems(null);
        choiceBoxCustomerName.setItems(customersNames);
    }

    public void setSearchTableItems() {
        colSearchProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        colSearchProductQTY.setCellValueFactory(new PropertyValueFactory<Product, Double>("productQuantity"));
        colSearchProductPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
        ObservableList productsList = DatabaseHandler.getProductsList();
        tbSearchProduct.setItems(productsList);
    }
    
    
    public void filterSearchTable(){
        ObservableList productsList = DatabaseHandler.getProductsList();
        FilteredList<Product> filteredData = new FilteredList<>(productsList, b -> true);
        txtProductName.textProperty().addListener((observable, oldValue, newValue)->{
           filteredData.setPredicate((Predicate<Product>) product ->{
               if(newValue == null ||   newValue.isEmpty()){
                   return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               if(product.getProductCode().contains(newValue)){
                   return true;
               } else if(product.getProductName().toLowerCase().contains(newValue)){
                   return true;
               }
               return false;
           });
        });
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbSearchProduct.comparatorProperty());
        tbSearchProduct.setItems(sortedData);
    }
    
    
    public void enableControls(){
        txtDate.setDisable(false);
        txtInvoiceNumber.setDisable(false);
        //choiceBoxCustomerName.setDisable(false);
        txtProductName.setDisable(false);
        txtProductPrice.setDisable(false);
        txtProductQuantity.setDisable(false);
        tbSearchProduct.setDisable(false);
        tbInvoice.setDisable(false);
        txtTax.setDisable(false);
        txtDiscount.setDisable(false);
        txtInvoiceTotal.setDisable(false);
        choiceBoxSalesInvoiceType.setDisable(false);
        
        
//        btnAddProductToInvoice.setDisable(false);
//        btnSaveInvoice.setDisable(false);
    }


    public void disableControls(){
        txtDate.setDisable(true);
        txtInvoiceNumber.setDisable(true);
        //choiceBoxCustomerName.setDisable(true);
        txtProductName.setDisable(true);
        txtProductPrice.setDisable(true);
        txtProductQuantity.setDisable(true);
        tbSearchProduct.setDisable(true);
        tbInvoice.setDisable(true);
        txtTax.setDisable(true);
        txtDiscount.setDisable(true);
        txtInvoiceTotal.setDisable(true);
        choiceBoxSalesInvoiceType.setDisable(true);
//        btnAddProductToInvoice.setDisable(true);
//        btnGetProductData.setDisable(true);
//        btnSaveInvoice.setDisable(true);
        
    }

    public void resetAllTotalTextFields() {
        txtInvoiceTotal.setText("0");
        txtTax.setText("0");
        txtDiscount.setText("0");
    }
    
    @FXML
    public void cancelGetProductData(){
        txtProductName.setDisable(false);
        txtProductName.clear();
        txtProductPrice.clear();
        txtProductQuantity.clear();
    }

    public void addSalesInvoiceTypes() {
        
        ObservableList payType = FXCollections.observableArrayList();
        payType.add("كاش");
        payType.add("بنكك");
        payType.add("شيك");
        payType.add("اجل");
        choiceBoxSalesInvoiceType.setItems(payType);
    }
    
    
    
    public void addCustomerChoiceBoxListener(){
        choiceBoxCustomerName.getSelectionModel().selectedIndexProperty().addListener(
              (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                            choiceBoxSalesInvoiceType.setDisable(false);
                                
                            });
    }
    
    
    public void addPayTypeChoiceBoxListener(){
        choiceBoxSalesInvoiceType.getSelectionModel().selectedIndexProperty().addListener(
              (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                            enableControls();
                                
                            });
    }
    
    public void dicreaseProductsQtyFromDataBase(){
        DatabaseHandler.dicreaseProductsQtyFromDataBase(invoiceDetailsList);
    }
    
    public void setDiscountAndTaxLabels(){
        lbtax.setText("0.0");
        lbdiscount.setText("0.0");
    }

    public void addProductMovement() {
        try
        {
            ProductMovement productMovement = new ProductMovement();
//            InvoiceDetails invoiceDetails ;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            Date date = new Date();
            String formatedDate = simpleDateFormat.format(date);

            for(int i = 0; i< invoiceDetailsList.size(); i++)
            {
                InvoiceDetails invoiceDetails = (InvoiceDetails) invoiceDetailsList.get(i);


                productMovement.setDate(formatedDate);
                productMovement.setProductCode(DatabaseHandler.getProductCode(invoiceDetails.getProductName()));
                productMovement.setProdcutName(invoiceDetails.getProductName());
                productMovement.setSalesInvoiceID(Integer.valueOf(txtInvoiceNumber.getText()));
                productMovement.setPurchaseInvoiceID(0);
                productMovement.setInQuantity(0);
                productMovement.setOutQuantity(invoiceDetails.getProductQuantity());
                productMovement.setCurrentQuantity(DatabaseHandler.getProductQTY(invoiceDetails.getProductName()));
                productMovement.setDetails("فاتورة مبيعات رقم : " + txtInvoiceNumber.getText());
                DatabaseHandler.addProductMovement(productMovement);
                //productMovement = null;
                //invoiceDetails = null;
                }
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void addSalesInvoiceTotalCost()
    {
        
    }
}



