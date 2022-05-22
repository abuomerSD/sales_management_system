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
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.InventoryAdjustment;
import resources.AlertMaker;
import resources.DatabaseHandler;
import resources.ReportViewer;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class ProductAdjustmentReportController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private TextField txtProductCode;

    @FXML
    void printReport(ActionEvent event) {
        if(txtProductCode.getText().isEmpty()){
            String sql = "SELECT * from tbInventoryAdjustment ";
            List<InventoryAdjustment> adjustmentList = DatabaseHandler.getInventoryAdjustmentsBySQL(sql);
            if(adjustmentList.isEmpty())
            {
                AlertMaker.showErrorAlert("لا توجد تسويات مخزنية");
                return;
            }
            Map map = new HashMap();
            ReportViewer rv = new ReportViewer();
            rv.showReport("inventoryAdjustmentReport.jrxml", map, adjustmentList);
        }
        else
        {
            String sql = "SELECT * from tbInventoryAdjustment WHERE productCode = '"+txtProductCode.getText()+"';";
            List<InventoryAdjustment> adjustmentList = DatabaseHandler.getInventoryAdjustmentsBySQL(sql);
            if(adjustmentList.isEmpty())
            {
                AlertMaker.showErrorAlert("لا توجد تسويات مخزنية");
                return;
            }
            Map map = new HashMap();
            ReportViewer rv = new ReportViewer();
            rv.showReport("inventoryAdjustmentReport.jrxml", map, adjustmentList);
        }

    }
    
}
