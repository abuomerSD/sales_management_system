/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.PurchaseInvoiceDetails;
import model.PurchaseInvoiceHeader;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class EditPurchaseInvoiceController extends PurchaseInvoiceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public void savePurchaseInvoice(ActionEvent event){
        PurchaseInvoiceHeader header = new PurchaseInvoiceHeader();
        List<PurchaseInvoiceDetails> detailsList = new ArrayList();
        
        header.setDate(txtDate.getValue().toString());
        header.setId(Integer.valueOf(txtInvoiceNumber.getText()));
        header.setPurchaeInvoiceTotalCost(Double.valueOf(txtPurchaseInvoiceTotal.getText()));
        header.setSupplierID(DatabaseHandler.getSupplierID(choiceBoxSupplierName.getValue()));
        header.setSupplierName(choiceBoxSupplierName.getValue());
        
        DatabaseHandler.editPurchaseInvoice(header, productsList);
        btnSaveInvoice.setDisable(true);
        
    }
    
    public void getOldInvoiceData(PurchaseInvoiceHeader header)
    {
       super.setSearchTableItems();
       
       List<PurchaseInvoiceDetails> purchaseInvoiceDetailsList = DatabaseHandler.getPurchaseInvoicesDetails(header.getId());
       
       setPurchaseInvoiceTable();
       setPurchaseInvoiceTableValues(purchaseInvoiceDetailsList);
       txtDate.setValue(LocalDate.now());
       txtInvoiceNumber.setText(String.valueOf(header.getId()));
       txtPurchaseInvoiceTotal.setText(String.valueOf(header.getPurchaeInvoiceTotalCost()));
       
       setSuppliersList();
       super.choiceBoxSupplierName.setValue(header.getSupplierName());
       
       
    }
    
    void setPurchaseInvoiceTableValues(List<PurchaseInvoiceDetails> list){
        productsList.clear();
        for(int i = 0 ; i < list.size() ; i++)
       {
           PurchaseInvoiceDetails details = list.get(i);
           productsList.add(details);
       }
        tbPurchaseInvoice.setItems(productsList);
    }
}
