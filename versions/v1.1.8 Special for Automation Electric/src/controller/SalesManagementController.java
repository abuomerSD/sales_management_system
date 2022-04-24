/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class SalesManagementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private Label lbSalesInvoiceCount;
    
    @FXML
    private void showNewSalesInvoiceStage()
    {
        StageShower stageShower = new StageShower();
        stageShower.show("/view/invoice.fxml", "فاتورة مبيعات", true);
    }
    
    @FXML
    private void showSalesInvoicesList(){
        StageShower stageShower = new StageShower();
        stageShower.show("/view/sales_invoices_list.fxml", "فاتورة مبيعات", true);
    }
    
    @FXML
    private void showSalesReports(){
        
    }
    
}
