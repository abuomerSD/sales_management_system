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
import resources.DatabaseHandler;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class PurchaseManagementController implements Initializable {
    @FXML
    private Label lbCustomersCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCountLabels();
    }    
    
    
    @FXML
    private void showNewPurchaseInvoiceStage(){
        StageShower stageShower = new StageShower();
        stageShower.show("/view/purchaseInvoice.fxml", "فاتورة مشتروات", true);
    }
    
    @FXML
    private void showPurchaseInvoiceListStage(){
        StageShower stageShower = new StageShower();
        stageShower.show("/view/purchase_invoices_list.fxml", "قائمة فواتير المشتروات", true);
    }
    
    @FXML
    private void showPurchaseInvoiceReportsStage(){
        StageShower stageShower = new StageShower();
        stageShower.show("/view/purchaseReports.fxml", "قائمة فواتير المشتروات", false); 
    }

    private void setCountLabels() {
       lbCustomersCount.setText(String.valueOf(DatabaseHandler.getPurchaseInvoicesCount()));
    }
    
    
}
