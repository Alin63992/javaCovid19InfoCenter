package com.example.mtdl_project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class TravelSuggestionsController implements Initializable{
    @FXML
    TextField confirmed=new TextField();
    @FXML
    TextField active=new TextField();
    @FXML
    TextField cured=new TextField();
    @FXML
    TextField deaths=new TextField();
    @FXML
    MenuButton pcr=new MenuButton();
    @FXML
    MenuButton app=new MenuButton();
    @FXML
    MenuButton quarantine=new MenuButton();
    @FXML
    MenuButton mask=new MenuButton();
    @FXML
    MenuButton vaccine=new MenuButton();
    @FXML
    MenuButton curfew=new MenuButton();
    @FXML
    Label nameLabel=new Label();
    @FXML
    Label wishesLabel=new Label();
    @FXML
    ScrollPane wishesScroll=new ScrollPane();
    @FXML
    ScrollPane allWishesScroll=new ScrollPane();
    @FXML
    ScrollPane someWishesScroll=new ScrollPane();
    boolean error=false;
    boolean allWishes=false;
    boolean someWishes=false;
    Hashtable<String, Integer> countries = new Hashtable<>();
    int wishCount=0;

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
    public void getSuggestions(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        int wishCounter=0;
        Class.forName("com.mysql.jdbc.Driver");
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        ResultSet rs=stmt.executeQuery("select * from covid_cases");
        if(!confirmed.getText().isEmpty()) {
            wishCount++;
        }
        if(!active.getText().isEmpty()) {
            wishCount++;
        }
        if(!cured.getText().isEmpty()) {
            wishCount++;
        }
        if(!deaths.getText().isEmpty()){
            wishCount++;
        }
        if(!pcr.getText().isEmpty()) {
            wishCount++;
        }
        if(!app.getText().isEmpty()) {
            wishCount++;
        }
        if(!quarantine.getText().isEmpty()) {
            wishCount++;
        }
        if(!mask.getText().isEmpty()) {
            wishCount++;
        }
        if(!vaccine.getText().isEmpty()) {
            wishCount++;
        }
        if(!curfew.getText().isEmpty()) {
            wishCount++;
        }
        if(wishCount==0){
            Alert wrong=new Alert(Alert.AlertType.ERROR);
            wrong.setHeaderText("We need some details to be able to do our magic");
            wrong.setContentText("Please tell us any of the criteria below to be able to suggest you a safe travel destination.");
            wrong.showAndWait();
            error=true;
        }
        while (rs.next() && !error){
            if(!confirmed.getText().isEmpty()) {
                int number=0;
                try{
                    number=Integer.parseInt(confirmed.getText());
                }
                catch (NumberFormatException e){
                    Alert wrong=new Alert(Alert.AlertType.ERROR);
                    wrong.setHeaderText("Error in the total confirmed cases field");
                    wrong.setContentText("What you typed in that field is not a number. Please only type numbers in all fields.");
                    wrong.showAndWait();
                    error=true;
                }
                if(!rs.getString(3).equals("unknown") && Integer.parseInt(rs.getString(3))<=number){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                wishesLabel.setText(wishesLabel.getText().concat("Total confirmed cases <= "+confirmed.getText()));
            }
            if(!active.getText().isEmpty()) {
                int number=0;
                try{
                    number=Integer.parseInt(active.getText());
                }
                catch (NumberFormatException e){
                    Alert wrong=new Alert(Alert.AlertType.ERROR);
                    wrong.setHeaderText("Error in the active cases field");
                    wrong.setContentText("What you typed in that field is not a number. Please only type numbers in all fields.");
                    wrong.showAndWait();
                    error=true;
                }
                if(!rs.getString(4).equals("unknown") && Integer.parseInt(rs.getString(4))<=number){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Active cases <= "+active.getText()));
            }
            if(!cured.getText().isEmpty()) {
                int number=0;
                try{
                    number=Integer.parseInt(cured.getText());
                }
                catch (NumberFormatException e){
                    Alert wrong=new Alert(Alert.AlertType.ERROR);
                    wrong.setHeaderText("Error in the cured cases field");
                    wrong.setContentText("What you typed in that field is not a number. Please only type numbers in all fields.");
                    wrong.showAndWait();
                    error=true;
                }
                if(!rs.getString(5).equals("unknown") && Integer.parseInt(rs.getString(5))>=number){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Cured cases <= "+cured.getText()));
            }
            if(!deaths.getText().isEmpty()){
                int number=0;
                try{
                    number=Integer.parseInt(deaths.getText());
                }
                catch (NumberFormatException e){
                    Alert wrong=new Alert(Alert.AlertType.ERROR);
                    wrong.setHeaderText("Error in the total deaths field");
                    wrong.setContentText("What you typed in that field is not a number. Please only type numbers in all fields.");
                    wrong.showAndWait();
                    error=true;
                }
                if(!rs.getString(6).equals("unknown") && Integer.parseInt(rs.getString(6))<=number){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Total deaths <= "+deaths.getText()));
            }
            if(!pcr.getText().isEmpty()) {
                if(pcr.getText().equals("Required") && rs.getString(7).equals("true")) {
                    if (countries.containsKey(rs.getString(2)))
                        wishCounter = countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter + 1);
                }
                else if(pcr.getText().equals("Not required") && rs.getString(7).equals("false")){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("PCR Testing "+pcr.getText()));
            }
            if(!app.getText().isEmpty()) {
                if(app.getText().equals("Required") && rs.getString(8).equals("true")) {
                    if (countries.containsKey(rs.getString(2)))
                        wishCounter = countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter + 1);
                }
                else if(app.getText().equals("Not required") && rs.getString(8).equals("false")){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Mobile Tracing App Usage "+app.getText()));
            }
            if(!quarantine.getText().isEmpty()) {
                if(quarantine.getText().equals("Required") && rs.getString(9).equals("true")) {
                    if (countries.containsKey(rs.getString(2)))
                        wishCounter = countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter + 1);
                }
                else if(quarantine.getText().equals("Not required") && rs.getString(9).equals("false")){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Quarantine "+quarantine.getText()));
            }
            if(!mask.getText().isEmpty()) {
                if(mask.getText().equals("Required") && rs.getString(10).equals("true")) {
                    if (countries.containsKey(rs.getString(2)))
                        wishCounter = countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter + 1);
                }
                else if(mask.getText().equals("Not required") && rs.getString(10).equals("false")){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Wearing a Mask "+mask.getText()));
            }
            if(!vaccine.getText().isEmpty()) {
                if(vaccine.getText().equals("Required") && rs.getString(11).equals("true")) {
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                else if(vaccine.getText().equals("Not required") && rs.getString(11).equals("false")){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Vaccination "+vaccine.getText()));
            }
            if(!curfew.getText().isEmpty()) {
                if(curfew.getText().equals("In order") && rs.getString(12).equals("true")) {
                    if(countries.contains(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                else if(curfew.getText().equals("Not in order") && rs.getString(12).equals("false")){
                    if(countries.containsKey(rs.getString(2)))
                        wishCounter=countries.get(rs.getString(2));
                    countries.put(rs.getString(2), wishCounter+1);
                }
                if(wishesLabel.getText().length()>13)
                    wishesLabel.setText(wishesLabel.getText().concat(" | "));
                wishesLabel.setText(wishesLabel.getText().concat("Overnight Curfew "+pcr.getText()));
            }
        }
        if (!error) {
            if(countries.size()!=0) {
                for (int i: countries.values()){
                    if(i==wishCount) allWishes=true;
                    else someWishes=true;
                }
                if(allWishes && !someWishes){
                    FXMLLoader loadAllWishes=new FXMLLoader(MTDL_Project.class.getResource("TravelSuggestionsAllCriteria.fxml"));
                    Scene scene=new Scene(loadAllWishes.load());
                    Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Travel Suggestions");
                    stage.show();
                }
                else if(!allWishes && someWishes){
                    Scene scene=new Scene(FXMLLoader.load(MTDL_Project.class.getResource("TravelSuggestionsSomeCriteria.fxml")));
                    Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Travel Suggestions");
                    stage.show();
                }
                else{
                    FXMLLoader loadBoth=new FXMLLoader(MTDL_Project.class.getResource("TravelSuggestionsBoth.fxml"));
                    Scene scene=new Scene(loadBoth.load());
                    Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Travel Suggestions");
                    stage.show();
                }
            }
            else {
                Alert wrong=new Alert(Alert.AlertType.ERROR);
                wrong.setHeaderText("We couldn't find any countries that match any of your criteria");
                wrong.setContentText("Try changing some of your criteria and see if there's a different result.");
                wrong.showAndWait();
            }
        }
    }

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

            if(allWishes && !someWishes){
                GridPane grid=new GridPane();
                grid.setHgap(10);
                Label label1=new Label("Country Name");
                label1.setStyle("-fx-font-weight: bold");
                grid.add(label1, 0, 0);
                Label label2=new Label("Total Confirmed Cases");
                label2.setStyle("-fx-font-weight: bold");
                grid.add(label2, 1, 0);
                Label label3=new Label("Active Cases");
                label3.setStyle("-fx-font-weight: bold");
                grid.add(label3, 2, 0);
                Label label4=new Label("Cured Cases");
                label4.setStyle("-fx-font-weight: bold");
                grid.add(label4, 3, 0);
                Label label5=new Label("Total Deaths");
                label5.setStyle("-fx-font-weight: bold");
                grid.add(label5, 4, 0);
                Label label6=new Label("PCR Test Required for entry");
                label6.setStyle("-fx-font-weight: bold");
                grid.add(label6, 5, 0);
                Label label7=new Label("Mobile Tracing App Mandatory");
                label7.setStyle("-fx-font-weight: bold");
                grid.add(label7, 6, 0);
                Label label8=new Label("Quarantine required upon entry");
                label8.setStyle("-fx-font-weight: bold");
                grid.add(label8, 7, 0);
                Label label9=new Label("Wearing a mask outdoors required");
                label9.setStyle("-fx-font-weight: bold");
                grid.add(label9, 8, 0);
                Label label10=new Label("Vaccination Required for entry");
                label10.setStyle("-fx-font-weight: bold");
                grid.add(label10, 9, 0);
                Label label11=new Label("Overnight Curfew in Order");
                label11.setStyle("-fx-font-weight: bold");
                grid.add(label11, 10, 0);
                int i=0;
                for (String country : countries.keySet()) {
                    rs = stmt.executeQuery("select * from covid_cases where Country_Name='" + country + "'");
                    rs.next();
                    grid.add(new Label(country), 0, 0);
                    grid.add(new Label(rs.getString(3)), 1, i);
                    grid.add(new Label(rs.getString(4)), 2, i);
                    grid.add(new Label(rs.getString(5)), 3, i);
                    grid.add(new Label(rs.getString(6)), 4, i);
                    if (rs.getString(7).equals("true"))
                        grid.add(new Label("✔"), 5, i);
                    else if (rs.getString(7).equals("false"))
                        grid.add(new Label("❌"), 5, i);
                    else grid.add(new Label(rs.getString(7)), 5, i);
                    if (rs.getString(8).equals("true"))
                        grid.add(new Label("✔"), 6, i);
                    else if (rs.getString(8).equals("false"))
                        grid.add(new Label("❌"), 6, i);
                    else grid.add(new Label(rs.getString(8)), 6, i);
                    if (rs.getString(9).equals("true"))
                        grid.add(new Label("✔"), 7, i);
                    else if (rs.getString(9).equals("false"))
                        grid.add(new Label("❌"), 7, i);
                    else grid.add(new Label(rs.getString(9)), 7, i);
                    if (rs.getString(10).equals("true"))
                        grid.add(new Label("✔"), 8, i);
                    else if (rs.getString(10).equals("false"))
                        grid.add(new Label("❌"), 8, i);
                    else grid.add(new Label(rs.getString(10)), 8, i);
                    if (rs.getString(11).equals("true"))
                        grid.add(new Label("✔"), 9, i);
                    else if (rs.getString(11).equals("false"))
                        grid.add(new Label("❌"), 9, i);
                    else grid.add(new Label(rs.getString(11)), 9, i);
                    if (rs.getString(12).equals("true"))
                        grid.add(new Label("✔"), 10, i);
                    else if (rs.getString(12).equals("false"))
                        grid.add(new Label("❌"), 10, i);
                    else grid.add(new Label(rs.getString(12)), 10, i);
                    i++;
                }
                wishesScroll.setContent(grid);
            }
            else if(!allWishes && someWishes){
                GridPane grid=new GridPane();
                grid.setHgap(10);
                Label label1=new Label("Country Name");
                label1.setStyle("-fx-font-weight: bold");
                grid.add(label1, 0, 0);
                Label label2=new Label("Total Confirmed Cases");
                label2.setStyle("-fx-font-weight: bold");
                grid.add(label2, 1, 0);
                Label label3=new Label("Active Cases");
                label3.setStyle("-fx-font-weight: bold");
                grid.add(label3, 2, 0);
                Label label4=new Label("Cured Cases");
                label4.setStyle("-fx-font-weight: bold");
                grid.add(label4, 3, 0);
                Label label5=new Label("Total Deaths");
                label5.setStyle("-fx-font-weight: bold");
                grid.add(label5, 4, 0);
                Label label6=new Label("PCR Test Required for entry");
                label6.setStyle("-fx-font-weight: bold");
                grid.add(label6, 5, 0);
                Label label7=new Label("Mobile Tracing App Mandatory");
                label7.setStyle("-fx-font-weight: bold");
                grid.add(label7, 6, 0);
                Label label8=new Label("Quarantine required upon entry");
                label8.setStyle("-fx-font-weight: bold");
                grid.add(label8, 7, 0);
                Label label9=new Label("Wearing a mask outdoors required");
                label9.setStyle("-fx-font-weight: bold");
                grid.add(label9, 8, 0);
                Label label10=new Label("Vaccination Required for entry");
                label10.setStyle("-fx-font-weight: bold");
                grid.add(label10, 9, 0);
                Label label11=new Label("Overnight Curfew in Order");
                label11.setStyle("-fx-font-weight: bold");
                grid.add(label11, 10, 0);
                int i=0;
                for (String country : countries.keySet()) {
                    rs = stmt.executeQuery("select * from covid_cases where Country_Name='" + country + "'");
                    rs.next();
                    grid.add(new Label(country), 0, 0);
                    grid.add(new Label(rs.getString(3)), 1, i);
                    grid.add(new Label(rs.getString(4)), 2, i);
                    grid.add(new Label(rs.getString(5)), 3, i);
                    grid.add(new Label(rs.getString(6)), 4, i);
                    if (rs.getString(7).equals("true"))
                        grid.add(new Label("✔"), 5, i);
                    else if (rs.getString(7).equals("false"))
                        grid.add(new Label("❌"), 5, i);
                    else grid.add(new Label(rs.getString(7)), 5, i);
                    if (rs.getString(8).equals("true"))
                        grid.add(new Label("✔"), 6, i);
                    else if (rs.getString(8).equals("false"))
                        grid.add(new Label("❌"), 6, i);
                    else grid.add(new Label(rs.getString(8)), 6, i);
                    if (rs.getString(9).equals("true"))
                        grid.add(new Label("✔"), 7, i);
                    else if (rs.getString(9).equals("false"))
                        grid.add(new Label("❌"), 7, i);
                    else grid.add(new Label(rs.getString(9)), 7, i);
                    if (rs.getString(10).equals("true"))
                        grid.add(new Label("✔"), 8, i);
                    else if (rs.getString(10).equals("false"))
                        grid.add(new Label("❌"), 8, i);
                    else grid.add(new Label(rs.getString(10)), 8, i);
                    if (rs.getString(11).equals("true"))
                        grid.add(new Label("✔"), 9, i);
                    else if (rs.getString(11).equals("false"))
                        grid.add(new Label("❌"), 9, i);
                    else grid.add(new Label(rs.getString(11)), 9, i);
                    if (rs.getString(12).equals("true"))
                        grid.add(new Label("✔"), 10, i);
                    else if (rs.getString(12).equals("false"))
                        grid.add(new Label("❌"), 10, i);
                    else grid.add(new Label(rs.getString(12)), 10, i);
                    i++;
                }
                wishesScroll.setContent(grid);
            }
            else{
                GridPane allWishesGrid=new GridPane();
                GridPane someWishesGrid=new GridPane();
                allWishesGrid.setHgap(10);
                someWishesGrid.setHgap(10);
                Label label1=new Label("Country Name");
                label1.setStyle("-fx-font-weight: bold");
                Label label2=new Label("Total Confirmed Cases");
                label2.setStyle("-fx-font-weight: bold");
                Label label3=new Label("Active Cases");
                label3.setStyle("-fx-font-weight: bold");
                Label label4=new Label("Cured Cases");
                label4.setStyle("-fx-font-weight: bold");
                Label label5=new Label("Total Deaths");
                label5.setStyle("-fx-font-weight: bold");
                Label label6=new Label("PCR Test Required for entry");
                label6.setStyle("-fx-font-weight: bold");
                Label label7=new Label("Mobile Tracing App Mandatory");
                label7.setStyle("-fx-font-weight: bold");
                Label label8=new Label("Quarantine required upon entry");
                label8.setStyle("-fx-font-weight: bold");
                Label label9=new Label("Wearing a mask outdoors required");
                label9.setStyle("-fx-font-weight: bold");
                Label label10=new Label("Vaccination Required for entry");
                label10.setStyle("-fx-font-weight: bold");
                Label label11=new Label("Overnight Curfew in Order");
                label11.setStyle("-fx-font-weight: bold");
                allWishesGrid.add(label1, 0, 0);
                allWishesGrid.add(label2, 1, 0);
                allWishesGrid.add(label3, 2, 0);
                allWishesGrid.add(label4, 3, 0);
                allWishesGrid.add(label5, 4, 0);
                allWishesGrid.add(label6, 5, 0);
                allWishesGrid.add(label7, 6, 0);
                allWishesGrid.add(label8, 7, 0);
                allWishesGrid.add(label9, 8, 0);
                allWishesGrid.add(label10, 9, 0);
                allWishesGrid.add(label11, 10, 0);
                someWishesGrid.add(label1, 0, 0);
                someWishesGrid.add(label2, 1, 0);
                someWishesGrid.add(label3, 2, 0);
                someWishesGrid.add(label4, 3, 0);
                someWishesGrid.add(label5, 4, 0);
                someWishesGrid.add(label6, 5, 0);
                someWishesGrid.add(label7, 6, 0);
                someWishesGrid.add(label8, 7, 0);
                someWishesGrid.add(label9, 8, 0);
                someWishesGrid.add(label10, 9, 0);
                someWishesGrid.add(label11, 10, 0);
                int all=1;
                int some=1;
                for(String country:countries.keySet()){
                    rs=stmt.executeQuery("select * from covid_cases where Country_Name='"+country+"'");
                    rs.next();
                    if(countries.get(country)==wishCount){
                        allWishesGrid.add(new Label(country), 0, all);
                        allWishesGrid.add(new Label(rs.getString(3)), 1, all);
                        allWishesGrid.add(new Label(rs.getString(4)), 2, all);
                        allWishesGrid.add(new Label(rs.getString(5)), 3, all);
                        allWishesGrid.add(new Label(rs.getString(6)), 4, all);
                        if (rs.getString(7).equals("true"))
                            allWishesGrid.add(new Label("✔"), 5, all);
                        else if (rs.getString(7).equals("false"))
                            allWishesGrid.add(new Label("❌"), 5, all);
                        else allWishesGrid.add(new Label(rs.getString(7)), 5, all);
                        if (rs.getString(8).equals("true"))
                            allWishesGrid.add(new Label("✔"), 6, all);
                        else if (rs.getString(8).equals("false"))
                            allWishesGrid.add(new Label("❌"), 6, all);
                        else allWishesGrid.add(new Label(rs.getString(8)), 6, all);
                        if (rs.getString(9).equals("true"))
                            allWishesGrid.add(new Label("✔"), 7, all);
                        else if (rs.getString(9).equals("false"))
                            allWishesGrid.add(new Label("❌"), 7, all);
                        else allWishesGrid.add(new Label(rs.getString(9)), 7, all);
                        if (rs.getString(10).equals("true"))
                            allWishesGrid.add(new Label("✔"), 8, all);
                        else if (rs.getString(10).equals("false"))
                            allWishesGrid.add(new Label("❌"), 8, all);
                        else allWishesGrid.add(new Label(rs.getString(10)), 8, all);
                        if (rs.getString(11).equals("true"))
                            allWishesGrid.add(new Label("✔"), 9, all);
                        else if (rs.getString(11).equals("false"))
                            allWishesGrid.add(new Label("❌"), 9, all);
                        else allWishesGrid.add(new Label(rs.getString(11)), 9, all);
                        if (rs.getString(12).equals("true"))
                            allWishesGrid.add(new Label("✔"), 10, all);
                        else if (rs.getString(12).equals("false"))
                            allWishesGrid.add(new Label("❌"), 10, all);
                        else allWishesGrid.add(new Label(rs.getString(12)), 10, all);
                        all++;
                    }
                    else {
                        someWishesGrid.add(new Label(country), 0, some);
                        someWishesGrid.add(new Label(rs.getString(3)), 1, some);
                        someWishesGrid.add(new Label(rs.getString(4)), 2, some);
                        someWishesGrid.add(new Label(rs.getString(5)), 3, some);
                        someWishesGrid.add(new Label(rs.getString(6)), 4, some);
                        if (rs.getString(7).equals("true"))
                            someWishesGrid.add(new Label("✔"), 5, some);
                        else if (rs.getString(7).equals("false"))
                            someWishesGrid.add(new Label("❌"), 5, some);
                        else someWishesGrid.add(new Label(rs.getString(7)), 5, some);
                        if (rs.getString(8).equals("true"))
                            someWishesGrid.add(new Label("✔"), 6, some);
                        else if (rs.getString(8).equals("false"))
                            someWishesGrid.add(new Label("❌"), 6, some);
                        else someWishesGrid.add(new Label(rs.getString(8)), 6, some);
                        if (rs.getString(9).equals("true"))
                            someWishesGrid.add(new Label("✔"), 7, some);
                        else if (rs.getString(9).equals("false"))
                            someWishesGrid.add(new Label("❌"), 7, some);
                        else someWishesGrid.add(new Label(rs.getString(9)), 7, some);
                        if (rs.getString(10).equals("true"))
                            someWishesGrid.add(new Label("✔"), 8, some);
                        else if (rs.getString(10).equals("false"))
                            someWishesGrid.add(new Label("❌"), 8, some);
                        else someWishesGrid.add(new Label(rs.getString(10)), 8, some);
                        if (rs.getString(11).equals("true"))
                            someWishesGrid.add(new Label("✔"), 9, some);
                        else if (rs.getString(11).equals("false"))
                            someWishesGrid.add(new Label("❌"), 9, some);
                        else someWishesGrid.add(new Label(rs.getString(11)), 9, some);
                        if (rs.getString(12).equals("true"))
                            someWishesGrid.add(new Label("✔"), 10, some);
                        else if (rs.getString(12).equals("false"))
                            someWishesGrid.add(new Label("❌"), 10, some);
                        else someWishesGrid.add(new Label(rs.getString(12)), 10, some);
                        some++;
                    }
                }
                allWishesScroll.setContent(allWishesGrid);
                someWishesScroll.setContent(someWishesGrid);
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
        int i=0;
        ObservableList<MenuItem> menuButtonItems=pcr.getItems();
        while (i<menuButtonItems.size()){
            MenuItem item=menuButtonItems.get(i);
            item.setOnAction(act-> pcr.setText(item.getText()));
            i++;
        }
        i=0;
        menuButtonItems=app.getItems();
        while (i<menuButtonItems.size()){
            MenuItem item=menuButtonItems.get(i);
            item.setOnAction(act-> app.setText(item.getText()));
            i++;
        }
        i=0;
        menuButtonItems=quarantine.getItems();
        while (i<menuButtonItems.size()){
            MenuItem item=menuButtonItems.get(i);
            item.setOnAction(act-> quarantine.setText(item.getText()));
            i++;
        }
        i=0;
        menuButtonItems=mask.getItems();
        while (i<menuButtonItems.size()){
            MenuItem item=menuButtonItems.get(i);
            item.setOnAction(act-> mask.setText(item.getText()));
            i++;
        }
        i=0;
        menuButtonItems=vaccine.getItems();
        while (i<menuButtonItems.size()){
            MenuItem item=menuButtonItems.get(i);
            item.setOnAction(act-> vaccine.setText(item.getText()));
            i++;
        }
        i=0;
        menuButtonItems=curfew.getItems();
        while (i<menuButtonItems.size()){
            MenuItem item=menuButtonItems.get(i);
            item.setOnAction(act-> curfew.setText(item.getText()));
            i++;
        }
    }
}