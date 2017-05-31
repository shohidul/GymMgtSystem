
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymmgtsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import util.DB;

/**
 * FXML Controller class
 *
 * @author Hridoy
 */
public class MembersFormController implements Initializable {

    @FXML
    public AnchorPane formroot;
    @FXML
    private ImageView profilePhoto;
    @FXML
    private StackPane memberStack;
    @FXML
    private Button allMembersBtn;

    int countAllMembersBtn;
    AnchorPane allMembersTable;
    @FXML
    private JFXButton saveToDatabaseBtn;
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXTextField lname;
    @FXML
    private StackPane membershipStack;
    @FXML
    private JFXToggleButton membershipViewChangeBtn;
    String viewChange = "form";
    int countMembershipViewChangeBtn = 0;
    @FXML
    private AnchorPane formAnchorPane;
    private AnchorPane membershipTable;
    @FXML
    private JFXTextField height;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXTextField weight;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private JFXComboBox<String> bodyshapCombo;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField cellNo;
    @FXML
    private JFXTextField occupaton;
    @FXML
    private JFXTextArea address;
    @FXML
    private JFXTextField id;
    String genderSelected;
    @FXML
    private JFXComboBox<String> memberTypeCombo;
    private File file;
    private BufferedImage bufferedImage;
    private WritableImage image;
    private String imagePath;
    FileInputStream proPic;
    @FXML
    private Tab addMemberTab;
    @FXML
    private Tab membershipTab;
    @FXML
    private TabPane membersTabPane;
    private int tabIndex = 0;
    @FXML
    private Button resetMemberFormBtn;
    @FXML
    private Button deleteMemberFormBtn;
    @FXML
    private JFXTextField idMembership;
    @FXML
    private JFXTextField idBill;
    @FXML
    private JFXButton getBtn;
    private String saveBtnCondition = "insert";
    @FXML
    private JFXButton instructorBtn;
    @FXML
    private JFXButton addPhotoBtn;
    @FXML
    private ToggleGroup gender;
    @FXML
    private Tab billingTab;
    @FXML
    private JFXButton saveBillBtn;
    @FXML
    private JFXTextField billID;
    @FXML
    private JFXButton billSearchBtn;
    @FXML
    private Button resetBillBtn;
    @FXML
    private Button deleteBillBtn;
    @FXML
    private Label fullNameLabel;
    @FXML
    private JFXComboBox<String> shift;
    @FXML
    private JFXComboBox<String> packageCombo;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private JFXComboBox<String> instructorCombo;
    @FXML
    private Label memberTypeLabel;
    @FXML
    private Label memberSinceLabel;
    @FXML
    private Button addShiftBtn;
    @FXML
    private Button addPackageBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        con = DB.getConnection();

        glowMembersBtn();
        ObservableList<String> shapeList = FXCollections.observableArrayList("Lean", "Fat", "Skinny");
        bodyshapCombo.setItems(shapeList);
//        bodyshapCombo.getSelectionModel().selectFirst();

        ObservableList<String> memberTypeList = FXCollections.observableArrayList("Member", "Instructor");
        memberTypeCombo.setItems(memberTypeList);
//        memberTypeCombo.getSelectionModel().selectFirst();

