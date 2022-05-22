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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.ProductMovement;
import resources.AlertMaker;
import resources.DatabaseHandler;
import resources.ReportViewer;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class ProductMovementReportController implements Initializable {
    @FXML
    private TextField txtProductCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void printReport()
    {
        if(txtProductCode.getText().isEmpty())
        {
            String sql = "SELECT * FROM tbProductMovement";
            List<ProductMovement> productMovementList = DatabaseHandler.getProductMovementListBySQL(sql);
            if(productMovementList.isEmpty())
            {
                AlertMaker.showErrorAlert("لا توجد بيانات");
                return;
            }
            Map map = new HashMap();
            ReportViewer rv = new ReportViewer();
            rv.showReport("productMovementReport.jrxml", map, productMovementList);
        }
        else
        {
            String sql = "SELECT * FROM tbProductMovement WHERE productCode = '"+ txtProductCode.getText()+ "';";
            List<ProductMovement> productMovementList = DatabaseHandler.getProductMovementListBySQL(sql);
            if(productMovementList.isEmpty())
            {
                AlertMaker.showErrorAlert("لا توجد بيانات");
                return;
            }
            Map map = new HashMap();
            ReportViewer rv = new ReportViewer();
            rv.showReport("productMovementReport.jrxml", map, productMovementList);
        }
    }
    
}
