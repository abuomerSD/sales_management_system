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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class MainWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    void addInvoice(ActionEvent event) {
        String newInvoiceURL = "/view/invoice.fxml";
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(newInvoiceURL));
            Stage stage =  new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Invoice");
            stage.show();
        } catch(Exception ex){
            ex.getMessage();
            ex.printStackTrace();
        }     

    }

    
    
    @FXML
    void addItem(ActionEvent event) {
        String newInvoiceURL = "/view/add_Product.fxml";
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(newInvoiceURL));
            Stage stage =  new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add Product");
            stage.show();
        } catch(Exception ex){
            ex.getMessage();
            ex.printStackTrace();
        }
        
    }
    
    @FXML
    void addCustomer(ActionEvent event){
        String newInvoiceURL = "/view/add_Cumstomer.fxml";
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(newInvoiceURL));
            Stage stage =  new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add Customer");
            stage.show();
        } catch(Exception ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }
    
    
    
    
    
    
}
