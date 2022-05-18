package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AllAccountsAdminController implements Initializable {
    @FXML
    Label nameLabel = new Label();
    @FXML
    ScrollPane scroll=new ScrollPane();
    GridPane grid=new GridPane();
    @FXML
    Rectangle darken=new Rectangle();
    @FXML
    AnchorPane deleteAccountPicker=new AnchorPane();
    @FXML
    ComboBox pickAccountDelete=new ComboBox<>();

    public void prepareGrid() throws SQLException {
        Label label1=new Label("First name");
        label1.setStyle("-fx-font-weight: bold");
        grid.add(label1, 0, 0);
        Label label2=new Label("Last name");
        label2.setStyle("-fx-font-weight: bold");
        grid.add(label2, 1, 0);
        Label label3=new Label("E-mail address");
        label3.setStyle("-fx-font-weight: bold");
        grid.add(label3, 2, 0);
        Label label4=new Label("Account role");
        label4.setStyle("-fx-font-weight: bold");
        grid.add(label4, 3, 0);
        int i=1;
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        ResultSet rs=stmt.executeQuery("select First_Name, Last_Name, Email, UserType from users");
        while(rs.next()){
            grid.add(new Label(rs.getString(1)), 0, i);
            grid.add(new Label(rs.getString(2)), 1, i);
            grid.add(new Label(rs.getString(3)), 2, i);
            grid.add(new Label(rs.getString(4)), 3, i);
            i++;
        }
        int numberOfRows=grid.getRowCount();
        Label label12=new Label("First name");
        label12.setStyle("-fx-font-weight: bold");
        grid.add(label12, 0, numberOfRows);
        Label label13=new Label("Last name");
        label13.setStyle("-fx-font-weight: bold");
        grid.add(label13, 1, numberOfRows);
        Label label14=new Label("E-mail address");
        label14.setStyle("-fx-font-weight: bold");
        grid.add(label14, 2, numberOfRows);
        Label label15=new Label("Account role");
        label15.setStyle("-fx-font-weight: bold");
        grid.add(label15, 3, numberOfRows);
        scroll.setContent(grid);
    }
    public void preparePicker() throws SQLException {
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        ResultSet rs= stmt.executeQuery("select Email, UserType from users");
        while (rs.next()){
            pickAccountDelete.getItems().addAll(rs.getString(1)+": "+rs.getString(2));
        }
        rs.beforeFirst();
        rs.next();
        pickAccountDelete.setValue(rs.getString(1)+": "+rs.getString(2));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Scanner readEmail = new Scanner(new File("data"));
            String email = null;
            while (readEmail.hasNext()) {
                String line;
                if ((line = readEmail.nextLine()).contains("email:")) {
                    String[] parts = line.split(":");
                    email = parts[1];
                    break;
                }
            }
            readEmail.close();
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            ResultSet rs = stmt.executeQuery("select First_Name from users where Email='" + email + "'");
            rs.next();
            nameLabel.setText(rs.getString("First_Name") + "!");

            grid.setHgap(10);
            prepareGrid();

            preparePicker();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event){
        Alert question=new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        question.setHeaderText("Are you sure you want to log out of your account?");
        ButtonType answer=question.showAndWait().orElse(ButtonType.NO);
        if(ButtonType.YES.equals(answer)){
            question.close();
            Stage motherStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
            motherStage.close();
            Stage logIn=new Stage();
            FXMLLoader load=new FXMLLoader(MTDL_Project.class.getResource("LoginPage.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(load.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            logIn.setScene(scene);
            logIn.setTitle("Log in");
            logIn.show();
            File dataFile=new File("data");
            dataFile.delete();
        }
    }

    public void goHome(ActionEvent event) throws IOException {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene homeMod=new Scene(FXMLLoader.load(MTDL_Project.class.getResource("HomeAdmin.fxml")));
        stage.setScene(homeMod);
        stage.setTitle("[MODERATING] Home");
    }


    public void seeDeletePicker(ActionEvent event) {
        darken.setVisible(true);
        deleteAccountPicker.setVisible(true);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] Choose an account");
    }
    public void hideDeletePicker(ActionEvent event){
        deleteAccountPicker.setVisible(false);
        darken.setVisible(false);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] All accounts");
    }

    public void seeAddScreen(ActionEvent event) throws IOException {
        Scene addAcct =new Scene(FXMLLoader.load(MTDL_Project.class.getResource("AddSomeoneAdmin.fxml")));
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addAcct);
        stage.setTitle("[ADMINISTRATOR] Create a new account");
    }

    public void deleteAccount(ActionEvent event) throws SQLException {
        Alert question=new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        question.setHeaderText("Are you sure you want to delete this account? All of this account's data will be lost forever!");
        ButtonType answer=question.showAndWait().orElse(ButtonType.NO);
        if(ButtonType.YES.equals(answer)){
            question.close();
            String[] parts=pickAccountDelete.getValue().toString().split(": ");
            String emailToBeDeleted=parts[0];
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            stmt.executeUpdate("delete from users where Email='"+emailToBeDeleted+"'");
            Alert success=new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Successfully deleted user with the e-mail address "+emailToBeDeleted+" from the database!");
            success.showAndWait();
            deleteAccountPicker.setVisible(false);
            darken.setVisible(false);
            Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("[MODERATING] All accounts");
            grid.getChildren().clear();
            prepareGrid();
        }
    }
}