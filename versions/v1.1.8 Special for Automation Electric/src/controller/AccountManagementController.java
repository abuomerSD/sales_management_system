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
public class AccountManagementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private Label lbCustomersCount;
    private Label lbSuppliersCount;
    
    
    @FXML
    private void showAddCustomerStage(){
        StageShower stageShower = new StageShower();
        stageShower.show("/view/add_Cumstomer.fxml", "اضافة عميل", false);
    }
    
    @FXML
    private void showAddSupplierStage(){
        StageShower stageShower = new StageShower();
        stageShower.show("/view/addSupplier.fxml", "اضافة مورد", false);
    }
    
    @FXML
    private void showCustomersListStage(){
        
    }
    
    @FXML 
    private void showSuppliersListStage()
    {
        
    }
    
    @FXML
    private void showCustomersAccountStage()
    {
        
    }
    
    @FXML
    private void showSuppliersAccountStage()
    {
        
    }
}
