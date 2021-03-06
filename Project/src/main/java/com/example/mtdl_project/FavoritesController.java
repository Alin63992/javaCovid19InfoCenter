package com.example.mtdl_project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FavoritesController implements Initializable {
    @FXML
    ScrollPane favoriteCountriesScroll=new ScrollPane();
    @FXML
    Button removeButton=new Button();
    @FXML
    AnchorPane deleteCountryPicker=new AnchorPane();
    @FXML
    ComboBox<String> pickCountryDelete=new ComboBox<>();
    @FXML
    Rectangle darken;
    GridPane grid=new GridPane();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scanner readEmail= null;
        try {
            readEmail = new Scanner(new File("data"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            ResultSet rs = stmt.executeQuery("select Favorites from users where Email='" + email + "'");
            rs.next();
            String[] countries=rs.getString(1).split(", ");
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
            int i=1;
            for(String c: countries){
                pickCountryDelete.getItems().add(c);
                rs=stmt.executeQuery("select * from covid_cases where Country_Name='"+c+"'");
                rs.next();
                grid.add(new Label(rs.getString(2)), 0, i);
                grid.add(new Label(rs.getString(3)), 1, i);
                grid.add(new Label(rs.getString(4)), 2, i);
                grid.add(new Label(rs.getString(5)), 3, i);
                grid.add(new Label(rs.getString(6)), 4, i);
                if(rs.getString(7).equals("true"))
                    grid.add(new Label("???"), 5, i);
                else if(rs.getString(7).equals("false"))
                    grid.add(new Label("???"), 5, i);
                else grid.add(new Label("-"), 5, i);
                if(rs.getString(8).equals("true"))
                    grid.add(new Label("???"), 6, i);
                else if(rs.getString(8).equals("false"))
                    grid.add(new Label("???"), 6, i);
                else grid.add(new Label("-"), 6, i);
                if(rs.getString(9).equals("true"))
                    grid.add(new Label("???"), 7, i);
                else if(rs.getString(9).equals("false"))
                    grid.add(new Label("???"), 7, i);
                else if(rs.getString(9).equals("unknown"))
                    grid.add(new Label("-"), 7, i);
                else grid.add(new Label(rs.getString(9)), 7, i);
                if(rs.getString(10).equals("true"))
                    grid.add(new Label("???"), 8, i);
                else if(rs.getString(10).equals("false"))
                    grid.add(new Label("???"), 8, i);
                else grid.add(new Label("-"), 8, i);
                if(rs.getString(11).equals("true"))
                    grid.add(new Label("???"), 9, i);
                else if(rs.getString(11).equals("false"))
                    grid.add(new Label("???"), 9, i);
                else grid.add(new Label("-"), 9, i);
                if(rs.getString(12).equals("true"))
                    grid.add(new Label("???"), 10, i);
                else if(rs.getString(12).equals("false"))
                    grid.add(new Label("???"), 10, i);
                else grid.add(new Label("-"), 10, i);
                i++;
            }
            int numberOfRows=grid.getRowCount();
            Label label12=new Label("Country Name");
            label12.setStyle("-fx-font-weight: bold");
            grid.add(label12, 0, numberOfRows);
            Label label13=new Label("Total Confirmed Cases");
            label13.setStyle("-fx-font-weight: bold");
            grid.add(label13, 1, numberOfRows);
            Label label14=new Label("Active Cases");
            label14.setStyle("-fx-font-weight: bold");
            grid.add(label14, 2, numberOfRows);
            Label label15=new Label("Cured Cases");
            label15.setStyle("-fx-font-weight: bold");
            grid.add(label15, 3, numberOfRows);
            Label label16=new Label("Total Deaths");
            label16.setStyle("-fx-font-weight: bold");
            grid.add(label16, 4, numberOfRows);
            Label label17=new Label("PCR Test Required for entry");
            label17.setStyle("-fx-font-weight: bold");
            grid.add(label17, 5, numberOfRows);
            Label label18=new Label("Mobile Tracing App Mandatory");
            label18.setStyle("-fx-font-weight: bold");
            grid.add(label18, 6, numberOfRows);
            Label label19=new Label("Quarantine required upon entry");
            label19.setStyle("-fx-font-weight: bold");
            grid.add(label19, 7, numberOfRows);
            Label label20=new Label("Wearing a mask outdoors required");
            label20.setStyle("-fx-font-weight: bold");
            grid.add(label20, 8, numberOfRows);
            Label label21=new Label("Vaccination Required for entry");
            label21.setStyle("-fx-font-weight: bold");
            grid.add(label21, 9, numberOfRows);
            Label label22=new Label("Overnight Curfew in Order");
            label22.setStyle("-fx-font-weight: bold");
            grid.add(label22, 10, numberOfRows);
            favoriteCountriesScroll.setContent(grid);
            pickCountryDelete.setValue(countries[0]);
        }
        catch(SQLException e){
            favoriteCountriesScroll.setVisible(false);
            removeButton.setVisible(false);
        }
    }
    public void seeDeletePicker(){
        darken.setVisible(true);
        deleteCountryPicker.setVisible(true);
    }
    public void hideDeletePicker(){
        deleteCountryPicker.setVisible(false);
        darken.setVisible(false);
    }
    public void removeCountries() throws FileNotFoundException, SQLException {
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
        String countryToBeRemoved=pickCountryDelete.getValue();
        ResultSet rs = stmt.executeQuery("select Favorites from users where Email='"+email+"'");
        rs.next();
        ArrayList<String> favCountries = new ArrayList<>(Arrays.asList(rs.getString(1).split(", ")));
        favCountries.remove(countryToBeRemoved);
        String favoriteCountries="";
        for(String c: favCountries){
            if(favCountries.indexOf(c)!=favCountries.size()-1){
                favoriteCountries = favoriteCountries + c + ", ";
            }
            else favoriteCountries = favoriteCountries + c;
        }
        stmt.executeUpdate("update users set Favorites='"+favoriteCountries+"' where Email='"+email+"'");
        deleteCountryPicker.setVisible(false);
        darken.setVisible(false);
        grid.getChildren().clear();
        pickCountryDelete.getItems().clear();
        rs = stmt.executeQuery("select Favorites from users where Email='" + email + "'");
        try {
            rs.next();
            String[] countries = rs.getString(1).split(", ");
            grid.setHgap(10);
            Label label1 = new Label("Country Name");
            label1.setStyle("-fx-font-weight: bold");
            grid.add(label1, 0, 0);
            Label label2 = new Label("Total Confirmed Cases");
            label2.setStyle("-fx-font-weight: bold");
            grid.add(label2, 1, 0);
            Label label3 = new Label("Active Cases");
            label3.setStyle("-fx-font-weight: bold");
            grid.add(label3, 2, 0);
            Label label4 = new Label("Cured Cases");
            label4.setStyle("-fx-font-weight: bold");
            grid.add(label4, 3, 0);
            Label label5 = new Label("Total Deaths");
            label5.setStyle("-fx-font-weight: bold");
            grid.add(label5, 4, 0);
            Label label6 = new Label("PCR Test Required for entry");
            label6.setStyle("-fx-font-weight: bold");
            grid.add(label6, 5, 0);
            Label label7 = new Label("Mobile Tracing App Mandatory");
            label7.setStyle("-fx-font-weight: bold");
            grid.add(label7, 6, 0);
            Label label8 = new Label("Quarantine required upon entry");
            label8.setStyle("-fx-font-weight: bold");
            grid.add(label8, 7, 0);
            Label label9 = new Label("Wearing a mask outdoors required");
            label9.setStyle("-fx-font-weight: bold");
            grid.add(label9, 8, 0);
            Label label10 = new Label("Vaccination Required for entry");
            label10.setStyle("-fx-font-weight: bold");
            grid.add(label10, 9, 0);
            Label label11 = new Label("Overnight Curfew in Order");
            label11.setStyle("-fx-font-weight: bold");
            grid.add(label11, 10, 0);
            int i = 1;
            for (String c : countries) {
                pickCountryDelete.getItems().add(c);
                rs = stmt.executeQuery("select * from covid_cases where Country_Name='" + c + "'");
                rs.next();
                grid.add(new Label(rs.getString(2)), 0, i);
                grid.add(new Label(rs.getString(3)), 1, i);
                grid.add(new Label(rs.getString(4)), 2, i);
                grid.add(new Label(rs.getString(5)), 3, i);
                grid.add(new Label(rs.getString(6)), 4, i);
                if (rs.getString(7).equals("true"))
                    grid.add(new Label("???"), 5, i);
                else if (rs.getString(7).equals("false"))
                    grid.add(new Label("???"), 5, i);
                else grid.add(new Label("-"), 5, i);
                if (rs.getString(8).equals("true"))
                    grid.add(new Label("???"), 6, i);
                else if (rs.getString(8).equals("false"))
                    grid.add(new Label("???"), 6, i);
                else grid.add(new Label("-"), 6, i);
                if (rs.getString(9).equals("true"))
                    grid.add(new Label("???"), 7, i);
                else if (rs.getString(9).equals("false"))
                    grid.add(new Label("???"), 7, i);
                else if (rs.getString(9).equals("unknown"))
                    grid.add(new Label("-"), 7, i);
                else grid.add(new Label(rs.getString(9)), 7, i);
                if (rs.getString(10).equals("true"))
                    grid.add(new Label("???"), 8, i);
                else if (rs.getString(10).equals("false"))
                    grid.add(new Label("???"), 8, i);
                else grid.add(new Label("-"), 8, i);
                if (rs.getString(11).equals("true"))
                    grid.add(new Label("???"), 9, i);
                else if (rs.getString(11).equals("false"))
                    grid.add(new Label("???"), 9, i);
                else grid.add(new Label("-"), 9, i);
                if (rs.getString(12).equals("true"))
                    grid.add(new Label("???"), 10, i);
                else if (rs.getString(12).equals("false"))
                    grid.add(new Label("???"), 10, i);
                else grid.add(new Label("-"), 10, i);
                i++;
            }
            int numberOfRows = grid.getRowCount();
            Label label12 = new Label("Country Name");
            label12.setStyle("-fx-font-weight: bold");
            grid.add(label12, 0, numberOfRows);
            Label label13 = new Label("Total Confirmed Cases");
            label13.setStyle("-fx-font-weight: bold");
            grid.add(label13, 1, numberOfRows);
            Label label14 = new Label("Active Cases");
            label14.setStyle("-fx-font-weight: bold");
            grid.add(label14, 2, numberOfRows);
            Label label15 = new Label("Cured Cases");
            label15.setStyle("-fx-font-weight: bold");
            grid.add(label15, 3, numberOfRows);
            Label label16 = new Label("Total Deaths");
            label16.setStyle("-fx-font-weight: bold");
            grid.add(label16, 4, numberOfRows);
            Label label17 = new Label("PCR Test Required for entry");
            label17.setStyle("-fx-font-weight: bold");
            grid.add(label17, 5, numberOfRows);
            Label label18 = new Label("Mobile Tracing App Mandatory");
            label18.setStyle("-fx-font-weight: bold");
            grid.add(label18, 6, numberOfRows);
            Label label19 = new Label("Quarantine required upon entry");
            label19.setStyle("-fx-font-weight: bold");
            grid.add(label19, 7, numberOfRows);
            Label label20 = new Label("Wearing a mask outdoors required");
            label20.setStyle("-fx-font-weight: bold");
            grid.add(label20, 8, numberOfRows);
            Label label21 = new Label("Vaccination Required for entry");
            label21.setStyle("-fx-font-weight: bold");
            grid.add(label21, 9, numberOfRows);
            Label label22 = new Label("Overnight Curfew in Order");
            label22.setStyle("-fx-font-weight: bold");
            grid.add(label22, 10, numberOfRows);
            favoriteCountriesScroll.setContent(grid);
            pickCountryDelete.setValue(countries[0]);
        }
        catch (SQLException e){
            favoriteCountriesScroll.setVisible(false);
            removeButton.setVisible(false);
        }
    }
}