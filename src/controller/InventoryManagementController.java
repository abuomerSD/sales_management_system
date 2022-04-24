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
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class InventoryManagementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void showAddProductsStage()
    {
        StageShower stageShower = new StageShower();
        stageShower.show("/view/add_Product.fxml", "اضافة الاصناف", false);
    }
    
    @FXML
    private void showProductListStage()
    {
        StageShower stageShower = new StageShower();
        stageShower.show("/view/product_list_control.fxml", "قائمة الاصناف", true);
    }
    
    @FXML
    private void showAddInventoryAdjustmentStage()
    {
        StageShower stageShower = new StageShower();
        stageShower.show("/view/inventory_adjustment.fxml", "التسوية المخزنية", false);
    }
    
}
