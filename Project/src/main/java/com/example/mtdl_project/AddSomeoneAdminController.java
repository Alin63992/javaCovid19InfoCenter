package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddSomeoneAdminController implements Initializable{
    @FXML
    Label nameLabel=new Label();
    @FXML
    TextField firstNameField=new TextField();
    @FXML
    TextField lastNameField=new TextField();
    @FXML
    TextField emailField=new TextField();
    @FXML
    TextField passwordField=new TextField();
    @FXML
    MenuButton acctRole=new MenuButton();
    EmailValidator validator=EmailValidator.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            Scanner readData = new Scanner(new File("data"));
            String email = null;
            ResultSet rs;
            while (readData.hasNext()) {
                String line = readData.nextLine();
                String[] parts = line.split(":");
                email = parts[1];
            }
            readData.close();
            rs = stmt.executeQuery("select first_name from users where Email='" + email + "'");
            rs.next();
            nameLabel.setText(rs.getString(1));
        }
        catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
        for (MenuItem item: acctRole.getItems()){
            item.setOnAction(act-> acctRole.setText(item.getText()));
        }
    }

    public void addAccount(ActionEvent event) throws SQLException, IOException {
        boolean error=false;
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        if(firstNameField.getText().equals("")) {
            Alert wrong=new Alert(Alert.AlertType.ERROR);
            wrong.setHeaderText("Error in the first name field");
            wrong.setContentText("The account needs at least one of the names of the owner. Please write it and try again.");
            wrong.showAndWait();
            error=true;
        }
        if(emailField.getText().equals("") || !validator.isValid(emailField.getText())) {
            Alert wrong=new Alert(Alert.AlertType.ERROR);
            wrong.setHeaderText("Error in the e-mail address field");
            wrong.setContentText("The account needs an e-mail address. Please write it and try again.");
            wrong.showAndWait();
            error=true;
        }
        if(passwordField.getText().equals("")) {
            Alert wrong=new Alert(Alert.AlertType.ERROR);
            wrong.setHeaderText("Error in the password field");
            wrong.setContentText("The account needs a password. Please write it and try again.");
            wrong.showAndWait();
            error=true;
        }
        if(!error) {
            stmt.executeUpdate("insert into users(Email, Password, UserType, First_Name, Last_Name, Favorites) values ('"+emailField.getText()+"', '"+passwordField.getText()+"', '"+acctRole.getText()+"', '"+firstNameField.getText()+"', '"+lastNameField.getText()+"', '')");
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Successfully created account with the e-mail address " + emailField.getText() + "!");
            success.showAndWait();
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("AllUsersAdmin.fxml"))));
            stage.setTitle("[ADMINISTRATOR] All accounts");
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
        stage.setTitle("[ADMINISTRATOR] Home");
    }

    public void goBack(ActionEvent event) throws IOException {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene back =new Scene(FXMLLoader.load(MTDL_Project.class.getResource("AllUsersAdmin.fxml")));
        stage.setScene(back);
        stage.setTitle("[ADMINISTRATOR] All countries");
    }
}