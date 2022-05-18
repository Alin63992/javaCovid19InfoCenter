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
            if (validator.isValid(emailField.getText())) {
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
                } else if (emailField.getText().equals("")) {
                    Alert wrong = new Alert(Alert.AlertType.WARNING);
                    wrong.setTitle("Type an e-mail address");
                    wrong.setHeaderText("Like a lot of dishes just aren't good without salt or pepper, an account is no good without an e-mail address...");
                    wrong.setContentText("Please type in an e-mail address and try logging in again.");
                    wrong.show();
                } else {
                    Alert wrong = new Alert(Alert.AlertType.ERROR);
                    wrong.setTitle("Oops");
                    wrong.setHeaderText("Oops! Nonexistent e-mail address.");
                    wrong.setContentText("We couldn't find this e-mail address. Check it or <a>create a new account</a>!");
                    wrong.show();
                }
                stmt.close();
            } else {
                Alert wrong = new Alert(Alert.AlertType.ERROR);
                wrong.setTitle("Oops");
                wrong.setHeaderText("Wrong e-mail format!");
                wrong.setContentText("The e-mail you entered is not valid. Please ensure that it has the correct format and try again.");
                wrong.show();
            }
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    public void resetPasswordButton(ActionEvent event) {
        Stage resetStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (newPassword.getText().length() != 0 && confirmNewPassword.getText().length() != 0) {
            try {
                Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
                Statement stmt = data.createStatement();
                ResultSet rs = stmt.executeQuery("select count(Email) from users where Email='" + emailField.getText() + "'");
                rs.next();
                if (validator.isValid(emailField.getText())) {
                    if (rs.getInt(1) != 0) {
                        if (newPassword.getText().equals(confirmNewPassword.getText())) {
                            stmt.executeUpdate("update users set Password='" + newPassword.getText() + "' where Email='" + emailField.getText() + "'");
                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setHeaderText("Password reset successfully!");
                            success.showAndWait();
                            resetStage.close();
                        } else {
                            Alert wrong = new Alert(Alert.AlertType.ERROR);
                            wrong.setHeaderText("Passwords don't match!");
                            wrong.setContentText("The new password and its confirmation do not match. Please type them again.");
                            wrong.showAndWait();
                        }
                    } else {
                        Alert wrong = new Alert(Alert.AlertType.ERROR);
                        wrong.setHeaderText("The e-mail address you entered does not exist in our database.");
                        wrong.setContentText("We can't seem to find an account linked to that e-mail address. Please check if you wrote it correctly, and if you did, ask a moderator to create an account for you with this address.");
                        wrong.showAndWait();
                    }
                } else {
                    Alert wrong = new Alert(Alert.AlertType.ERROR);
                    wrong.setHeaderText("Wrong e-mail format");
                    wrong.setContentText("The address you entered is not a correct e-mail format. Please check it and type it again.");
                    wrong.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            wrong.setHeaderText("You need to write your new password in both password fields in order to reset it.");
            wrong.showAndWait();
        }
    }

    public void resetPassword() throws IOException {
        Stage resetStage = new Stage();
        resetStage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("ResetPassword.fxml"))));
        resetStage.setTitle("Reset password");
        resetStage.setResizable(false);
        resetStage.show();
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