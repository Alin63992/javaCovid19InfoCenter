package com.example.mtdl_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChangeCountryDataModController implements Initializable{
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
    Label countryLabel=new Label();
    @FXML
    Button customPCR=new Button();
    @FXML
    Button customApp=new Button();
    @FXML
    Button customQuarantine=new Button();
    @FXML
    Button customMask=new Button();
    @FXML
    Button customVaccine=new Button();
    @FXML
    Button customCurfew=new Button();
    String pcrValue=pcr.getText();
    String appValue=app.getText();
    String quarantineValue=quarantine.getText();
    String maskValue=mask.getText();
    String vaccineValue=vaccine.getText();
    String curfewValue=curfew.getText();
    String confirmedValue=confirmed.getText();
    String activeValue=active.getText();
    String curedValue=cured.getText();
    String deathsValue=deaths.getText();
    boolean add=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            Scanner readData = new Scanner(new File("data"));
            String email = null;
            String country = null;
            ResultSet rs;
            while (readData.hasNext()) {
                String line = readData.nextLine();
                if (line.contains("email:")) {
                    String[] parts = line.split(":");
                    email = parts[1];
                } else if (line.contains("country:")) {
                    String[] parts = line.split(":");
                    if(line.contains("add:")){
                        add=true;
                        country = parts[2];
                    }
                    else {
                        add=false;
                        country = parts[1];
                    }
                }
            }
            readData.close();
            countryLabel.setText(country);
            rs = stmt.executeQuery("select first_name from users where Email='" + email + "'");
            rs.next();
            nameLabel.setText(rs.getString(1));
            if(!add) {
                rs = stmt.executeQuery("select * from covid_cases where country_name='" + country + "'");
                rs.next();
                confirmed.setText(rs.getString(3));
                confirmedValue = rs.getString(3);
                active.setText(rs.getString(4));
                activeValue = rs.getString(4);
                cured.setText(rs.getString(5));
                curedValue = rs.getString(5);
                deaths.setText(rs.getString(6));
                deathsValue = rs.getString(6);
                for (MenuItem item : pcr.getItems()) {
                    if (rs.getString(7).equals("true") && item.getText().equals("Required")) {
                        pcr.setText(item.getText());
                        customPCR.setVisible(false);
                        pcrValue = "true";
                        break;
                    } else if (rs.getString(7).equals("false") && item.getText().equals("Not required")) {
                        pcr.setText(item.getText());
                        customPCR.setVisible(false);
                        pcrValue = "false";
                        break;
                    } else {
                        pcrValue = rs.getString(7);
                        if (item.getText().equals("Custom value")) {
                            pcr.setText(item.getText());
                            customPCR.setVisible(true);
                            customPCR.setOnAction(a -> {
                                TextInputDialog customValue = new TextInputDialog(pcrValue);
                                customValue.setHeaderText("Enter custom value for PCR test entry requirement");
                                customValue.showAndWait();
                                pcrValue = customValue.getEditor().getText();
                            });
                        }
                    }
                }
                for (MenuItem item : app.getItems()) {
                    if (rs.getString(8).equals("true") && item.getText().equals("Required")) {
                        app.setText(item.getText());
                        customApp.setVisible(false);
                        appValue = "true";
                        break;
                    } else if (rs.getString(8).equals("false") && item.getText().equals("Not required")) {
                        app.setText(item.getText());
                        customApp.setVisible(false);
                        appValue = "false";
                        break;
                    } else {
                        appValue = rs.getString(8);
                        if (item.getText().equals("Custom value")) {
                            app.setText(item.getText());
                            customApp.setVisible(true);
                            customApp.setOnAction(a -> {
                                TextInputDialog customValue = new TextInputDialog(appValue);
                                customValue.setHeaderText("Enter custom value for mobile tracing app usage requirement");
                                customValue.showAndWait();
                                appValue = customValue.getEditor().getText();
                            });
                        }
                    }
                }
                for (MenuItem item : quarantine.getItems()) {
                    if (rs.getString(9).equals("true") && item.getText().equals("Required")) {
                        quarantine.setText(item.getText());
                        customQuarantine.setVisible(false);
                        quarantineValue = "true";
                        break;
                    } else if (rs.getString(9).equals("false") && item.getText().equals("Not required")) {
                        quarantine.setText(item.getText());
                        customQuarantine.setVisible(false);
                        quarantineValue = "false";
                        break;
                    } else {
                        quarantineValue = rs.getString(9);
                        if (item.getText().equals("Custom value")) {
                            quarantine.setText(item.getText());
                            customQuarantine.setVisible(true);
                            customQuarantine.setOnAction(a -> {
                                TextInputDialog customValue = new TextInputDialog(quarantineValue);
                                customValue.setHeaderText("Enter custom value for quarantining requirement");
                                customValue.showAndWait();
                                quarantineValue = customValue.getEditor().getText();
                            });
                        }
                    }
                }
                for (MenuItem item : mask.getItems()) {
                    if (rs.getString(10).equals("true") && item.getText().equals("Required")) {
                        mask.setText(item.getText());
                        customMask.setVisible(false);
                        maskValue = "true";
                        break;
                    } else if (rs.getString(10).equals("false") && item.getText().equals("Not required")) {
                        mask.setText(item.getText());
                        customMask.setVisible(false);
                        maskValue = "false";
                        break;
                    } else {
                        maskValue = rs.getString(10);
                        if (item.getText().equals("Custom value")) {
                            mask.setText(item.getText());
                            customMask.setVisible(true);
                            customMask.setOnAction(a -> {
                                TextInputDialog customValue = new TextInputDialog(maskValue);
                                customValue.setHeaderText("Enter custom value for mask wearing requirement");
                                customValue.showAndWait();
                                maskValue = customValue.getEditor().getText();
                            });
                        }
                    }
                }
                for (MenuItem item : vaccine.getItems()) {
                    if (rs.getString(11).equals("true") && item.getText().equals("Required")) {
                        vaccine.setText(item.getText());
                        customVaccine.setVisible(false);
                        vaccineValue = "true";
                        break;
                    } else if (rs.getString(11).equals("false") && item.getText().equals("Not required")) {
                        vaccine.setText(item.getText());
                        customVaccine.setVisible(false);
                        vaccineValue = "false";
                        break;
                    } else {
                        vaccineValue = rs.getString(11);
                        if (item.getText().equals("Custom value")) {
                            vaccine.setText(item.getText());
                            customVaccine.setVisible(true);
                            customVaccine.setOnAction(a -> {
                                TextInputDialog customValue = new TextInputDialog(vaccineValue);
                                customValue.setHeaderText("Enter custom value for vaccination entry requirement");
                                customValue.showAndWait();
                                vaccineValue = customValue.getEditor().getText();
                            });
                        }
                    }
                }
                for (MenuItem item : curfew.getItems()) {
                    if (rs.getString(12).equals("true") && item.getText().equals("Required")) {
                        curfew.setText(item.getText());
                        customCurfew.setVisible(false);
                        curfewValue = "true";
                        break;
                    } else if (rs.getString(12).equals("false") && item.getText().equals("Not required")) {
                        curfew.setText(item.getText());
                        customCurfew.setVisible(false);
                        curfewValue = "false";
                        break;
                    } else {
                        curfewValue = rs.getString(12);
                        if (item.getText().equals("Custom value")) {
                            curfew.setText(item.getText());
                            customCurfew.setVisible(true);
                            customCurfew.setOnAction(a -> {
                                TextInputDialog customValue = new TextInputDialog(curfewValue);
                                customValue.setHeaderText("Enter custom value for nightly curfew being set");
                                customValue.showAndWait();
                                curfewValue = customValue.getEditor().getText();
                            });
                        }
                    }
                }
            }
        }
        catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
        for (MenuItem item: pcr.getItems()){
            item.setOnAction(act->{
                pcr.setText(item.getText());
                customPCR.setVisible(false);
                if(item.getText().equals("Required")) pcrValue="true";
                else if(item.getText().equals("Not required")) pcrValue="false";
                else {
                    customPCR.setVisible(true);
                    customPCR.setOnAction(a -> {
                        TextInputDialog customValue = new TextInputDialog(pcrValue);
                        customValue.setHeaderText("Enter custom value for PCR test entry requirement");
                        customValue.showAndWait();
                        pcrValue = customValue.getEditor().getText();
                    });
                }
            });
        }
        for (MenuItem item: app.getItems()){
            item.setOnAction(act->{
                app.setText(item.getText());
                customApp.setVisible(false);
                if(item.getText().equals("Required")) appValue="true";
                else if(item.getText().equals("Not required")) appValue="false";
                else {
                    customApp.setVisible(true);
                    customApp.setOnAction(a -> {
                        TextInputDialog customValue = new TextInputDialog(pcrValue);
                        customValue.setHeaderText("Enter custom value for mobile tracing app usage requirement");
                        customValue.showAndWait();
                        appValue = customValue.getEditor().getText();
                    });
                }
            });
        }
        for (MenuItem item: quarantine.getItems()){
            item.setOnAction(act->{
                quarantine.setText(item.getText());
                customQuarantine.setVisible(false);
                if(item.getText().equals("Required")) quarantineValue="true";
                else if(item.getText().equals("Not required")) quarantineValue="false";
                else {
                    customQuarantine.setVisible(true);
                    customQuarantine.setOnAction(a -> {
                        TextInputDialog customValue = new TextInputDialog(quarantineValue);
                        customValue.setHeaderText("Enter custom value for PCR test entry requirement");
                        customValue.showAndWait();
                        quarantineValue = customValue.getEditor().getText();
                    });
                }
            });
        }
        for (MenuItem item: mask.getItems()){
            item.setOnAction(act->{
                mask.setText(item.getText());
                customMask.setVisible(false);
                if(item.getText().equals("Required")) maskValue="true";
                else if(item.getText().equals("Not required")) maskValue="false";
                else {
                    customMask.setVisible(true);
                    customMask.setOnAction(a -> {
                        TextInputDialog customValue = new TextInputDialog(maskValue);
                        customValue.setHeaderText("Enter custom value for PCR test entry requirement");
                        customValue.showAndWait();
                        maskValue = customValue.getEditor().getText();
                    });
                }
            });
        }
        for (MenuItem item: vaccine.getItems()){
            item.setOnAction(act->{
                vaccine.setText(item.getText());
                customVaccine.setVisible(false);
                if(item.getText().equals("Required")) vaccineValue="true";
                else if(item.getText().equals("Not required")) vaccineValue="false";
                else {
                    customVaccine.setVisible(true);
                    customVaccine.setOnAction(a -> {
                        TextInputDialog customValue = new TextInputDialog(vaccineValue);
                        customValue.setHeaderText("Enter custom value for PCR test entry requirement");
                        customValue.showAndWait();
                        vaccineValue = customValue.getEditor().getText();
                    });
                }
            });
        }
        for (MenuItem item: curfew.getItems()){
            item.setOnAction(act->{
                curfew.setText(item.getText());
                customCurfew.setVisible(false);
                if(item.getText().equals("Required")) curfewValue="true";
                else if(item.getText().equals("Not required")) curfewValue="false";
                else {
                    customCurfew.setVisible(true);
                    customCurfew.setOnAction(a -> {
                        TextInputDialog customValue = new TextInputDialog(curfewValue);
                        customValue.setHeaderText("Enter custom value for PCR test entry requirement");
                        customValue.showAndWait();
                        curfewValue = customValue.getEditor().getText();
                    });
                }
            });
        }
    }

    public void saveChanges(ActionEvent event) throws SQLException, IOException {
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt = data.createStatement();
        if(pcrValue.equals("")) pcrValue="unknown";
        if(appValue.equals("")) appValue="unknown";
        if(quarantineValue.equals("")) quarantineValue="unknown";
        if(maskValue.equals("")) maskValue="unknown";
        if(vaccineValue.equals("")) vaccineValue="unknown";
        if(curfewValue.equals("")) curfewValue="unknown";
        if(confirmed.getText().equals("")) confirmedValue="unknown"; else confirmedValue=confirmed.getText();
        if(active.getText().equals("")) activeValue="unknown"; else activeValue=active.getText();
        if(cured.getText().equals("")) curedValue="unknown"; else curedValue=cured.getText();
        if(deaths.getText().equals("")) deathsValue="unknown"; else deathsValue=deaths.getText();
        if(!add) {
            stmt.executeUpdate("update covid_cases set Date_Updated='" + LocalDate.now() + "', Total_Confirmed_Cases='" + confirmedValue + "', Active_Cases='" + activeValue + "', Cured_Cases='" + curedValue + "', Total_Deaths='" + deathsValue + "', PCR_Test_Required='" + pcrValue + "', Mobile_Tracing_App='" + appValue + "', quarantine='" + quarantineValue + "', mask='" + maskValue + "', vaccine='" + vaccineValue + "', curfew='" + curfewValue + "' where Country_Name='" + countryLabel.getText() + "'");
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Successfully updated data for " + countryLabel.getText() + "!");
            success.showAndWait();
        }
        else {
            stmt.executeUpdate("insert into covid_cases (Date_Updated, Country_Name, Total_Confirmed_Cases, Active_Cases, Cured_Cases, Total_Deaths, PCR_Test_Required, Mobile_Tracing_App, quarantine, mask, vaccine, curfew) VALUES ('"+LocalDate.now()+"', '"+countryLabel.getText()+"', '"+confirmedValue+"', '"+activeValue+"', '"+curedValue+"', '"+deathsValue+"', '"+pcrValue+"', '"+appValue+"', '"+quarantineValue+"', '"+maskValue+"', '"+vaccineValue+"', '"+curfewValue+"')");
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Successfully added country " + countryLabel.getText() + " and its data!");
            success.showAndWait();
        }
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(MTDL_Project.class.getResource("AllCountriesMod.fxml"))));
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

    public void goHome(ActionEvent event) throws IOException {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene homeMod=new Scene(FXMLLoader.load(MTDL_Project.class.getResource("HomeModerator.fxml")));
        stage.setScene(homeMod);
        stage.setTitle("[MODERATING] Home");
    }

    public void goBack(ActionEvent event) throws IOException {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene back =new Scene(FXMLLoader.load(MTDL_Project.class.getResource("AllCountriesMod.fxml")));
        stage.setScene(back);
        stage.setTitle("[MODERATING] All countries");
    }
}