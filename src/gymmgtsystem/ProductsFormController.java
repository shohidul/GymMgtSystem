/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymmgtsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import util.DB;

/**
 * FXML Controller class
 *
 * @author Mohammad Arefin
 */
public class ProductsFormController implements Initializable {

    Connection con = null;

    @FXML
    private StackPane memberStack;
    @FXML
    private AnchorPane formroot;
    @FXML
    private JFXTextField productName;
    @FXML
    private JFXTextField productCode;
    @FXML
    private JFXTextField productSize;
    @FXML
    private JFXTextField productQuantity;
    @FXML
    private JFXTextArea productDescription;
    @FXML
    private JFXTextField buyingPrice;
    @FXML
    private JFXTextField sellingPrice;
    @FXML
    private JFXDatePicker purchaseDate;
    @FXML
    private JFXTextField productType;
    @FXML
    private JFXButton addBtn;
    @FXML
    private Button allMembersBtn;
    @FXML
    private TabPane membersTabPane;
    @FXML
    private Tab addMemberTab;
    @FXML
    private JFXButton instructorBtn;
    @FXML
    private Tab membershipTab;

    @FXML
    private TableView productTable;

    @FXML
    private JFXTextField productSearch;
    @FXML
    private JFXButton getProductBtn;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private Button refreshBtn;
    
    @FXML
    private JFXTextField filterInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildData();
    }
    LocalDate date;
    String add = "add";
//    String update = "Update Product";
 
    @FXML
    private void addProduct(ActionEvent event) {
        if (add.equals("add")) {
           
String sqlDate = purchaseDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            try {

                con = DB.getConnection();
                String sql = "INSERT INTO product(product_name, product_type, description, product_size, buying_price, selling_price, quantity, product_code, purchase_date) VALUES(?,?,?,?,?,?,?,?,?) ";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, productName.getText());
                ps.setString(2, productType.getText());
                ps.setString(3, productDescription.getText());
                ps.setString(4, productSize.getText());
                ps.setString(5, buyingPrice.getText());
                ps.setString(6, sellingPrice.getText());
                ps.setString(7, productQuantity.getText());
                ps.setString(8, productCode.getText());

                ps.setString(9, sqlDate);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    clearForm();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else  {

            try {
String sqlDate = purchaseDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                con = DB.getConnection();
                String sql = "UPDATE product SET product_name = ?, product_type =?, description =?, product_size =?, buying_price =?, selling_price =?, quantity=?, product_code =?, purchase_date = ? where product_id=? ";
                PreparedStatement ps = con.prepareStatement(sql);
                
                ps.setString(1, productName.getText());
                ps.setString(2, productType.getText());
                ps.setString(3, productDescription.getText());
                ps.setString(4, productSize.getText());
                ps.setString(5, buyingPrice.getText());
                ps.setString(6, sellingPrice.getText());
                ps.setString(7, productQuantity.getText());
                ps.setString(8, productCode.getText());
                ps.setString(9, sqlDate);
                ps.setString(10, productSearch.getText());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    clearForm();
                    addBtn.setText("Add Product");
                    productSearch.setText("");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    
    @FXML
    private void searchProduct(ActionEvent event) {
                data = FXCollections.observableArrayList();
        try {
            String getSearchField = filterInput.getText();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from product where product_code = '"+getSearchField+"' ";   						//change table name
            //ResultSet
            con = DB.getConnection();
            ResultSet rs = con.createStatement().executeQuery(SQL);

            //* TABLE COLUMN ADDED DYNAMICALLY *
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                productTable.getColumns().addAll(col);			//change table name
                System.out.println("Column [" + i + "] ");
            }
            //* Data added to ObservableList *
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            productTable.setItems(data);			//change table name
            
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        
    }
    
    

    private void clearForm() {
        productName.setText("");
        productType.setText("");
        productCode.setText("");
        productDescription.setText("");
        productSize.setText("");
        productQuantity.setText("");
        buyingPrice.setText("");
        sellingPrice.setText("");
        purchaseDate.setValue(null);

    }

    @FXML
    private void allMembersBtn(MouseEvent event) {
    }
    
  

    @FXML
    private void instructorBtnAction(ActionEvent event) {
    }


    @FXML
    private void memberTabAction(MouseEvent event) {
    }
    String getProductId;

    @FXML
    private void getProduct(ActionEvent event) {
         try {
            con = DB.getConnection();
            String sql = "Select * from product where product_id=? OR product_name=?";
            PreparedStatement ps = con.prepareStatement(sql);
            getProductId = productSearch.getText();
            ps.setString(1, getProductId);
            ps.setString(2, getProductId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                productName.setText(rs.getString(2));
                productType.setText(rs.getString(3));
                productDescription.setText(rs.getString(4));
                productSize.setText(rs.getString(4));
               
                buyingPrice.setText(rs.getString(5));
                sellingPrice.setText(rs.getString(6));
                productQuantity.setText(rs.getString(7));
                productCode.setText(rs.getString(8));
                 java.sql.Date d = rs.getDate("purchase_date");
                purchaseDate.setValue(d.toLocalDate());
                
                addBtn.setText("Update Product");
                add = "update";
                
            } else {

            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void resetMemberFormBtnAction(MouseEvent event) {
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        System.out.println("enter");
        try {
           
            con = DB.getConnection();
            String sql = "DELETE FROM product where product_id= '"+productSearch.getText()+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    ObservableList<ObservableList> data;

    private void buildData() {
        data = FXCollections.observableArrayList();
        try {
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from product";   						//change table name
            //ResultSet
            con = DB.getConnection();
            ResultSet rs = con.createStatement().executeQuery(SQL);

            //* TABLE COLUMN ADDED DYNAMICALLY *
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                productTable.getColumns().addAll(col);			//change table name
                System.out.println("Column [" + i + "] ");
            }
            //* Data added to ObservableList *
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            productTable.setItems(data);			//change table name
            filterInput.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    @FXML
    private void refreshBtnAction(ActionEvent event) {
        productTable.getItems().clear();
        buildData();
    }

}
