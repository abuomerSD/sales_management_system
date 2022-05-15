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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supplier;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class SuppliersListController implements Initializable {
    @FXML
    private TextField txtSupplierName;
    @FXML
    private TableView<Supplier> tbSuppliers;
    @FXML
    private TableColumn<Supplier, Integer> colSupplierID;
    @FXML
    private TableColumn<Supplier, String> colSupplierName;
    
    ObservableList<Supplier> suppliersList ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    }    

    @FXML
    private void editSupplierName(ActionEvent event) {
        
        if (! txtSupplierName.getText().isEmpty()){
            Supplier supplier = tbSuppliers.getSelectionModel().getSelectedItem();
            supplier.setName(txtSupplierName.getText());
            
            try
            {
               DatabaseHandler.updateSupplierName(supplier);
               AlertMaker.showInformationAlert("تم تعديل اسم المورد بنجاح");
               fillTable();
               txtSupplierName.clear();
            }
            catch(Exception ex)
            {
                AlertMaker.showErrorAlert(ex.getMessage());
                ex.printStackTrace();
            }
        }
        else 
        {
            AlertMaker.showErrorAlert("ادخل الاسم اولا");
        }
    }

    private void fillTable() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
        
        suppliersList = DatabaseHandler.getSuppliersList();
        tbSuppliers.setItems(suppliersList);
    }
    
    
  
}
