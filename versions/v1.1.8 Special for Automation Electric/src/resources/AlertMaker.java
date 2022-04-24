/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javafx.scene.control.Alert;

/**
 *
 * @author eltayeb
 */
public class AlertMaker {
    
    public static void showErrorAlert(String errorText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorText);
        alert.show();
    }
    
    
    public static void showInformationAlert(String informationText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setContentText(informationText);
        alert.show();
    }
}
