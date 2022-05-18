package com.example.mtdl_project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CreateAccountController implements Initializable {
    @FXML
    TextField First_Name=new TextField();
    @FXML
    TextField Last_Name=new TextField();
    @FXML
    TextField Email=new TextField();
    @FXML
    TextField Password=new TextField();
    @FXML
    TextField confirmPassword=new TextField();
    @FXML
    MenuButton securityQuestion1=new MenuButton();
    @FXML
    MenuButton securityQuestion2=new MenuButton();
    @FXML
    MenuButton securityQuestion3=new MenuButton();
    @FXML
    TextField answer1=new TextField();
    @FXML
    TextField answer2=new TextField();
    @FXML
    TextField answer3=new TextField();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}