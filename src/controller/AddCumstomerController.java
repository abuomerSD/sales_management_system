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
import javafx.geometry.Point2D;
import model.Customer;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class AddCumstomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private JFXTextField txtCusmtomerName;

    @FXML
    void addCustomer(ActionEvent event) {
        Customer customer = new Customer();
        customer.setName(txtCusmtomerName.getText());
        customer.setTotalPurchase(0);
        
        if(txtCusmtomerName.getText() == ""){
            
            AlertMaker.showErrorAlert("ادخل اسم العميل");
        }
        else{
            DatabaseHandler.addCustomer(customer);
            AlertMaker.showInformationAlert(customer.getName()+" Added");
            txtCusmtomerName.clear();
        }

    }
    
    
}
