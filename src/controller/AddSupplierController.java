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
import model.Supplier;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class AddSupplierController implements Initializable {
    @FXML
    private JFXTextField txtSupplierPhone;

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
        String supplierPhone = "";
        
        try{
          supplierName = txtSuppliername.getText();
          supplierPhone = txtSupplierPhone.getText();
          if(!supplierName.equals("") && !supplierPhone.equals(""))
          {
            Supplier supplier = new Supplier();
            supplier.setName(supplierName);
            supplier.setPhone(supplierPhone);
            DatabaseHandler.addSupplier(supplier);  
            AlertMaker.showInformationAlert("تم اضافة " + supplierName + " بنجاح");
            txtSuppliername.clear();
            txtSuppliername.requestFocus();
            txtSupplierPhone.clear();
          }
          else
          {
              AlertMaker.showErrorAlert("ادخل اسم المورد و رقم الهاتف اولا");
          }
          
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
}
