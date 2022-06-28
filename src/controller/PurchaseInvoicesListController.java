/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InvoiceHeader;
import model.Product;
import model.ProductMovement;
import model.PurchaseInvoiceDetails;
import model.PurchaseInvoiceHeader;
import resources.AlertMaker;
import resources.DatabaseHandler;
import static resources.DatabaseHandler.getProductCode;
import resources.ReportViewer;

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
        //disableEditInvoiceButton();
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
    private JFXButton btnEditInvoice;

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
                double productPreviousCost = DatabaseHandler.getProductPrevoiusCost(details.getProductName());
                DatabaseHandler.updateProductValues(details.getProductName(), DatabaseHandler.getProductQTY(details.getProductName()) - details.getProductQTY(), details.getProductCost(),productPreviousCost);
                // adding product Movement
                     ProductMovement productMovement = new ProductMovement();
                     productMovement.setCurrentQuantity(DatabaseHandler.getProductQTY(details.getProductName()));
                     productMovement.setDate(LocalDate.now().toString());
                     productMovement.setDetails("حذف فاتورة مشتروات رقم : "+ purchaseInvoiceHeader.getId());
                     productMovement.setInQuantity(0);
                     productMovement.setOutQuantity(details.getProductQTY());
                     productMovement.setProdcutName(details.getProductName());
                     productMovement.setProductCode(getProductCode(details.getProductName()));
                     productMovement.setPurchaseInvoiceID(0);
                     productMovement.setSalesInvoiceID(0);
                     DatabaseHandler.addProductMovement(productMovement);
            }
            System.out.println("Deleteing ");
            DatabaseHandler.deletePurchaseInvoice(purchaseInvoiceHeader.getId());
            //AlertMaker.showInformationAlert("تم حذف الفاتورة بنجاح");
            
            // UPDATING PRODUCT COST 
            
            for (PurchaseInvoiceDetails details : purchaseInvoiceDetailsList)
            {
                        int previousPurchaseInvoiceId = DatabaseHandler.getPreviousPurchaseInvoiceId(details.getProductName());
                        double previousCost = DatabaseHandler.getPreviousProductCost(previousPurchaseInvoiceId, details.getProductName());
                        Product product = new Product();
                        product.setProductCost(previousCost);
                        product.setProductName(details.getProductName());
                        DatabaseHandler.updateProductCost(product);
            }
            
            
            
            setTableData();
            filterTableData();
            
        }
            

    }

    @FXML
    void editPurchaseInvoice(ActionEvent event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPurchaseInvoice.fxml"));
            Parent parent = loader.load();
            EditPurchaseInvoiceController controller = (EditPurchaseInvoiceController) loader.getController();
            
            PurchaseInvoiceHeader header = tbPurchaseInvoicesList.getSelectionModel().getSelectedItem();
            controller.getOldInvoiceData(header);
            controller.enableConrols();
            Stage stage = new Stage();
            stage.setTitle("تعديل فاتورة المشتروات");
            stage.setScene(new Scene(parent));
            stage.setMaximized(true);
            stage.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            AlertMaker.showErrorAlert(ex.getMessage());
        }

    }

    @FXML
    void printPurchaseInvoice(ActionEvent event) {
        if (tbPurchaseInvoicesList.getSelectionModel().getSelectedItem() == null) {
            AlertMaker.showErrorAlert("من فضلك اختر فاتورة اولا");
            return;
        }
        PurchaseInvoiceHeader purchaseInvoiceHeader = tbPurchaseInvoicesList.getSelectionModel().getSelectedItem();
        
        
        ReportViewer reportViewer = new ReportViewer();
        String reportName = "puchaseInvoice.jrxml";
        
        Map paramters = new HashMap();
        
        paramters.put("invoiceNumber", purchaseInvoiceHeader.getId());
        paramters.put("supplierName", purchaseInvoiceHeader.getSupplierName());
        paramters.put("date", purchaseInvoiceHeader.getDate());
        paramters.put("total", purchaseInvoiceHeader.getPurchaeInvoiceTotalCost());
        
        
        List<PurchaseInvoiceDetails> list = DatabaseHandler.getPurchaseInvoicesDetails(purchaseInvoiceHeader.getId());
        
        reportViewer.showReport(reportName, paramters, list);
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

    private void disableEditInvoiceButton() {
        btnEditInvoice.setDisable(true);
    }
}
