/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class EditProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    private JFXTextField txtProductPrice;

    @FXML
    private JFXTextField txtProductCode;

    @FXML
    private JFXTextField txtProductCost;

    @FXML
    void getProductData(ActionEvent event) {
        String code = txtProductCode.getText();
        String name = DatabaseHandler.getproductName(code);
        Double cost = DatabaseHandler.getProductCost(name);
        Double price = DatabaseHandler.getProductPrice(name);
        
        txtProductCost.setText(cost.toString());
        txtProductPrice.setText(price.toString());

    }

    @FXML
    void editProduct(ActionEvent event) {
        double cost = Double.valueOf(txtProductCost.getText());
        double price = Double.valueOf(txtProductPrice.getText());
        String code = txtProductCode.getText();
        
        try
        {
            DatabaseHandler.editProduct(cost, price, code);
            AlertMaker.showInformationAlert("تم تعديل الصنف بنجاح : "+ DatabaseHandler.getproductName(code));
        }
        
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }

    }
    
}