        loadShift();
        loadPackage();
        loadInstructor();

    }

    private void closeFormAction(ActionEvent event) {
        formroot.toBack();
//      System.out.println("Form to back");
    }

    @FXML
    private void allMembersBtn(MouseEvent e) {

        if (e.getClickCount() >= 1) {
            countAllMembersBtn += 1;
        } else {
        }

        if (countAllMembersBtn <= 1) {

            try {
                allMembersTable = FXMLLoader.load(getClass().getResource("AllMembersTable.fxml"));
                memberStack.getChildren().add(allMembersTable);

            } catch (IOException ex) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("allMembersTable added");
        } else {
            allMembersTable.toFront();
            System.out.println("allMembersTable to Front");
        }

    }

    private void glowMembersBtn() {
        allMembersBtn.setOnMouseEntered((event) -> {
            allMembersBtn.setEffect(new Glow(.5));
        });
        allMembersBtn.setOnMouseExited((event) -> {
            allMembersBtn.setEffect(null);
        });
    }

    @FXML
    private void saveToDatabaseBtnAction(MouseEvent event) {
        if (saveBtnCondition.equals("insert")) {
            if (tabIndex == 0) {
                if (maleRadio.isSelected()) {
                    genderSelected = "Male";
                } else {
                    genderSelected = "Female";
                }
                String sqlDate = dob.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                try {

                    String sql = "INSERT into member(member_fname, member_lname, gender, dob, email, cell, address, occupation, height, weight, body_shape, member_type, member_image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, fname.getText());
                    ps.setString(2, lname.getText());
                    ps.setString(3, genderSelected);
                    ps.setString(4, sqlDate);
                    ps.setString(5, email.getText());
                    ps.setString(6, cellNo.getText());
                    ps.setString(7, address.getText());
                    ps.setString(8, occupaton.getText());
                    ps.setString(9, height.getText());
                    ps.setString(10, weight.getText());
                    ps.setString(11, bodyshapCombo.getSelectionModel().getSelectedItem().toString());
                    ps.setString(12, memberTypeCombo.getSelectionModel().getSelectedItem().toString());
                    ps.setBinaryStream(13, proPic, proPic.available());
                    ps.executeUpdate();

                    resetMemberDetails();

                    ps = con.prepareStatement("Select member_id from member order by member_id desc LIMIT 1");
                    rs = ps.executeQuery();
                    rs.next();
                    id.setText(rs.getString(1));
                    idMembership.setText(rs.getString(1));
                    idBill.setText(rs.getString(1));

                } catch (Exception e) {
                    System.out.println("save error");
                    e.printStackTrace();
                }
            } else if (tabIndex == 1) {
                try {

                    String shiftName = shift.getSelectionModel().getSelectedItem().toString();
                    String sql1 = "SELECT shift_id FROM shift where shift_name=?";
                    ps = con.prepareStatement(sql1);
                    ps.setString(1, shiftName);
                    rs = ps.executeQuery();
                    String shiftId=null;
                    while (rs.next()) {
                        shiftId = rs.getString(1);
                        System.out.println(shiftId);
                    }
                    
                    String packageName = packageCombo.getSelectionModel().getSelectedItem().toString();
                    String sql2 = "SELECT package_id FROM package where package_name=?";
                    ps = con.prepareStatement(sql2);
                    ps.setString(1, packageName);
                    rs = ps.executeQuery();
                    String packageId=null;
                    while (rs.next()) {
                        packageId = rs.getString(1);
                        System.out.println(packageId);
                    }
                        

                    String startDateValue = startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String endDateValue = endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    String sql = "INSERT into membership(member_id, shift_id, instructor_name, package_id, start_date, end_date) VALUES(?,?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, idMembership.getText());
                    ps.setString(2, shiftId);
                    ps.setString(3, instructorCombo.getSelectionModel().getSelectedItem().toString());
                    ps.setString(4, packageId);
                    ps.setString(5, startDateValue);
                    ps.setString(6, endDateValue);

                    ps.executeUpdate();

                    resetMemberDetails();
//                    System.out.println("Membership saved");
                } catch (Exception e) {
                    System.out.println("membership error");
                    e.printStackTrace();
                }

            } else {
                System.out.println("Bill saved");
            }

        } else {
            if (tabIndex == 0) {
                System.out.println("Membership updated");
            } else if (tabIndex == 1) {
                System.out.println("Membership updated");
            } else {
                System.out.println("Bill updated");
            }

        }

    }

    @FXML
    private void membershipViewChangeBtnAction(MouseEvent e) {
        if (viewChange.equals("form")) {
            membershipViewChangeBtn.setText("Table View");
            viewChange = "table";

            if (e.getClickCount() >= 1) {
                countMembershipViewChangeBtn += 1;
            } else {
            }

            if (countMembershipViewChangeBtn <= 1) {

                try {
                    membershipTable = FXMLLoader.load(getClass().getResource("MembershipTable.fxml"));
                    membershipStack.getChildren().add(membershipTable);

                } catch (IOException ex) {
                    Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("membershipTable added");
            } else {
                membershipTable.toFront();
                System.out.println("membershipTable to Front");
            }
        } else {
            membershipViewChangeBtn.setText("Form View");
            viewChange = "form";
            formAnchorPane.toFront();
            System.out.println("tilePane.toFront");
        }
    }

    private void resetMemberDetails() {
        fname.clear();
        lname.clear();
        dob.setValue(null);
        email.clear();
        cellNo.clear();
        address.clear();
        occupaton.clear();
        height.clear();
        weight.clear();
        bodyshapCombo.getSelectionModel().clearSelection(); 
        memberTypeCombo.getSelectionModel().clearSelection(); 
        profilePhoto.setImage(null);
        id.clear();
        saveBtnCondition = "insert";
        buttonChange();

        idMembership.clear();
        shift.getSelectionModel().clearSelection(); 
        packageCombo.getSelectionModel().clearSelection(); 
        instructorCombo.getSelectionModel().clearSelection(); 
        startDate.setValue(null);
        endDate.setValue(null);
    }

    @FXML
    private void addPhotoBtnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG (Joint Photographic Group)", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG (Portable Network Graphics)", "*.png")
        );

        fileChooser.setTitle("Choise a Image File");

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file);
            bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            profilePhoto.setImage(image);
            imagePath = file.getAbsolutePath();
            proPic = new FileInputStream(imagePath);
            System.out.println(imagePath);
        }

    }

    @FXML
    private void memberTabAction(MouseEvent event) {
        buttonChange();

    }

    @FXML
    private void resetMemberFormBtnAction(MouseEvent event) {
        resetMemberDetails();
    }

    @FXML
    private void deleteMemberFormBtnAction(ActionEvent event) {
    }

    @FXML
    private void getBtnAction(ActionEvent event) throws SQLException {
        ps = con.prepareStatement("Select * from member where member_id=?");
        ps.setString(1, id.getText());
        rs = ps.executeQuery();

        if (rs == null || !rs.first()) {
            resetMemberDetails();
            ps = con.prepareStatement("Select member_id from member order by member_id desc LIMIT 1");
            rs = ps.executeQuery();
            rs.next();
            int idInt = Integer.parseInt(rs.getString(1));
            String idIncrement = String.valueOf(idInt + 1);

            id.setText(idIncrement);
            idMembership.setText(idIncrement);
            idBill.setText(idIncrement);
            saveBtnCondition = "insert";
        } else {
            fname.setText(rs.getString("member_fname"));
            lname.setText(rs.getString("member_lname"));
            java.sql.Date d = rs.getDate("dob");
            dob.setValue(d.toLocalDate());
            email.setText(rs.getString("email"));
            cellNo.setText(rs.getString("cell"));
            address.setText(rs.getString("address"));
            occupaton.setText(rs.getString("occupation"));
            height.setText(rs.getString("height"));
            weight.setText(rs.getString("weight"));
            bodyshapCombo.setValue(rs.getString("body_shape"));
            memberTypeCombo.setValue(rs.getString("member_type"));
            String genderValue = rs.getString("gender");
            if (genderValue.equals("Male")) {
                maleRadio.setSelected(true);
            } else {
                femaleRadio.setSelected(true);
            }

            Image convertToJavaFXImage = convertToJavaFXImage(rs.getBytes("member_image"), 180, 180);

            profilePhoto.setImage(convertToJavaFXImage);
            saveBtnCondition = "update";
            buttonChange();
        }
    }

    private static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    private void buttonChange() {
        SingleSelectionModel<Tab> selectionModel = membersTabPane.getSelectionModel();
        tabIndex = selectionModel.getSelectedIndex();
        if (saveBtnCondition.equals("insert")) {
            if (tabIndex == 0) {
                saveToDatabaseBtn.setVisible(true);
                saveToDatabaseBtn.setText("Create New Member");
                getBtn.setVisible(true);
                id.setVisible(true);
                resetMemberFormBtn.setVisible(true);
                deleteMemberFormBtn.setVisible(true);
            } else if (tabIndex == 1) {
                saveToDatabaseBtn.setVisible(true);
                saveToDatabaseBtn.setText("Add Membership");
                getBtn.setVisible(true);
                id.setVisible(true);
                resetMemberFormBtn.setVisible(true);
                deleteMemberFormBtn.setVisible(true);
            } else {
                saveToDatabaseBtn.setVisible(false);
                getBtn.setVisible(false);
                id.setVisible(false);
                resetMemberFormBtn.setVisible(false);
                deleteMemberFormBtn.setVisible(false);
            }

        } else {
            if (tabIndex == 0) {
                saveToDatabaseBtn.setText("Update Member");
            } else if (tabIndex == 1) {
                saveToDatabaseBtn.setText("Update Membership");
            } else {
                saveToDatabaseBtn.setText("Update Bill");
            }
        }
    }

    @FXML
    private void instructorBtnAction(ActionEvent event) throws IOException {
        nextStage(GymMgtSystem.InsInfo, "", true);
    }

    @FXML
    private void memberTypeComboAction(ActionEvent event) {
        String value = memberTypeCombo.getSelectionModel().getSelectedItem().toString();
        if (value.equals("Instructor")) {
            instructorBtn.setVisible(true);
        } else {
            instructorBtn.setVisible(false);
        }
    }

    private void nextStage(String fxml, String title, boolean resizable) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(resizable);
        stage.show();
        
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2); 
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);  
    }

    @FXML
    private void saveBillBtnAction(ActionEvent event) {
    }

    @FXML
    private void resetBillBtn(ActionEvent event) {
    }

    @FXML
    private void deleteBillBtnAction(ActionEvent event) {
    }

    private void loadShift() {

        try {
            String sql = "SELECT shift_name FROM shift";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ObservableList<String> options = FXCollections.observableArrayList(rs.getString("shift_name"));
                shift.getItems().addAll(options);
            }
        } catch (Exception e) {
        }
    }

    private void loadPackage() {
        try {
            String sql = "SELECT package_name FROM package";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ObservableList<String> options = FXCollections.observableArrayList(rs.getString("package_name"));
                packageCombo.getItems().addAll(options);
            }
        } catch (Exception e) {
        }

    }

    private void loadInstructor() {
        try {
            String sql = "SELECT member_fname, member_lname FROM member where member_type=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Instructor");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("member_fname") + " " + rs.getString("member_lname");
                ObservableList<String> options = FXCollections.observableArrayList(name);
                instructorCombo.getItems().addAll(options);
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void addShiftBtnAction(ActionEvent event) throws IOException {
        nextStage(GymMgtSystem.ShiftForm, "", true);
    }

    @FXML
    private void addPackageBtnAction(ActionEvent event) throws IOException {
            nextStage(GymMgtSystem.PackagesForm, "", true);
    }

}
