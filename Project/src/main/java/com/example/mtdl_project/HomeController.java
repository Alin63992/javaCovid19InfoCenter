package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomeController implements Initializable {
    @FXML
    ComboBox<String> pickCountry=new ComboBox<>();
    @FXML
    ComboBox<String> pickCountryRestrictions=new ComboBox<>();
    @FXML
    Label nameLabel=new Label();
    @FXML
    AnchorPane countryPicker=new AnchorPane();
    @FXML
    AnchorPane countryPickerRestrictions=new AnchorPane();
    @FXML
    Rectangle darken;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt= data.createStatement();
            ResultSet rs = stmt.executeQuery("select Country_Name from covid_cases");
            while (rs.next()){
                pickCountry.getItems().addAll(rs.getString("Country_Name"));
                pickCountryRestrictions.getItems().addAll(rs.getString("Country_Name"));
            }
            rs.beforeFirst();
            rs.next();
            pickCountry.setValue(rs.getString("Country_Name"));
            pickCountryRestrictions.setValue(rs.getString("Country_Name"));
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
            rs=stmt.executeQuery("select First_Name from users where Email='"+email+"'");
            rs.next();
            nameLabel.setText(rs.getString("First_Name")+"!");
            stmt.close();
        } catch (SQLException | FileNotFoundException e) {
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
    public void seeStats(ActionEvent event){
        darken.setVisible(true);
        countryPicker.setVisible(true);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Choose a country");
    }
    public void hideStats(ActionEvent event){
        countryPicker.setVisible(false);
        darken.setVisible(false);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home");
    }
    public void seeStatsPicker(ActionEvent event) throws IOException{
        Scanner sc=new Scanner(new FileReader("data"));
        String fileContent=sc.nextLine();
        sc.close();
        fileContent=fileContent+"\ncountry:"+pickCountry.getValue();
        BufferedWriter writeCountry=new BufferedWriter(new FileWriter("data"));
        writeCountry.write(fileContent);
        writeCountry.close();
        FXMLLoader loadStatsPage = new FXMLLoader(MTDL_Project.class.getResource("CountryStats.fxml"));
        Scene countryStats=new Scene(loadStatsPage.load());
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(countryStats);
        stage.setTitle("Country Statistics");
    }
    public void seeRestrictions(ActionEvent event){
        darken.setVisible(true);
        countryPickerRestrictions.setVisible(true);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Choose a country");
    }
    public void hideRestrictions(ActionEvent event){
        countryPickerRestrictions.setVisible(false);
        darken.setVisible(false);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home");
    }
    public void seeRestrictionsPicker(ActionEvent event) throws IOException{
        Scanner sc=new Scanner(new FileReader("data"));
        String fileContent=sc.nextLine();
        sc.close();
        fileContent=fileContent+"\ncountry:"+pickCountryRestrictions.getValue();
        BufferedWriter writeCountry=new BufferedWriter(new FileWriter("data"));
        writeCountry.write(fileContent);
        writeCountry.close();
        FXMLLoader loadRestrictionsPage = new FXMLLoader(MTDL_Project.class.getResource("CountryRestrictions.fxml"));
        Scene countryRestrictions=new Scene(loadRestrictionsPage.load());
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(countryRestrictions);
        stage.setTitle("Country Restrictions");
    }
    public void seeEuropeStats(ActionEvent event) throws IOException {
        FXMLLoader loadEuropeStatsPage = new FXMLLoader(MTDL_Project.class.getResource("EuropeStats.fxml"));
        Scene europeStats=new Scene(loadEuropeStatsPage.load());
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(europeStats);
        stage.setTitle("Europe Statistics");
    }
    public void seeSuggestionsWizard(ActionEvent event) throws IOException {
        Scene travelSuggestionsWizard=new Scene(FXMLLoader.load(MTDL_Project.class.getResource("TravelSuggestionsWizard.fxml")));
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(travelSuggestionsWizard);
        stage.setTitle("Travel suggestions wizard");
    }
}