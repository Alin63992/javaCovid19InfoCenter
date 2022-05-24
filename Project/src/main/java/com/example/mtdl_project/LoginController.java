package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import org.apache.commons.validator.routines.EmailValidator;

public class LoginController implements Initializable {
    @FXML
    TextField emailField = new TextField();
    @FXML
    TextField emailFieldReset = new TextField();
    @FXML
    PasswordField passwordField;
    @FXML
    TextField newPassword;
    @FXML
    TextField confirmNewPassword;
    @FXML
    AnchorPane loginAnchor;
    @FXML
    Button loginButton;
    EmailValidator validator = EmailValidator.getInstance();

    public void loadHomePage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            EmailValidator validator = EmailValidator.getInstance();
            ResultSet rs = stmt.executeQuery("select Password from users where Email='" + emailField.getText() + "'");
            if (!emailField.getText().equals("") && !passwordField.getText().equals("")) {
                if (rs.next()) {
                    if (passwordField.getText().equals(rs.getString(1))) {
                        BufferedWriter writeEmail = new BufferedWriter(new FileWriter("data"));
                        writeEmail.write("email:" + emailField.getText());
                        writeEmail.close();
                        stage.close();
                        Scene home = null;
                        Stage stageHome = new Stage();
                        rs = stmt.executeQuery("select UserType from users where Email='" + emailField.getText() + "'");
                        rs.next();
                        if (rs.getString(1).equals("User")) {
                            home = new Scene(FXMLLoader.load(MTDL_Project.class.getResource("Home.fxml")));
                            stageHome.setTitle("Home");
                        } else if (rs.getString(1).equals("Moderator")) {
                            home = new Scene(FXMLLoader.load(MTDL_Project.class.getResource("HomeModerator.fxml")));
                            stageHome.setTitle("[MODERATING] Home");
                        } else if (rs.getString(1).equals("Administrator")) {
                            home = new Scene(FXMLLoader.load(MTDL_Project.class.getResource("HomeAdmin.fxml")));
                            stageHome.setTitle("[ADMINISTRATOR] Home");
                        }
                        stageHome.setScene(home);
                        stageHome.setResizable(false);
                        stageHome.setOnCloseRequest(event1 -> {
                            File dataFile = new File("data");
                            dataFile.delete();
                        });
                        stageHome.show();
                    } else {
                        Alert wrong = new Alert(Alert.AlertType.ERROR);
                        wrong.setTitle("Oops");
                        wrong.setHeaderText("Oops! Wrong credientials.");
                        wrong.setContentText("It looks like your password is wrong. Check it and try logging in again!");
                        wrong.show();
                    }
                } else if (!validator.isValid(emailField.getText())) {
                    Alert wrong = new Alert(Alert.AlertType.ERROR);
                    wrong.setTitle("Oops");
                    wrong.setHeaderText("Wrong e-mail format!");
                    wrong.setContentText("The e-mail you entered is not valid. Please ensure that it has the correct format and try again.");
                    wrong.show();
                } else {
                    Alert wrong = new Alert(Alert.AlertType.ERROR);
                    wrong.setTitle("Oops");
                    wrong.setHeaderText("Oops! Nonexistent e-mail address.");
                    wrong.setContentText("We couldn't find this e-mail address. Please check it and try again!");
                    wrong.show();
                }
                stmt.close();
            } else {
                Alert wrong = new Alert(Alert.AlertType.ERROR);
                wrong.setTitle("E-mail address or password missing");
                wrong.setHeaderText("You didn't type in an e-mail address or password!");
                wrong.setContentText("Please type both of them and try logging in again.");
                wrong.show();
            }
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    public void resetPasswordButton(ActionEvent event) throws SQLException, IOException {
        Stage resetStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (newPassword.getText().length() != 0 && confirmNewPassword.getText().length() != 0 && emailFieldReset.getText().length() != 0) {
            if (validator.isValid(emailFieldReset.getText())) {
                if (newPassword.getText().equals(confirmNewPassword.getText())) {
                    Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
                    Statement stmt = data.createStatement();
                    ResultSet rs = stmt.executeQuery("select count(Email) from users where Email='" + emailFieldReset.getText() + "'");
                    rs.next();
                    if (rs.getInt(1) != 0) {
                        stmt.executeUpdate("update users set Password='" + newPassword.getText() + "' where Email='" + emailFieldReset.getText() + "'");
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setHeaderText("Password reset successfully!");
                        success.showAndWait();
                        resetStage.close();
                        Stage loginStage=new Stage();
                        loginStage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("LoginPage.fxml"))));
                        loginStage.setTitle("Log in");
                        loginStage.setResizable(false);
                        loginStage.show();
                    } else {
                        Alert wrong = new Alert(Alert.AlertType.ERROR);
                        wrong.setHeaderText("The e-mail address you entered does not exist in our database.");
                        wrong.setContentText("We can't seem to find an account linked to that e-mail address. Please check if you wrote it correctly, and if you did, ask a moderator to create an account for you with this address.");
                        wrong.showAndWait();
                    }
                } else {
                    Alert wrong = new Alert(Alert.AlertType.ERROR);
                    wrong.setHeaderText("Passwords don't match!");
                    wrong.setContentText("The new password and its confirmation do not match. Please type them again.");
                    wrong.showAndWait();
                }
            } else {
                Alert wrong = new Alert(Alert.AlertType.ERROR);
                wrong.setHeaderText("Wrong e-mail format");
                wrong.setContentText("The address you entered is not a correct e-mail format. Please check it and type it again.");
                wrong.showAndWait();
            }
        } else {
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            wrong.setHeaderText("You need to write an e-mail address and your new password in both password fields in order to reset it.");
            wrong.showAndWait();
        }
    }

    public void resetPassword(ActionEvent event) throws IOException {
        Stage loginStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        loginStage.close();
        Stage resetStage = new Stage();
        resetStage.setOnCloseRequest(a->System.exit(0));
        resetStage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("ResetPassword.fxml"))));
        resetStage.setTitle("Reset password");
        resetStage.setResizable(false);
        resetStage.show();
    }

    public void showLoginScreen(ActionEvent event) throws IOException {
        Stage resetStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        resetStage.close();
        Stage loginStage=new Stage();
        loginStage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("LoginPage.fxml"))));
        loginStage.setTitle("Log in");
        loginStage.setResizable(false);
        loginStage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAnchor.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }
}