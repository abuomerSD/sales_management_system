/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserDetails;
import resources.AlertMaker;
import resources.DatabaseHandler;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassWord;

    @FXML
    void login(ActionEvent event) {
        try {
            List<UserDetails> usersList = DatabaseHandler.getAllUsers();
            
            Map<String, String> usersMap = new HashMap<String, String>();
            
            for(UserDetails user : usersList)
            {
                usersMap.put(user.getUserName(), user.getPassWord());
                if(txtUserName.getText().equals(user.getUserName()) && txtPassWord.getText().equals(usersMap.get(user.getUserName())))
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_stage.fxml"));
                    Parent root = loader.load();
                    MainStageController controller = (MainStageController) loader.getController();
                    controller.setUserWindowOptions(user);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("الواجهة الرئيسية");
                    stage.setMaximized(true);
                    stage.show();
                    Stage oldStage = (Stage) txtUserName.getScene().getWindow();
                    oldStage.close();
                }
//                else
//                {
//                    AlertMaker.showErrorAlert("من فضلك ادخل بيانات المستخدم بصورة صحيحة");
//                }
            }
            
            
        } catch (Exception ex) {
            //AlertMaker.showErrorAlert("من فضلك ادخل بيانات المستخدم بصورة صحيحة");
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
        
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
    }
    
}
