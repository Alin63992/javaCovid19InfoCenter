package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomeModController implements Initializable {
    @FXML
    Label nameLabel=new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt= data.createStatement();
            Scanner readEmail=new Scanner(new File("data"));
            String email=null;
            while(readEmail.hasNext()){
                String line;
                if((line=readEmail.nextLine()).contains("email:")){
                    String[] parts=line.split(":");
                    email=parts[1];
                    break;
                }
            }
            readEmail.close();
            ResultSet rs=stmt.executeQuery("select First_Name from users where Email='"+email+"'");
            rs.next();
            nameLabel.setText(rs.getString("First_Name")+"!");
            stmt.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void seeCountryData(ActionEvent event) throws IOException {
        FXMLLoader loadAllCountriesPage = new FXMLLoader(MTDL_Project.class.getResource("AllCountriesMod.fxml"));
        Scene allCountries =new Scene(loadAllCountriesPage.load());
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(allCountries);
        stage.setTitle("[MODERATING] All Countries");
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
}