package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

public class CountrySpecsController implements Initializable {
    @FXML
    Label nameLabel=new Label();
    @FXML
    Label countryLabel=new Label();
    @FXML
    Label confirmed=new Label(), active=new Label(), cured=new Label(), deaths=new Label();
    @FXML
    AnchorPane incompleteInfoWarning;
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
            Connection data=DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt=data.createStatement();
            ResultSet rs = stmt.executeQuery("select First_Name from users where Email='"+email+"'");
            rs.next();
            nameLabel.setText(rs.getString("First_Name")+"!");
            countryLabel.setText(country);
            rs=stmt.executeQuery("select Total_Confirmed_Cases, Active_Cases, Cured_Cases, Total_Deaths from covid_cases where Country_Name='"+country+"'");
            int i=1;
            rs.next();
            while(i<4){
                if(rs.getString(i).equals("unknown")){
                    incompleteInfoWarning.setVisible(true);
                    break;
                }
                i++;
            }
            rs.beforeFirst();
            rs.next();
            if(!rs.getString(1).equals("unknown")) confirmed.setText(rs.getString(1));
            else confirmed.setText("-"); confirmed.setStyle("-fx-font-weight: bold");
            if(!rs.getString(2).equals("unknown")) active.setText(rs.getString(2));
            else active.setText("-"); active.setStyle("-fx-font-weight: bold");
            if(!rs.getString(3).equals("unknown")) cured.setText(rs.getString(3));
            else cured.setText("-"); cured.setStyle("-fx-font-weight: bold");
            if(!rs.getString(4).equals("unknown")) deaths.setText(rs.getString(4));
            else deaths.setText("-"); deaths.setStyle("-fx-font-weight: bold");
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