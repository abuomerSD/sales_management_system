/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import model.InvoiceDetails;
import model.InvoiceHeader;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class EditSalesInvoiceController extends InvoiceController implements Initializable  {
    
    //ObservableList<InvoiceDetails> invoiceDetailsList  = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public void saveInvoice(ActionEvent event)
    {
        
        double totalCost = 0.0;
        double costForEveryProduct;
        for (int i = 0 ; i < super.invoiceDetailsList.size() ; i++)
        {
            InvoiceDetails invoiceDetails = (InvoiceDetails) invoiceDetailsList.get(i);
            costForEveryProduct = DatabaseHandler.getProductCost(invoiceDetails.getProductName()) * invoiceDetails.getProductQuantity();
            totalCost = totalCost + costForEveryProduct;
        }
        
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        
        invoiceHeader.setCustomerName(choiceBoxCustomerName.getValue().toString());
        invoiceHeader.setDate(txtDate.getValue().toString());
        invoiceHeader.setDiscount(Double.valueOf(txtDiscount.getText()));
        invoiceHeader.setNumber(Integer.valueOf(txtInvoiceNumber.getText()));
        invoiceHeader.setPayType(choiceBoxSalesInvoiceType.getValue().toString());
        invoiceHeader.setTax(Double.valueOf(txtTax.getText()));
        invoiceHeader.setTotal(Double.valueOf(txtInvoiceTotal.getText()));
        invoiceHeader.setTotalCost(totalCost);
        
        try
        {
            int customerID1 = DatabaseHandler.getCustomerID(choiceBoxCustomerName.getValue().toString());
            invoiceHeader.setCumstomerID(customerID1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
        DatabaseHandler.editSalesInvoice(invoiceHeader, super.invoiceDetailsList);
        btnSaveInvoice.setDisable(true);
    }
    
    public void getOldInvoiceData(InvoiceHeader header){
        System.out.println("from edit controller");
        
        setInvoiceTableData();
        List<InvoiceDetails> list = DatabaseHandler.getSalesInvoiceDetailsList(header.getNumber());
        for(int i = 0 ; i< list.size() ; i++ )
        {
            InvoiceDetails invoiceDetails = (InvoiceDetails) list.get(i);
            invoiceDetailsList.add(invoiceDetails);
            System.out.println("adding "+ invoiceDetails.getProductName());
        }
        //this.invoiceDetailsList = (ObservableList) DatabaseHandler.getSalesInvoiceDetailsList(header.getNumber());
        super.tbInvoice.setItems(invoiceDetailsList);
        txtDate.setValue(LocalDate.now());
        txtDiscount.setText(String.valueOf(header.getDiscount()));
        txtInvoiceNumber.setText(String.valueOf(header.getNumber()));
        txtInvoiceTotal.setText(String.valueOf(header.getTotal()));
        txtTax.setText(String.valueOf(header.getTax()));
        
        super.setSearchTableItems();
        super.setCustomersList();
        super.addSalesInvoiceTypes();
        super.choiceBoxSalesInvoiceType.setValue(header.getPayType());
        super.choiceBoxCustomerName.setValue(header.getCustomerName());
    }
    
    void setInvoiceTableData()
    {
        
            colInvoiceProductName.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, String>("productName"));
            colInvoiceProductQTY.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Double>("productQuantity"));
            colInvoiceProductPrice.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Double>("productPrice"));
            colInvoiceProductTotal.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Double>("productTotalSale"));
            colInvoiceColumnNumber.setCellValueFactory(new PropertyValueFactory<InvoiceDetails, Integer>("colNumber"));
            
            
    }
}
