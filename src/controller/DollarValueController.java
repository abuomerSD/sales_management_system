/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import resources.AlertMaker;
import resources.DatabaseHandler;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class DollarValueController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private JFXTextField txtDollarValue;

    @FXML
    void updateDollarValue(ActionEvent event) {
        double dollarValue = Double.valueOf(txtDollarValue.getText());
        try
        {
            DatabaseHandler.updateDollarValue(dollarValue);
            StageShower stageShower  =new StageShower();
            stageShower.show("/view/main_stage.fxml", "الواجهة الرئيسية", true);
            Stage stage = (Stage) txtDollarValue.getScene().getWindow();
            stage.close();
        }
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }

    }

    
    
}
