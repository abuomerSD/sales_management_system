/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Product;
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
        txtProductCode.setDisable(true);
    }  
    
    @FXML
    private JFXTextField txtProductPrice;

    @FXML
    private JFXTextField txtProductCode;

    @FXML
    private JFXTextField txtProductCost;
    
    @FXML
    private JFXTextField txtProductName;

    @FXML
    void getProductData(ActionEvent event) {
        String code = txtProductCode.getText();
        String name = DatabaseHandler.getproductName(code);
        Double cost = DatabaseHandler.getProductCost(name);
        Double price = DatabaseHandler.getProductPrice(name);
        
        txtProductCost.setText(cost.toString());
        txtProductPrice.setText(price.toString());
        txtProductName.setText(name);

    }

    @FXML
    void editProduct(ActionEvent event) {
        DecimalFormat df = new DecimalFormat("#.0000");
        
        txtProductCode.setDisable(true);
        
        double cost = Double.valueOf(df.format(Double.valueOf(txtProductCost.getText())));
        double price = Double.valueOf(df.format(Double.valueOf(txtProductPrice.getText())));
        String code = txtProductCode.getText();
        String name = txtProductName.getText();
        
        try
        {
            DatabaseHandler.editProduct(cost,name, price, code);
            AlertMaker.showInformationAlert("تم تعديل الصنف بنجاح : "+ DatabaseHandler.getproductName(code));
            txtProductCode.clear();
            txtProductCost.clear();
            txtProductName.clear();
            txtProductPrice.clear();
            txtProductCode.requestFocus();
        }
        
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }

    }
    
    public void setProductDataForEdit(Product product)
    {
        txtProductCode.setText(product.getProductCode());
        txtProductCost.setText(String.valueOf(product.getProductCost()));
        txtProductName.setText(product.getProductName());
        txtProductPrice.setText(String.valueOf(product.getProductPrice()));
    }
    
}
