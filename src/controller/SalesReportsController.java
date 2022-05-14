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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.InvoiceDetails;
import model.InvoiceHeader;
import resources.DatabaseHandler;
import resources.ReportViewer;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class SalesReportsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        @FXML
    private TextField txtFromInvoice;
        
    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtToInvoice;

    @FXML
    void printReport(ActionEvent event) 
    {
        
        String customerID = txtCustomerID.getText();
        String fromInvoice = txtFromInvoice.getText();
        String toInvoice = txtToInvoice.getText();
        
        String sql = " SELECT * FROM tbInvoiceHeader";
        
        if(fromInvoice.equals("") || toInvoice.equals(""))
        {
            sql = sql;
            System.out.println(sql);
        }
        if(customerID.isEmpty() && !fromInvoice.isEmpty() && ! toInvoice.isEmpty())
        {
            sql = sql.concat(" WHERE invoiceNumber >= "+ fromInvoice +" and invoiceNumber <="+toInvoice+" ");
            System.out.println(sql);
        }
            
        if (!customerID.isEmpty() && !fromInvoice.isEmpty() && !toInvoice.isEmpty())
        {
            sql = sql.concat(" AND customerID =  "+ customerID );
            System.out.println(sql);
        }
        
        if (!customerID.isEmpty() && fromInvoice.isEmpty() && toInvoice.isEmpty())
        {
            sql = sql.concat(" WHERE customerID =  "+ customerID);
            System.out.println(sql);
        } 
        
        List<InvoiceHeader> invoiceHeaderList = DatabaseHandler.getAllSalesInvoicesHeadersBySQL(sql);
        invoiceHeaderList.forEach(System.out::println);
        
        ReportViewer reportViewer = new ReportViewer();
        reportViewer.showReport("SalesInvoicesReport.jrxml", new HashMap(), invoiceHeaderList);
    }
    
}
