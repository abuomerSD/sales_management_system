/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.Collator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import model.PurchaseInvoiceDetails;
import model.PurchaseInvoiceHeader;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class PurchaseInvoicesListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setTableData();
        filterTableData();
    }    
    
    ObservableList purchaseInvoicesList = DatabaseHandler.getPurchaseInvoicesList();
    
    @FXML
    private TableView<PurchaseInvoiceHeader> tbPurchaseInvoicesList;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, Double> colTotal;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, String> colSupplierNameName;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, String> colDate;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, Integer> colPurchaseInvoiceNumber;

    @FXML
    void deletePurchaseInvoice(ActionEvent event) {
        PurchaseInvoiceHeader purchaseInvoiceHeader = tbPurchaseInvoicesList.getSelectionModel().getSelectedItem();
        List<PurchaseInvoiceDetails> purchaseInvoiceDetailsList = DatabaseHandler.getPurchaseInvoicesDetails(purchaseInvoiceHeader.getId());
        System.out.println("selected invoice id = "+ purchaseInvoiceHeader.getId());
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("هل تريد حذف الفاتورة؟");
        alert.setHeaderText("تأكيد");
        Optional<ButtonType> response = alert.showAndWait();
        
        if(response.get() == ButtonType.OK)
        {
            System.out.println(purchaseInvoiceDetailsList.size());
            
            for (PurchaseInvoiceDetails details : purchaseInvoiceDetailsList) {
                System.out.println("Updating " + details.getProductName());
                DatabaseHandler.updateProductValues(details.getProductName(), DatabaseHandler.getProductQTY(details.getProductName()) - details.getProductQTY(), details.getProductCost());
            }
            System.out.println("Deleteing ");
            DatabaseHandler.deletePurchaseInvoice(purchaseInvoiceHeader.getId());
            //AlertMaker.showInformationAlert("تم حذف الفاتورة بنجاح");
            
            setTableData();
            filterTableData();
            
        }
            

    }

    @FXML
    void editSalesInvoice(ActionEvent event) {

    }

    @FXML
    void printPurchaseInvoice(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {
        setTableData();
        filterTableData();

    }
    
    private void setTableData()
    {
        purchaseInvoicesList = DatabaseHandler.getPurchaseInvoicesList();
        colDate.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceHeader, String>("date"));
        colPurchaseInvoiceNumber.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceHeader, Integer>("id"));
        colSupplierNameName.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceHeader, String>("supplierName"));
        colTotal.setCellValueFactory(new PropertyValueFactory<PurchaseInvoiceHeader, Double>("purchaeInvoiceTotalCost"));
        
        //tbPurchaseInvoicesList.getSortOrder().addAll(colSupplierNameName);
        //colPurchaseInvoiceNumber.setComparator(Collator.getInstance().reversed());
        //colPurchaseInvoiceNumber.setSortNode(new Group());
        
        tbPurchaseInvoicesList.setItems(purchaseInvoicesList);
    }
    
    private void filterTableData()
    {
        purchaseInvoicesList = DatabaseHandler.getPurchaseInvoicesList();
        FilteredList<PurchaseInvoiceHeader> filteredData = new FilteredList<>(purchaseInvoicesList, b -> true);
        txtSupplierName.textProperty().addListener((observable, oldValue, newValue)->{
           filteredData.setPredicate((Predicate<PurchaseInvoiceHeader>) invoiceHeader ->{
               if(newValue == null ||   newValue.isEmpty()){
                   return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               if(invoiceHeader.getSupplierName().contains(newValue)){
                   return true;
               } else if(String.valueOf(invoiceHeader.getId()).toLowerCase().contains(newValue)){
                   return true;
               }
               
               return false;
           });
        });
        SortedList<PurchaseInvoiceHeader> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbPurchaseInvoicesList.comparatorProperty());
        tbPurchaseInvoicesList.setItems(sortedData); 
    }

    private void updateProductQtyAndCost(Product product) {
        
    }
    
}
