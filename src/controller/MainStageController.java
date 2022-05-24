/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.UserDetails;
import resources.AlertMaker;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class MainStageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        btnAccount.setDisable(true);
//        btnPurchaseManagement.setDisable(true);
//        btnSalesManagement.setDisable(true);
//        btnSettings.setDisable(true);
    }    
    
    @FXML
    private BorderPane borderBane;
    
     @FXML
    private JFXButton btnPurchaseManagement;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnAccount;

    @FXML
    private JFXButton btnSalesManagement;
    
    @FXML
    private JFXButton btnInventory;

    @FXML
    void showSalesManagmentStage(ActionEvent event) {
        loadWindow("/view/sales_management.fxml");

    }

    @FXML
    void showPurchaseManagmentStage(ActionEvent event) {
        loadWindow("/view/purchase_management.fxml");

    }

    @FXML
    void showInventoryManagmentStage(ActionEvent event) {
        loadWindow("/view/Inventory_management.fxml");

    }

    @FXML
    void showAccountManagmentStage(ActionEvent event) {
        loadWindow("/view/account_management.fxml");

    }

    @FXML
    void showSettingsStage(ActionEvent event) {
        loadWindow("/view/settings.fxml");

    }

    @FXML
    void showAboutStage(ActionEvent event) {
        loadWindow("/view/about.fxml");

    }
    
    private void loadWindow(String url){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(url));
            borderBane.setCenter(root);
        }
        catch(Exception ex){
            ex.printStackTrace();
            AlertMaker.showErrorAlert(ex.getMessage());
        }
    }
    
    public void setUserWindowOptions(UserDetails user)
    {
        if(user.getSalesPermissions() != 1)
            btnSalesManagement.setDisable(true);
        if(user.getPurchasePermissions() != 1)
            btnPurchaseManagement.setDisable(true);
        if(user.getInventoryPermissions() != 1)
            btnInventory.setDisable(true);
        if(user.getCustomersAndSuppliersPermissions() != 1)
            btnAccount.setDisable(true);
        if(user.getId() > 1)
            btnSettings.setDisable(true);
    }
        
    
    
}
