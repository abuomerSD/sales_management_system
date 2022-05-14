/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.PurchaseInvoiceHeader;
import resources.DatabaseHandler;
import resources.ReportViewer;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class PurchaseReportsController implements Initializable {
  
    @FXML
    private TextField txtSupplierID;
    @FXML
    private TextField txtToInvoice;
    @FXML
    private TextField txtFromInvoice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void printReport()
    {
        String supplierID = txtSupplierID.getText();
        String fromInvoice = txtFromInvoice.getText();
        String toInvoice = txtToInvoice.getText();
        
        String sql = " SELECT * FROM tbPurchaseInvoiceHeader";
        
        if(fromInvoice.equals("") || toInvoice.equals(""))
        {
            sql = sql;
            System.out.println(sql);
        }
        if(supplierID.isEmpty() && !fromInvoice.isEmpty() && ! toInvoice.isEmpty())
        {
            sql = sql.concat(" WHERE id >= "+ fromInvoice +" and id <="+toInvoice+" ");
            System.out.println(sql);
        }
            
        if (!supplierID.isEmpty() && !fromInvoice.isEmpty() && !toInvoice.isEmpty())
        {
            sql = sql.concat(" AND supplierID =  "+ supplierID );
            System.out.println(sql);
        }
        
        if (!supplierID.isEmpty() && fromInvoice.isEmpty() && toInvoice.isEmpty())
        {
            sql = sql.concat(" WHERE supplierID =  "+ supplierID);
            System.out.println(sql);
        } 
        
        List<PurchaseInvoiceHeader> invoiceHeaderList = DatabaseHandler.getAllPurchaseInvoicesHeadersBySQL(sql);
        invoiceHeaderList.forEach(System.out::println);
        
        ReportViewer reportViewer = new ReportViewer();
        reportViewer.showReport("SalesInvoicesReport.jrxml", new HashMap(), invoiceHeaderList);
        
    }
    
}
