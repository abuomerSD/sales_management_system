/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class CustomersListController implements Initializable {
    @FXML
    private TableColumn<Customer, Integer> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableView<Customer> tbCustomers;
    
    ObservableList<Customer> customersList ;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone;
    @FXML
    private TextField txtCustomerPhone;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    }    
    
    private void fillTable(){

        colCustomerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        
        customersList = DatabaseHandler.getCusmtomerList();
        tbCustomers.setItems(customersList);
    }
    
    
    @FXML
    private void editCustomerName()
    {
        String customerName = txtCustomerName.getText();
        Customer customer = tbCustomers.getSelectionModel().getSelectedItem();
        customer.setName(customerName);
        customer.setPhone(txtCustomerPhone.getText());
        try {
            
            if(! txtCustomerName.getText().isEmpty())
            {
                DatabaseHandler.updateCustomerName(customer);
                AlertMaker.showInformationAlert("تم تعديل اسم العميل بنجاح");
                fillTable();
                txtCustomerName.clear();
            }
            else
            {
                AlertMaker.showErrorAlert("ادخل الاسم الجديد");
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(CustomersListController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            AlertMaker.showErrorAlert(ex.getMessage());
        }
    }
    
}
