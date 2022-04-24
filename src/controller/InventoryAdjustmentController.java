/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.InventoryAdjustment;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class InventoryAdjustmentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML private JFXTextField txtProductCode;
    @FXML private JFXTextField txtProductQTY;
    @FXML private JFXTextField txtDetails;
    
    
    
    @FXML
    private void addInventoryAdjustment()
    {
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            Date date = new Date();
            String formatedDate = simpleDateFormat.format(date);
            String productCode = txtProductCode.getText();
            String productName = DatabaseHandler.getproductName(productCode);
            double adjustmentQTY = Double.valueOf(txtProductQTY.getText());
            String details = txtDetails.getText();
            double qtyBeforeAdjustment = DatabaseHandler.getProductQTY(productName);
            double qtyAfterAdjustment = qtyBeforeAdjustment + adjustmentQTY;
            
            InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();
            
            inventoryAdjustment.setDate(formatedDate);
            inventoryAdjustment.setProductCode(productCode);
            inventoryAdjustment.setProductName(productName);
            inventoryAdjustment.setAdjustmentQTY(adjustmentQTY);
            inventoryAdjustment.setDetails(details);
            inventoryAdjustment.setProductQtyAfterAdjustment(qtyAfterAdjustment);
            
            DatabaseHandler.addInventoryAdjustment(inventoryAdjustment);
            AlertMaker.showInformationAlert("تم اضافة التسوية المخزنية بنجاح للصنف : "+productName);
            txtDetails.clear();
            txtProductCode.clear();
            txtProductQTY.clear();
            txtProductCode.requestFocus();
            
        }
        catch(Exception ex){
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
}
