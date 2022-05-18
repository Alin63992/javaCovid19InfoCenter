package com.example.mtdl_project;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CountryRestrictionsController implements Initializable {
    @FXML
    Label nameLabel=new Label();
    @FXML
    Label countryLabel=new Label();
    @FXML
    Label pcr=new Label(), tracing=new Label(), quarantine=new Label(), mask=new Label(), vaccine=new Label(), curfew=new Label();
    @FXML
    AnchorPane incompleteInfoWarning;
    @FXML
    Rectangle rectanglePCR=new Rectangle(), rectangleApp=new Rectangle(), rectangleQuarantine=new Rectangle(), rectangleMask=new Rectangle(), rectangleVaccine=new Rectangle(), rectangleCurfew=new Rectangle();
    @FXML
    Label quarantineDetails=new Label();
    @FXML
    AnchorPane quarantineDetailsAnchor=new AnchorPane();
    @FXML
    Button addRemoveFavoritesButton=new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Scanner readData =new Scanner(new File("data"));
            String email=null;
            String country=null;
            while(readData.hasNext()){
                String line=readData.nextLine();
                if(line.contains("email:")){
                    String[] parts=line.split(":");
                    email=parts[1];
                }
                else if(line.contains("country:")){
                    String[] parts=line.split(":");
                    country=parts[1];
                }
            }
            readData.close();
            Connection data= DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt=data.createStatement();
            ResultSet rs = stmt.executeQuery("select First_Name from users where Email='"+email+"'");
            rs.next();
            nameLabel.setText(rs.getString("First_Name")+"!");
            countryLabel.setText(country);
            rs=stmt.executeQuery("select PCR_Test_Required, Mobile_Tracing_App, quarantine, mask, vaccine, curfew from covid_cases where Country_Name='"+country+"'");
            int i=1;
            rs.next();
            while(i<=6){
                if(rs.getString(i).equals("unknown")){
                    incompleteInfoWarning.setVisible(true);
                    break;
                }
                i++;
            }
            rs.beforeFirst();
            rs.next();
            if(rs.getString("PCR_Test_Required").equals("true")) {
                pcr.setText("✔");
                Color paint = new Color(0.1294, 1.0, 0.1294, 1.0);
                rectanglePCR.setFill(paint);
            }
            else if(rs.getString("PCR_Test_Required").equals("false")) {
                pcr.setText("❌");
                Color paint = new Color(1.0, 0.1294, 0.1294, 1.0);
                rectanglePCR.setFill(paint);
            }
            else {
                pcr.setText("-");
                pcr.setStyle("-fx-font-weight: bold");
                Color paint = new Color(0, 0, 0, 1.0);
                rectanglePCR.setFill(paint);
            }
            if(rs.getString("Mobile_Tracing_App").equals("true")) {
                tracing.setText("✔");
                Color paint = new Color(0.1294, 1.0, 0.1294, 1.0);
                rectangleApp.setFill(paint);
            }
            else if(rs.getString("Mobile_Tracing_App").equals("false")) {
                tracing.setText("❌");
                Color paint = new Color(1.0, 0.1294, 0.1294, 1.0);
                rectangleApp.setFill(paint);
            }
            else if (rs.getString("Mobile_Tracing_App").equals("Recommended")){
                tracing.setText(rs.getString("Mobile_Tracing_App"));
                Color paint = new Color(1.0, 0.942, 0.13, 1.0);
                rectangleApp.setFill(paint);
            }
            else {
                tracing.setText("-");
                tracing.setStyle("-fx-font-weight: bold");
                Color paint = new Color(0, 0, 0, 1.0);
                rectangleApp.setFill(paint);
            }
            if(rs.getString("quarantine").equals("true")) {
                quarantine.setText("✔");
                Color paint = new Color(0.1294, 1.0, 0.1294, 1.0);
                rectangleQuarantine.setFill(paint);
            }
            else if(rs.getString("quarantine").equals("false")) {
                quarantine.setText("❌");
                Color paint = new Color(1.0, 0.1294, 0.1294, 1.0);
                rectangleQuarantine.setFill(paint);
            }
            else if(!rs.getString("quarantine").equals("unknown")) {
                quarantine.setText("/");
                Color paint = new Color(1.0, 0.942, 0.13, 1.0);
                rectangleQuarantine.setFill(paint);
                String quarantineDeets=rs.getString("quarantine");
                rectangleQuarantine.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
                    if(show){
                        quarantineDetails.setText(quarantineDeets);
                        quarantineDetailsAnchor.setVisible(true);
                    }
                    else{
                        quarantineDetails.setText("");
                        quarantineDetailsAnchor.setVisible(false);
                    }
                });
                quarantineDetailsAnchor.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
                    if(show){
                        quarantineDetails.setText(quarantineDeets);
                        quarantineDetailsAnchor.setVisible(true);
                    }
                    else{
                        quarantineDetails.setText("");
                        quarantineDetailsAnchor.setVisible(false);
                    }
                });
            }
            else {
                quarantine.setText("-");
                quarantine.setStyle("-fx-font-weight: bold");
                Color paint = new Color(0, 0, 0, 1.0);
                rectangleQuarantine.setFill(paint);
            }
            if(rs.getString("mask").equals("true")) {
                mask.setText("✔");
                Color paint = new Color(0.1294, 1.0, 0.1294, 1.0);
                rectangleMask.setFill(paint);
            }
            else if(rs.getString("mask").equals("false")) {
                mask.setText("❌");
                Color paint = new Color(1.0, 0.1294, 0.1294, 1.0);
                rectangleMask.setFill(paint);
            }
            else {
                mask.setText("-");
                mask.setStyle("-fx-font-weight: bold");
                Color paint = new Color(0, 0, 0, 1.0);
                rectangleMask.setFill(paint);
            }
            if(rs.getString("vaccine").equals("true")) {
                vaccine.setText("✔");
                Color paint = new Color(0.1294, 1.0, 0.1294, 1.0);
                rectangleVaccine.setFill(paint);
            }
            else if(rs.getString("vaccine").equals("false")) {
                vaccine.setText("❌");
                Color paint = new Color(1.0, 0.1294, 0.1294, 1.0);
                rectangleVaccine.setFill(paint);
            }
            else {
                vaccine.setText("-");
                vaccine.setStyle("-fx-font-weight: bold");
                Color paint = new Color(0, 0, 0, 1.0);
                rectangleVaccine.setFill(paint);
            }
            if(rs.getString("curfew").equals("true")) {
                curfew.setText("✔");
                Color paint = new Color(0.1294, 1.0, 0.1294, 1.0);
                rectangleCurfew.setFill(paint);
            }
            else if(rs.getString("curfew").equals("false")) {
                curfew.setText("❌");
                Color paint = new Color(1.0, 0.1294, 0.1294, 1.0);
                rectangleCurfew.setFill(paint);
            }
            else {
                curfew.setText("-");
                curfew.setStyle("-fx-font-weight: bold");
                Color paint = new Color(0, 0, 0, 1.0);
                rectangleCurfew.setFill(paint);
            }
            rs=stmt.executeQuery("select Favorites from users where Email='"+email+"'");
            rs.next();
            String[] favoriteCountries=rs.getString(1).split(", ");
            for(String c: favoriteCountries){
                if(c.matches(countryLabel.getText())) {
                    addRemoveFavoritesButton.setText("Remove country from your favorites list");
                    break;
                }
            }
            stmt.close();
        }
        catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event){
        Stage motherStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert question=new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        question.setHeaderText("Are you sure you want to log out of your account?");
        ButtonType answer=question.showAndWait().orElse(ButtonType.NO);
        if(ButtonType.YES.equals(answer)){
            question.close();
            motherStage.close();
            Stage logIn=new Stage();
            FXMLLoader load=new FXMLLoader(MTDL_Project.class.getResource("LoginPage.fxml"));
            Scene scene= null;
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
    public void seeFavorites() throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("Favorites.fxml"))));
        stage.setTitle("Your favorite countries");
        stage.show();
    }
    public void goHome(ActionEvent event) throws IOException {
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("Home.fxml"))));
        stage.setTitle("Home");
    }
    public void addRemoveFavorites() throws FileNotFoundException, SQLException {
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
        Connection data=DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt=data.createStatement();
        ResultSet rs = stmt.executeQuery("select Favorites from users where Email='"+email+"'");
        if(addRemoveFavoritesButton.getText().contains("Remove ")){
            rs.next();
            ArrayList<String> favCountries = new ArrayList<>(Arrays.asList(rs.getString(1).split(", ")));
            favCountries.remove(countryLabel.getText());
            String favoriteCountries="";
            for(String c: favCountries){
                if(favCountries.indexOf(c)!=favCountries.size()-1){
                    favoriteCountries.concat(c+", ");
                }
                else favoriteCountries.concat(c);
            }
            stmt.executeUpdate("update users set Favorites='"+favoriteCountries+"' where Email='"+email+"'");
            addRemoveFavoritesButton.setText("Add country to your favorites list");
        }
        else if(addRemoveFavoritesButton.getText().contains("Add ")) {
            rs.next();
            String favoriteCountries = rs.getString(1);
            if(favoriteCountries.length()!=0) favoriteCountries=favoriteCountries + ", " + countryLabel.getText();
            else favoriteCountries=favoriteCountries + countryLabel.getText();
            stmt.executeUpdate("update users set Favorites='" + favoriteCountries + "' where Email='" + email + "'");
            addRemoveFavoritesButton.setText("Remove country from your favorites list");
        }
    }
}