/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;
import resources.AlertMaker;
import resources.DatabaseHandler;
import resources.ReportViewer;
import resources.StageShower;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class ProductListControlController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ObservableList<Product> productsList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //btnEtidProduct.setDisable(true);
        setProductTableItems();
        filterProductTableItems();
//        btnRefreshTable.setDisable(true);
//        btnEtidProduct.setDisable(true);
//        btnDelete.setDisable(true);
    }   
    
    
    @FXML
    private JFXButton btnDelete;
    
    @FXML
    private JFXButton btnEtidProduct;
    
    @FXML
    private TableColumn<Product, Double> colQty;

    @FXML
    private TableColumn<Product, String> colProductPrice;

    @FXML
    private TableView<Product> tbProduct;

    @FXML
    private TableColumn<Product, String> colProductCode;

    @FXML
    private TableColumn<Product, Double> colProductCost;

    @FXML
    private TextField txtProductName;

    @FXML
    private TableColumn<Product, String> colProductName;
    
    @FXML private JFXButton btnRefreshTable;
    
    
    
    /**
    * this method is used to delete the  products
    * 
    * 
    */
    
//    @FXML
//    void deleteAllProducts(ActionEvent event)
//    {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setContentText("هل تريد حذف جميع الاصناف ؟");
//        alert.setHeaderText("تأكيد");
//        Optional<ButtonType> response = alert.showAndWait();
//        
//        if(response.get().equals( ButtonType.OK))
//        {
//            DatabaseHandler.deleteAllProducts();
//            productsList.clear();
//            productsList = DatabaseHandler.getProductsListWithDollarValue();
//            setProductTableItems();
//        }
//    }
    
    /**
     * this method is used to edit the selected product at the table view
     *  
     */

    @FXML
    void editProductDetails(ActionEvent event) {
        StageShower stageShower = new StageShower();
        stageShower.show("/view/editProduct.fxml", "تعديل الصنف", false);

    }
    
    private void setProductTableItems(){
        productsList = DatabaseHandler.getProductsListWithDollarValue();
        colProductCode.setCellValueFactory(new PropertyValueFactory<Product, String>("productCode"));
        colProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<Product, Double>("productQuantity"));
        colProductCost.setCellValueFactory(new PropertyValueFactory<Product, Double>("productCost"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("productPrice"));
        
        tbProduct.setItems(productsList);

    }

    private void filterProductTableItems() {
        ObservableList productsList = DatabaseHandler.getProductsListWithDollarValue();
        FilteredList<Product> filteredData = new FilteredList<>(productsList, b -> true);
        txtProductName.textProperty().addListener((observable, oldValue, newValue)->{
           filteredData.setPredicate((Predicate<Product>) product ->{
               if(newValue == null ||   newValue.isEmpty()){
                   return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               if(product.getProductCode().contains(newValue)){
                   return true;
               } else if(product.getProductName().toLowerCase().contains(newValue)){
                   return true;
               }
               return false;
           });
        });
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbProduct.comparatorProperty());
        tbProduct.setItems(sortedData);
    }
    
    @FXML
    private void printAllProducts()
    {
        List<Product> list = new ArrayList<Product>();
        

        
        for(int i = 0; i<productsList.size(); i++)
        {
            Product product = productsList.get(i);
            list.add(product);
        }
        
        
        Map parametersMap = new HashMap();
        //parametersMap.put(list, list)
        
        ReportViewer reportViewer = new ReportViewer();
        reportViewer.showReport("InventoryFullReport.jrxml", parametersMap, productsList);
        
    }
    
    @FXML
    private void refreshTable()
    {
        setProductTableItems();
        filterProductTableItems();
    }
    
}
