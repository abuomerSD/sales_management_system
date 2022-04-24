/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.DatabaseHandler;
import resources.StageShower;

/**
 *
 * @author eltayeb
 */
public class Main extends Application{

    @Override
    public void start(Stage stage){
        String stageURL = "/view/dollarValue.fxml";
        
        StageShower stageShower = new StageShower();
        stageShower.show(stageURL, "الواجهة الرئيسية", false);
        DatabaseHandler.createTables();
        
    }
    
    
    public static void main(String [] args){
        launch(args);
    }
    
    
}
