package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AllCountriesModController implements Initializable {
    @FXML
    Label nameLabel = new Label();
    @FXML
    ScrollPane scroll=new ScrollPane();
    GridPane grid=new GridPane();
    @FXML
    Rectangle darken=new Rectangle();
    @FXML
    AnchorPane changeCountryDataPicker=new AnchorPane();
    @FXML
    AnchorPane deleteCountryPicker=new AnchorPane();
    @FXML
    ComboBox<String> pickCountry=new ComboBox<>();
    @FXML
    ComboBox<String> pickCountryDelete=new ComboBox<>();
    String countryName="";

    public void prepareGrid() throws SQLException {
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
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        ResultSet rs=stmt.executeQuery("select * from covid_cases");
        while(rs.next()){
            pickCountry.getItems().add(rs.getString(1));
            pickCountryDelete.getItems().add(rs.getString(1));
            grid.add(new Label(rs.getString(2)), 0, i);
            grid.add(new Label(rs.getString(3)), 1, i);
            grid.add(new Label(rs.getString(4)), 2, i);
            grid.add(new Label(rs.getString(5)), 3, i);
            grid.add(new Label(rs.getString(6)), 4, i);
            if(rs.getString(7).equals("true"))
                grid.add(new Label("✔"), 5, i);
            else if(rs.getString(7).equals("false"))
                grid.add(new Label("❌"), 5, i);
            else if(rs.getString(7).equals("unknown"))
                grid.add(new Label("-"), 5, i);
            else grid.add(new Label(rs.getString(7)), 5, i);
            if(rs.getString(8).equals("true"))
                grid.add(new Label("✔"), 6, i);
            else if(rs.getString(8).equals("false"))
                grid.add(new Label("❌"), 6, i);
            else if(rs.getString(8).equals("unknown"))
                grid.add(new Label("-"), 6, i);
            else grid.add(new Label(rs.getString(8)), 6, i);
            if(rs.getString(9).equals("true"))
                grid.add(new Label("✔"), 7, i);
            else if(rs.getString(9).equals("false"))
                grid.add(new Label("❌"), 7, i);
            else if(rs.getString(9).equals("unknown"))
                grid.add(new Label("-"), 7, i);
            else grid.add(new Label(rs.getString(9)), 7, i);
            if(rs.getString(10).equals("true"))
                grid.add(new Label("✔"), 8, i);
            else if(rs.getString(10).equals("false"))
                grid.add(new Label("❌"), 8, i);
            else if(rs.getString(10).equals("unknown"))
                grid.add(new Label("-"), 8, i);
            else grid.add(new Label(rs.getString(10)), 8, i);
            if(rs.getString(11).equals("true"))
                grid.add(new Label("✔"), 9, i);
            else if(rs.getString(11).equals("false"))
                grid.add(new Label("❌"), 9, i);
            else if(rs.getString(11).equals("unknown"))
                grid.add(new Label("-"), 9, i);
            else grid.add(new Label(rs.getString(11)), 9, i);
            if(rs.getString(12).equals("true"))
                grid.add(new Label("✔"), 10, i);
            else if(rs.getString(12).equals("false"))
                grid.add(new Label("❌"), 10, i);
            else if(rs.getString(12).equals("unknown"))
                grid.add(new Label("-"), 10, i);
            else grid.add(new Label(rs.getString(12)), 10, i);
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
        scroll.setContent(grid);
    }
    @Override
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

            grid.setHgap(10);
            prepareGrid();

            rs= stmt.executeQuery("select country_name from covid_cases");
            while (rs.next()){
                pickCountry.getItems().addAll(rs.getString("Country_Name"));
                pickCountryDelete.getItems().addAll(rs.getString("Country_Name"));
            }
            rs.beforeFirst();
            rs.next();
            pickCountry.setValue(rs.getString("Country_Name"));
            pickCountryDelete.setValue(rs.getString("Country_Name"));
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void seeHeatmap() {
        Stage stage=new Stage();
        stage.setTitle("[MODERATING] COVID-19 World Heatmap");
        WebView web = new WebView();
        web.setContextMenuEnabled(false);
        web.setPrefWidth(1200);
        web.setPrefHeight(900);
        web.getEngine().load("https://covid19.who.int/?gclid=Cj0KCQjwvLOTBhCJARIsACVldV0-53PrHkeeZP7maRlMRt6F-oq3ZuRMwD2Vd-ZjQFIKM_mIFrf40lQaAh4bEALw_wcB");
        VBox root = new VBox();
        root.getChildren().add(web);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        Scene homeMod=new Scene(FXMLLoader.load(MTDL_Project.class.getResource("HomeModerator.fxml")));
        stage.setScene(homeMod);
        stage.setTitle("[MODERATING] Home");
    }

    public void seeChangePicker(ActionEvent event) {
        darken.setVisible(true);
        changeCountryDataPicker.setVisible(true);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] Choose a country");
    }
    public void seeDeletePicker(ActionEvent event) {
        darken.setVisible(true);
        deleteCountryPicker.setVisible(true);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] Choose a country");
    }

    public void hideChangePicker(ActionEvent event){
        changeCountryDataPicker.setVisible(false);
        darken.setVisible(false);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] All countries");
    }
    public void hideDeletePicker(ActionEvent event){
        deleteCountryPicker.setVisible(false);
        darken.setVisible(false);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] All countries");
    }

    public void seeChangeScreen(ActionEvent event) throws IOException {
        changeCountryDataPicker.setVisible(false);
        darken.setVisible(false);
        Scanner sc=new Scanner(new FileReader("data"));
        String fileContent=sc.nextLine();
        sc.close();
        fileContent=fileContent+"\ncountry:"+pickCountry.getValue();
        BufferedWriter writeCountry=new BufferedWriter(new FileWriter("data"));
        writeCountry.write(fileContent);
        writeCountry.close();
        Scene changeData =new Scene(FXMLLoader.load(MTDL_Project.class.getResource("ChangeCountryDataMod.fxml")));
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(changeData);
        stage.setTitle("[MODERATING] Change country data");
    }

    public void seeAddScreen(ActionEvent event) throws IOException, SQLException {
        TextInputDialog getCountry=new TextInputDialog();
        getCountry.setHeaderText("Input the name of the country you'd like to add to the database");
        getCountry.showAndWait();
        countryName=getCountry.getEditor().getText();
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        ResultSet rs=stmt.executeQuery("select count(Country_Name) from covid_cases where Country_Name='"+countryName+"'");
        rs.next();
        if(rs.getInt(1)==0) {
            Scanner sc = new Scanner(new FileReader("data"));
            String fileContent = sc.nextLine();
            sc.close();
            fileContent = fileContent + "\nadd:country:" + countryName;
            BufferedWriter writeCountry = new BufferedWriter(new FileWriter("data"));
            writeCountry.write(fileContent);
            writeCountry.close();
            Scene changeData = new Scene(FXMLLoader.load(MTDL_Project.class.getResource("ChangeCountryDataMod.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(changeData);
            stage.setTitle("[MODERATING] Change country data");
        }
        else {
            Alert wrong = new Alert(Alert.AlertType.INFORMATION);
            wrong.setHeaderText("Country " + countryName + " already exists in the database!");
            wrong.showAndWait();
        }
    }

    public void deleteCountry(ActionEvent event) throws SQLException {
        String countryName=pickCountryDelete.getValue();
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        stmt.executeUpdate("delete from covid_cases where Country_Name='"+countryName+"'");
        Alert success=new Alert(Alert.AlertType.INFORMATION);
        success.setHeaderText("Successfully deleted country "+countryName+" from the database!");
        success.showAndWait();
        deleteCountryPicker.setVisible(false);
        darken.setVisible(false);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("[MODERATING] All countries");
        grid.getChildren().clear();
        pickCountryDelete.getItems().clear();
        pickCountry.getItems().clear();
        prepareGrid();
    }
}