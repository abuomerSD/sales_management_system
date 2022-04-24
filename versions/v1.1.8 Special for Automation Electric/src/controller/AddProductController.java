/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import model.Product;
import model.ProductMovement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import resources.AlertMaker;
import resources.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author eltayeb
 */
public class AddProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public JFXTextField txtProductQuantity;

    @FXML
    public JFXTextField txtProducyCost;

    @FXML
    public JFXTextField txtProductPrice;

    @FXML
    public JFXTextField txtProductName;

    @FXML
    public JFXTextField txtProductCode;

    @FXML
    public JFXButton btnAddSingleProduct;

    @FXML
    public JFXButton btnAddMultiProducts;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label lbAddMultiProducts;

    @FXML
    void addMultiProduct(ActionEvent event) {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File Path");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Files", "*.xls"));

            File file = fileChooser.showOpenDialog(null);
            System.out.println(file.getPath());
            FileInputStream fileInput = new FileInputStream(new File(file.getPath()));

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    String sql = "INSERT INTO tbProduct (productCode, productName, productQuantity,"
                            + "productCost, productPrice) VALUES(?,?,?,?,?);";

                    Connection con = DatabaseHandler.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql);

                    XSSFWorkbook wb = new XSSFWorkbook(fileInput);
                    XSSFSheet sheet = wb.getSheetAt(0);
                    Row row;
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                        row = sheet.getRow(i);
                        //lbAddMultiProducts.setText("تم إضافة الصنف : "+ row.getCell(1).getStringCellValue());
                        ps.setString(1, row.getCell(0).getStringCellValue());
                        ps.setString(2, row.getCell(1).getStringCellValue());
                        ps.setDouble(3, row.getCell(2).getNumericCellValue());
                        ps.setDouble(4, row.getCell(3).getNumericCellValue());
                        ps.setDouble(5, row.getCell(4).getNumericCellValue());
                        ps.execute();

                // ADDING PRODUCT MOVEMENT TO ITS TABLE
                        Product product = new Product();
                        product.setProductCode(row.getCell(0).getStringCellValue());
                        product.setProductName(row.getCell(1).getStringCellValue());
                        product.setProductQuantity(row.getCell(2).getNumericCellValue());
                        product.setProductCost(row.getCell(3).getNumericCellValue());
                        product.setProductPrice(row.getCell(4).getNumericCellValue());

                        addProductMovement(product);

                //updating the progress bar
                        updateProgress(i, sheet.getLastRowNum());
                        updateMessage("جاري اضافة : " + row.getCell(1).getStringCellValue());
                        Thread.sleep(10);

                    }

                    updateMessage("تم إضافة جميع الاصناف بنجاح");
                    System.out.println("Products Added");
                    AlertMaker.showInformationAlert("تم اضافة جميع الاصناف");
//                lbAddMultiProducts.setText("تم إضافة جميع الاصناف بنجاح");

                    return null;
                }

            };

            task.messageProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    lbAddMultiProducts.setText(newValue);
                }

            });

            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } catch (Exception ex) {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }

    }

        //progressBar.setVisible(true);
    @FXML
    void addSingleProduct(ActionEvent event) {
        Product product = new Product(txtProductCode.getText(), txtProductName.getText(), Double.valueOf(txtProductQuantity.getText()), Double.valueOf(txtProducyCost.getText()), Double.valueOf(txtProductPrice.getText())
        );

        if (txtProductCode.getText() == "" || txtProductName.getText() == "" || txtProductPrice.getText() == "" || txtProductQuantity.getText() == "" || txtProducyCost.getText() == "") {
            AlertMaker.showErrorAlert("الرجاء ادخال البيانتا في جميع الحقول");
        } else {

            DatabaseHandler.addProduct(product);
            txtProductCode.clear();
            txtProductName.clear();
            txtProductQuantity.clear();
            txtProducyCost.clear();
            txtProductQuantity.clear();
            txtProductPrice.clear();

            // adding productMovement
            addProductMovement(product);
        }

    }

    public void updateProductValues(Product product) {
        btnAddMultiProducts.setDisable(true);

        txtProductCode.setText(product.getProductCode());
        txtProductName.setText(product.getProductName());
        txtProductPrice.setText(String.valueOf(product.getProductPrice()));
        txtProductQuantity.setText(String.valueOf(product.getProductQuantity()));
        txtProducyCost.setText(String.valueOf(product.getProductCost()));

        btnAddSingleProduct.setText("تعديل الصنف");

        btnAddSingleProduct.setOnAction(e -> {
            DatabaseHandler.updateProductValues(product.getProductName());
        });
    }

    private void addProductMovement(Product product) {
        ProductMovement productMovement = new ProductMovement();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        Date date = new Date();

        productMovement.setDate(simpleDateFormat.format(date));
        productMovement.setProductCode(product.getProductCode());
        productMovement.setProdcutName(product.getProductName());
        productMovement.setSalesInvoiceID(0);
        productMovement.setPurchaseInvoiceID(0);
        productMovement.setInQuantity(product.getProductQuantity());
        productMovement.setCurrentQuantity(DatabaseHandler.getProductQTY(product.getProductName()));
        productMovement.setDetails("رصيد إفتتاحي");
        DatabaseHandler.addProductMovement(productMovement);
    }

}
