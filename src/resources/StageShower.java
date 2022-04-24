/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author eltayeb
 */
public class StageShower {
    
    public void show(String stageURL, String title, boolean isMaximized)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource(stageURL));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(isMaximized);
            stage.show();
        } catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
