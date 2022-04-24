/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;
import model.ProductMovement;
import model.PurchaseInvoiceDetails;
import model.PurchaseInvoiceHeader;
import model.Supplier;
import resources.AlertMaker;
import resources.DatabaseHandler;
import resources.StageShower;
import resources.ReportViewer;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class PurchaseInvoiceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setInvoiceNumber();
        setInvoiceDate();
        setSuppliersList();
        setSearchTableItems();
        filterSearchTable();
        disableControls();
        addSupplierChoiceBoxListener();
        txtPurchaseInvoiceTotal.setText("0.0");
    }    
    
     @FXML
    private TableView<Product> tbSearchProduct;

    @FXML
    private TableColumn<PurchaseInvoiceDetails, Double> colInvoiceProductCost;
    
    @FXML
    private TableColumn<Product, Double> colSearchProductCost;

    @FXML
    private ChoiceBox<String> choiceBoxSupplierName;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtPurchaseInvoiceTotal;

    @FXML
    private TextField txtProductQuantity;

    @FXML
    private JFXButton btnGetProductData;

    @FXML
    private TextField txtProductName;

    @FXML
    private JFXButton btnNewInvoice;

    @FXML
    private TableColumn<Product, String> colSearchProductName;

    @FXML
    private TableColumn<PurchaseInvoiceDetails, String> colInvoiceProductName;

    @FXML
    private JFXButton btnSaveInvoice;

    @FXML
    private TableColumn<PurchaseInvoiceDetails, Double> colInvoiceProductQTY;

    @FXML
    private TableView<PurchaseInvoiceDetails> tbPurchaseInvoice;

    @FXML
    private JFXButton btnPrintInvoice;

    @FXML
    private TextField txtInvoiceNumber;

    @FXML
    private TableColumn<Product, Double> colSearchProductQTY;

    @FXML
    private TextField txtProductPrice;

    @FXML
    private JFXButton btnAddProductToInvoice;

    @FXML
    private TableColumn<PurchaseInvoiceDetails, Double> colInvoiceProductTotal;
    
    @FXML
    private TableColumn<PurchaseInvoiceDetails, Integer> colPurchaseInvoiceDetailsColNumber;
    
    private Product product;
    ObservableList<PurchaseInvoiceDetails> productsList = FXCollections.observableArrayList();

    @FXML
    void addProductToTableView(ActionEvent event) 
    {
        int colNumber = productsList.size();
        
        colInvoiceProductCost.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceDetails, Double>("productCost"));
        colInvoiceProductName.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceDetails, String>("productName"));
        colInvoiceProductQTY.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceDetails, Double>("productQTY"));
        colInvoiceProductTotal.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceDetails, Double>("productTotalCost"));
        colPurchaseInvoiceDetailsColNumber.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceDetails, Integer>("colNumber"));
        
        PurchaseInvoiceDetails purchaseInvoiceDetails = new PurchaseInvoiceDetails();
        purchaseInvoiceDetails.setColNumber(colNumber+1);
        purchaseInvoiceDetails.setProductCost(Double.valueOf(txtProductPrice.getText()));
        purchaseInvoiceDetails.setProductName(product.getProductName());
        purchaseInvoiceDetails.setProductQTY(Double.valueOf((txtProductQuantity.getText())));
        purchaseInvoiceDetails.setProductTotalCost(purchaseInvoiceDetails.getProductQTY() * purchaseInvoiceDetails.getProductCost());
        
        productsList.add(purchaseInvoiceDetails);
        tbPurchaseInvoice.setItems(productsList);
        
        Double total = purchaseInvoiceDetails.getProductTotalCost() + Double.valueOf(txtPurchaseInvoiceTotal.getText());
        txtPurchaseInvoiceTotal.setText(total.toString());
        
        txtProductName.setDisable(false);
        txtProductName.clear();
        txtProductPrice.clear();
        txtProductQuantity.clear();
        txtProductName.requestFocus();
        product = null;
        //setSearchTableItems();

    }


    @FXML
    void getProductDetails(ActionEvent event) 
    {
        try
        {
           product = tbSearchProduct.getSelectionModel().getSelectedItem();
           txtProductName.setText(product.getProductName());
           txtProductName.setDisable(true);
           txtProductPrice.requestFocus();
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
        
        

    }

    @FXML
    void deleteProductFromInvoiceTable(ActionEvent event) {
        PurchaseInvoiceDetails purchaseInvoiceDetails= tbPurchaseInvoice.getSelectionModel().getSelectedItem();
        productsList.remove(purchaseInvoiceDetails);
        double newTotalValue = Double.valueOf(txtPurchaseInvoiceTotal.getText()) - purchaseInvoiceDetails.getProductTotalCost();
        txtPurchaseInvoiceTotal.setText(String.valueOf(newTotalValue));

    }

    @FXML
    void savePurchaseInvoice(ActionEvent event) {
        int supplierID = DatabaseHandler.getSupplierID(choiceBoxSupplierName.getValue());
        
        PurchaseInvoiceHeader purchaseInvoiceHeader = new PurchaseInvoiceHeader();
        purchaseInvoiceHeader.setId(Integer.valueOf(txtInvoiceNumber.getText()));
        purchaseInvoiceHeader.setDate(txtDate.getValue().toString());
        purchaseInvoiceHeader.setPurchaeInvoiceTotalCost(Double.valueOf(txtPurchaseInvoiceTotal.getText()));
        purchaseInvoiceHeader.setSupplierID(supplierID);
        purchaseInvoiceHeader.setSupplierName(choiceBoxSupplierName.getValue());
        
        DatabaseHandler.savePurchaseInvoice(purchaseInvoiceHeader.getId(),
                purchaseInvoiceHeader.getDate(),
                supplierID,
                purchaseInvoiceHeader.getSupplierName(),
                purchaseInvoiceHeader.getPurchaeInvoiceTotalCost(), productsList);
        
        addProductMovement();
        productsList.clear();
        disableControls();
        
        

    }

    @FXML
    void printPurchaseInvoice(ActionEvent event) {
        Map parameters = new HashMap();
        List<PurchaseInvoiceDetails> purchaseInvoiceDetailsList = new ArrayList<PurchaseInvoiceDetails>();
        
        int invoiceNumber = Integer.valueOf(txtInvoiceNumber.getText());
        String supplierName = choiceBoxSupplierName.getValue();
        String date = txtDate.getValue().toString();
        double total = Double.valueOf(txtPurchaseInvoiceTotal.getText());
        
        parameters.put("invoiceNumber", invoiceNumber);
        parameters.put("supplierName", supplierName);
        parameters.put("date", date);
        parameters.put("total", total);
        
        for(int i =0; i<productsList.size(); i++){
            PurchaseInvoiceDetails purchaseInvoiceDetails = productsList.get(i);
            purchaseInvoiceDetailsList.add(purchaseInvoiceDetails);
        }
        
        
        ReportViewer reportViewer = new ReportViewer();
        reportViewer.showReport("puchaseInvoice.jrxml", parameters, purchaseInvoiceDetailsList);
        

    }

    @FXML
    void newPurchaseInvoice(ActionEvent event) {
        Stage oldStage = (Stage) txtInvoiceNumber.getParent().getScene().getWindow();
        oldStage.close();
        
        StageShower stageShower = new StageShower();
        stageShower.show("/view/purchaseInvoice.fxml", "فاتورة مشتروات", true);

    }

   

    @FXML
    void cancelGetProductData(ActionEvent event) {
        txtProductName.clear();
        txtProductPrice.clear();
        txtProductQuantity.clear();
        txtProductName.requestFocus();
        txtProductName.setDisable(false);

    }

    private void setInvoiceNumber() {
        Integer lastInvoiceNumber = 0;
        lastInvoiceNumber = DatabaseHandler.getLastPurchaseInvoiceNumber();
        Integer newInvoiceNumber = lastInvoiceNumber + 1 ;
        txtInvoiceNumber.setText(newInvoiceNumber.toString());
    }

    private void setInvoiceDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println( simpleDateFormat.format(date) );
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(simpleDateFormat.format(date), formatter);
        txtDate.setValue(localDate);
    }

    private void setSuppliersList() {
        ObservableList suppliersObjects = DatabaseHandler.getSuppliersList();
        ObservableList suppliersNames = FXCollections.observableArrayList();
        
        for(int i = 0; i<suppliersObjects.size();i++){
            Supplier supplier = (Supplier) suppliersObjects.get(i);
            suppliersNames.add(supplier.getName());
            System.out.println(supplier.getName());
        }
        
        choiceBoxSupplierName.setItems(null);
        
        choiceBoxSupplierName.setItems(suppliersNames);
    }

    private void setSearchTableItems() {
        colSearchProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        colSearchProductQTY.setCellValueFactory(new PropertyValueFactory<Product, Double>("productQuantity"));
        colSearchProductCost.setCellValueFactory(new PropertyValueFactory<Product, Double>("productCost"));
        ObservableList productsList = DatabaseHandler.getProductsList();
        tbSearchProduct.setItems(productsList);
    }

    private void filterSearchTable() {
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

    private void disableControls() {
        txtDate.setDisable(true);
        txtInvoiceNumber.setDisable(true);
        //choiceBoxCustomerName.setDisable(true);
        txtProductName.setDisable(true);
        txtProductPrice.setDisable(true);
        txtProductQuantity.setDisable(true);
        tbSearchProduct.setDisable(true);
        tbPurchaseInvoice.setDisable(true);
        txtDate.setDisable(true);
        txtPurchaseInvoiceTotal.setDisable(true);
        //choiceBoxSupplierName.setDisable(false);
    }

    private void addSupplierChoiceBoxListener() {
        choiceBoxSupplierName.getSelectionModel().selectedIndexProperty().addListener(
              (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                            enableConrols();
                                
                            });
    }

    private void enableConrols() {
        txtDate.setDisable(false);
        txtInvoiceNumber.setDisable(false);
        //choiceBoxCustomerName.setDisable(true);
        txtProductName.setDisable(false);
        txtProductPrice.setDisable(false);
        txtProductQuantity.setDisable(false);
        tbSearchProduct.setDisable(false);
        tbPurchaseInvoice.setDisable(false);
        txtDate.setDisable(false);
        txtPurchaseInvoiceTotal.setDisable(false);
        choiceBoxSupplierName.setDisable(false);
    }
    
    @FXML
    private void setFocusOnQtyTextField()
    {
        txtProductQuantity.requestFocus();
    }
    
    private void addProductMovement()
    {
                try
        {
            ProductMovement productMovement = new ProductMovement();
            PurchaseInvoiceDetails invoiceDetails ;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            Date date = new Date();
            String formatedDate = simpleDateFormat.format(date);

            for(int i = 0; i< productsList.size(); i++)
            {
                invoiceDetails = (PurchaseInvoiceDetails) productsList.get(i);


                productMovement.setDate(formatedDate);
                productMovement.setProductCode(DatabaseHandler.getProductCode(invoiceDetails.getProductName()));
                productMovement.setProdcutName(invoiceDetails.getProductName());
                productMovement.setSalesInvoiceID(0);
                productMovement.setPurchaseInvoiceID(Integer.valueOf(txtInvoiceNumber.getText()));
                productMovement.setInQuantity(invoiceDetails.getProductQTY());
                productMovement.setOutQuantity(0);
                productMovement.setCurrentQuantity(DatabaseHandler.getProductQTY(invoiceDetails.getProductName()));
                productMovement.setDetails("فاتورة مشتروات رقم : " + txtInvoiceNumber.getText());
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

    
    
}
