/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.UserDetails;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class SettingsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    }    
    
    @FXML
    private TableColumn<UserDetails, String> colPassWord;

    @FXML
    private TableColumn<UserDetails, Integer> colCustomersPer;

    @FXML
    private TableColumn<UserDetails, Integer> colSalesPer;

    @FXML
    private TableView<UserDetails> tbUsers;

    @FXML
    private TableColumn<UserDetails, Integer> colInventoryPer;

    @FXML
    private TableColumn<UserDetails, Integer> colPurchasePer;

    @FXML
    private TextField txtUsername;

    @FXML
    private CheckBox cbInventoryPermission;

    @FXML
    private CheckBox cbSalesPermission;

    @FXML
    private CheckBox cbPurchasePermission;

    @FXML
    private TableColumn<UserDetails, Integer> colId;

    @FXML
    private CheckBox cbCustomersPermission;

    @FXML
    private TableColumn<UserDetails, String> colUsername;

    @FXML
    private PasswordField txtPassword;
    
    ObservableList<UserDetails> usersList ;

    @FXML
    void getUserFromTable(ActionEvent event) {
        UserDetails user = tbUsers.getSelectionModel().getSelectedItem();
        txtUsername.setText(user.getUserName());
        txtPassword.setText(user.getPassWord());
        
        if(user.getSalesPermissions() == 1)
            cbSalesPermission.setSelected(true);
        if(user.getPurchasePermissions() == 1)
            cbPurchasePermission.setSelected(true);
        if(user.getInventoryPermissions() == 1)
            cbInventoryPermission.setSelected(true);
        if(user.getCustomersAndSuppliersPermissions() == 1)
            cbCustomersPermission.setSelected(true);
    }

    @FXML
    void addUser(ActionEvent event) {
        UserDetails user = new UserDetails();
        try
        {
            user.setUserName(txtUsername.getText());
            user.setPassWord(txtPassword.getText());
            
            if(cbSalesPermission.isSelected()) {
                user.setSalesPermissions(1);
            }
            else
            {
                user.setSalesPermissions(0);
            }
            
            if(cbPurchasePermission.isSelected()){
                user.setPurchasePermissions(1);
            }
            else{
                user.setPurchasePermissions(0);
            }
            
            if(cbInventoryPermission.isSelected()){
                user.setInventoryPermissions(1);
            }
            else{
                user.setInventoryPermissions(0);
            }
            
            if(cbCustomersPermission.isSelected()){
                user.setCustomersAndSuppliersPermissions(1);
            }
            else{
                user.setCustomersAndSuppliersPermissions(0);
            }
            
            DatabaseHandler.addUser(user);
            fillTable();
            clearEnteries();
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    void editUser(ActionEvent event) {
        UserDetails user = tbUsers.getSelectionModel().getSelectedItem();
        try
        {
            user.setUserName(txtUsername.getText());
            user.setPassWord(txtPassword.getText());
            
            if(cbSalesPermission.isSelected()) {
                user.setSalesPermissions(1);
            }
            else
            {
                user.setSalesPermissions(0);
            }
            
            if(cbPurchasePermission.isSelected()){
                user.setPurchasePermissions(1);
            }
            else{
                user.setPurchasePermissions(0);
            }
            
            if(cbInventoryPermission.isSelected()){
                user.setInventoryPermissions(1);
            }
            else{
                user.setInventoryPermissions(0);
            }
            
            if(cbCustomersPermission.isSelected()){
                user.setCustomersAndSuppliersPermissions(1);
            }
            else{
                user.setCustomersAndSuppliersPermissions(0);
            }
            
            DatabaseHandler.updateUser(user);
            fillTable();
            clearEnteries();
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
        }
    } 
    
    private void fillTable()
    {
     usersList = DatabaseHandler.getAllUsers();
     colCustomersPer.setCellValueFactory(new PropertyValueFactory<UserDetails, Integer>("customersAndSuppliersPermissions"));
     colId.setCellValueFactory(new PropertyValueFactory<UserDetails, Integer>("id"));
     colInventoryPer.setCellValueFactory(new PropertyValueFactory<UserDetails, Integer>("inventoryPermissions"));
     colPassWord.setCellValueFactory(new PropertyValueFactory<UserDetails, String>("passWord"));
     colPurchasePer.setCellValueFactory(new PropertyValueFactory<UserDetails, Integer>("purchasePermissions"));
     colSalesPer.setCellValueFactory(new PropertyValueFactory<UserDetails, Integer>("salesPermissions"));
     colUsername.setCellValueFactory(new PropertyValueFactory<UserDetails, String>("userName"));
     
     tbUsers.setItems(usersList);
    }
    
    private void clearEnteries(){
        txtPassword.clear();
        txtUsername.clear();
        cbCustomersPermission.setSelected(false);
        cbInventoryPermission.setSelected(false);
        cbPurchasePermission.setSelected(false);
        cbSalesPermission.setSelected(false);
        txtUsername.requestFocus();
    }
}
