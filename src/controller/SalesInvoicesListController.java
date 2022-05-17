/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
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
import model.InvoiceDetails;
import model.InvoiceHeader;
import resources.DatabaseHandler;
import resources.ReportViewer;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class SalesInvoicesListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSalesInvoicesTableData();
        filterSalesInvoiceListTable();
    }    
    
    ObservableList<InvoiceHeader> salesInvoicesList = FXCollections.observableArrayList();
    
    @FXML
    private TextField txtCustomerName;
    
    @FXML
    private TableView<InvoiceHeader> tbSalesInvoicesList;
    
    @FXML
    private TableColumn<InvoiceHeader, Double> colTotalCost;
    
    @FXML
    private TableColumn<InvoiceHeader, Double> colTotal;

    @FXML
    private TableColumn<InvoiceHeader, String> colCustomerName;

    @FXML
    private TableColumn<InvoiceHeader, Double> colTax;
    
    @FXML
    private TableColumn<InvoiceHeader, String> colPayType;

    @FXML
    private TableColumn<InvoiceHeader, String> colDate;

    @FXML
    private TableColumn<InvoiceHeader, Double> colDiscount;

    @FXML
    private TableColumn<InvoiceHeader, Integer> colInvoiceNumber;
    
    
    

    @FXML
    void deleteSalesInvoice(ActionEvent event) {
        InvoiceHeader invoiceHeader = tbSalesInvoicesList.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("هل تريد حذف الفاتورة ؟");
        alert.setTitle("تأكيد");
        Optional<ButtonType> response= alert.showAndWait();
        
        if (response.get().equals(ButtonType.OK) ) 
        {
            System.out.println("ok");
            DatabaseHandler.deleteSalesInvoice(invoiceHeader.getNumber());
            setSalesInvoicesTableData();
            
        }

    }

    @FXML
    void editSalesInvoice(ActionEvent event) {
//        StageShower stageShower = new StageShower();
//        stageShower.show("editSalesInvoice.fxml", "تعديل فاتورة مبيعات", true);
        
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editSalesInvoice.fxml"));
            Parent parent = loader.load();
            EditSalesInvoiceController controller = (EditSalesInvoiceController) loader.getController();
            
            InvoiceHeader header = tbSalesInvoicesList.getSelectionModel().getSelectedItem();
            controller.getOldInvoiceData(header);
            controller.enableControls();
            Stage stage = new Stage();
            stage.setTitle("تعديل فاتورة مبيعات");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    void printSalesInvoice(ActionEvent event) {
        InvoiceHeader invoiceHeader = tbSalesInvoicesList.getSelectionModel().getSelectedItem();
        System.out.println(invoiceHeader.getNumber());
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("invoiceNumber", invoiceHeader.getNumber());
        parameters.put("customerName", invoiceHeader.getCustomerName());
        parameters.put("tax", invoiceHeader.getTax());
        parameters.put("discount", invoiceHeader.getDiscount());
        parameters.put("total", invoiceHeader.getTotal());
        parameters.put("date", invoiceHeader.getDate());
        
        InvoiceDetails invoiceDetails;
        
        List<InvoiceDetails> list;
        list = DatabaseHandler.getSalesInvoiceDetailsList(invoiceHeader.getNumber());
        
        ReportViewer reportViewer = new ReportViewer();
        reportViewer.showReport("salesInvoiceOriginalDate.jrxml", parameters, list);

    }

    private void setSalesInvoicesTableData() {
        colCustomerName.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, String>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, String>("date"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, Double>("discount"));
        colInvoiceNumber.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, Integer>("number"));
        colPayType.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, String>("payType"));
        colTax.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, Double>("tax"));
        colTotal.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, Double>("total"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<InvoiceHeader, Double>("totalCost"));
        
        salesInvoicesList = DatabaseHandler.getSalesInvoicesList();
        tbSalesInvoicesList.setItems(salesInvoicesList);
        
    }
    
    private void filterSalesInvoiceListTable()
    {
        ObservableList productsList = DatabaseHandler.getSalesInvoicesList();
        FilteredList<InvoiceHeader> filteredData = new FilteredList<>(productsList, b -> true);
        txtCustomerName.textProperty().addListener((observable, oldValue, newValue)->{
           filteredData.setPredicate((Predicate<InvoiceHeader>) invoiceHeader ->{
               if(newValue == null ||   newValue.isEmpty()){
                   return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               if(invoiceHeader.getCustomerName().contains(newValue)){
                   return true;
               } else if(String.valueOf(invoiceHeader.getNumber()).toLowerCase().contains(newValue)){
                   return true;
               }
               
               return false;
           });
        });
        SortedList<InvoiceHeader> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbSalesInvoicesList.comparatorProperty());
        tbSalesInvoicesList.setItems(sortedData);
    }
    
    @FXML
    private void refreshTable()
    {
        setSalesInvoicesTableData();
        filterSalesInvoiceListTable();
    }
    
}
