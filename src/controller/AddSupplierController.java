/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class AddSupplierController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML private JFXTextField txtSuppliername;
    
    @FXML
    private void addSupplier(){
        String supplierName = "";
        try{
          supplierName = txtSuppliername.getText();
          if(!supplierName.equals(""))
          {
            DatabaseHandler.addSupplier(supplierName);  
            AlertMaker.showInformationAlert("تم اضافة " + supplierName + " بنجاح");
            txtSuppliername.clear();
            txtSuppliername.requestFocus();
          }
          else
          {
              AlertMaker.showErrorAlert("ادخل اسم المورد اولا");
          }
          
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
}
