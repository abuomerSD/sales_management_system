/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.PurchaseInvoiceHeader;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class PurchaseInvoicesListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private TableView<PurchaseInvoiceHeader> tbPurchaseInvoicesList;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, Double> colTotal;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, String> colSupplierNameName;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, String> colDate;

    @FXML
    private TableColumn<PurchaseInvoiceHeader, Integer> colPurchaseInvoiceNumber;

    @FXML
    void deletePurchaseInvoice(ActionEvent event) {

    }

    @FXML
    void editSalesInvoice(ActionEvent event) {

    }

    @FXML
    void printPurchaseInvoice(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {

    }
    
    
}
