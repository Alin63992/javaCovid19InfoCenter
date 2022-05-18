package com.example.mtdl_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.*;

class MyCallable implements Callable<Integer> {
    public Integer call () throws IOException, SQLException {
        Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
        Statement stmt= data.createStatement();
        ResultSet rs = stmt.executeQuery("select Date_Updated from covid_cases");
        if (!rs.next()) {
            String command = "curl \"https://test.api.amadeus.com/v1/security/oauth2/token\" -H \"Content-Type: application/x-www-form-urlencoded\" -d \"grant_type=client_credentials&client_id=JGudQKMibNnIzujFTrWJdhVZpB1GgeJB&client_secret=1GgEGmzWeZ2uNfxC\"";
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader authProcessReturn = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String lineRead;
            String authToken = null;
            while ((lineRead = authProcessReturn.readLine()) != null) {
                if (lineRead.contains("\"access_token\"")) {
                    String[] temp = lineRead.split(": ");
                    authToken = temp[1].substring(1, temp[1].length() - 2);
                    break;
                }
            }
            authProcessReturn.close();
            String[] locales= {"AL", "AD", "AT", "BY", "BE", "BA", "BG", "HR", "CZ", "DK", "EE", "FI", "FR", "DE", "GR", "VA", "HU", "IS", "IE", "IT", "LV", "LI", "LT", "LU", "MT", "MD", "MC", "ME", "NL", "MK", "NO", "PL", "PT", "RO", "RU", "SM", "RS", "SK", "SI", "ES", "SE", "CH", "UA", "GB"};
            String countryInLocale;
            int i=0;
            while (i < locales.length) {
                countryInLocale = locales[i];
                System.out.println(countryInLocale);
                String getInfoCommand = "curl -X GET \"https://test.api.amadeus.com/v1/duty-of-care/diseases/covid19-area-report?countryCode=" + countryInLocale + "\" -H \"Authorization: Bearer " + authToken + "\"";
                Locale obj = new Locale("", countryInLocale);
                String Country_Name = obj.getDisplayCountry();
                String Date_Updated = LocalDate.now().toString();
                String Total_Confirmed_Cases="unknown";
                String Active_Cases="unknown";
                String Cured_Cases="unknown";
                String Total_Deaths="unknown";
                String PCR_Test_Required="unknown";
                String Mobile_Tracing_App="unknown";
                String quarantine="unknown";
                String mask="unknown";
                String vaccine="unknown";
                String curfew="unknown";
                boolean error=false;
                p = Runtime.getRuntime().exec(getInfoCommand);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((lineRead = in.readLine()) != null) {
                    if (lineRead.equals("{") || lineRead.contains("}") || lineRead.contains("iataCode") || lineRead.contains("\"date\"") || lineRead.contains("areaType") || lineRead.contains("\"links\"") || lineRead.contains("meta") || lineRead.contains("\"self\" :") || lineRead.contains("\"data\"") || lineRead.contains("\"area\"") || lineRead.contains("\"name\" :") || lineRead.contains("\"summary\"") || lineRead.contains("diseaseRiskLevel") || lineRead.contains("diseaseInfection") || lineRead.contains("\"level\" :") || lineRead.contains("\"rate\"") || lineRead.contains("diseaseCases") || lineRead.contains("dataSources") || lineRead.contains("covidDashboardLink") || lineRead.contains("heathDepartementSiteLink") || lineRead.contains("governmentSiteLink") || lineRead.contains("areaRestrictions") || lineRead.contains("\"text\"") || lineRead.contains("areaAccessRestrictions") || lineRead.contains("declarationDocuments") || lineRead.contains("documentRequired") || lineRead.contains("\"entry\"") || lineRead.contains("\"ban\"") || lineRead.contains("\"rules\"") || lineRead.contains("borderBan") || lineRead.contains("borderType") || lineRead.contains("\"status\"") || lineRead.contains("minimumAge") || lineRead.contains("testType") || lineRead.contains("validityPeriod") || lineRead.contains("\"delay\"") || lineRead.contains("\"exit\"") || lineRead.contains("specialRequirements") || lineRead.contains("isBanned") || lineRead.contains("otherRestriction") || lineRead.contains("referenceLink") || lineRead.contains("qualifiedVaccines") || lineRead.contains("areaPolicy") || lineRead.contains("startDate") || lineRead.contains("endDate") || lineRead.contains("relatedArea") || lineRead.contains("\"methods\"") || lineRead.contains("\"rel\"") || lineRead.contains("\"areaVaccinated\"") || lineRead.contains("\"type\"")){
                        continue;
                    }
                    if (lineRead.contains("\"active\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Active_Cases = parts[1].substring(0, parts[1].length() - 1);
                        else Active_Cases = parts[1];
                    } else if (lineRead.contains("\"confirmed\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Total_Confirmed_Cases = parts[1].substring(0, parts[1].length() - 1);
                        else Total_Confirmed_Cases = parts[1];
                    } else if (lineRead.contains("\"deaths\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Total_Deaths = parts[1].substring(0, parts[1].length() - 1);
                        else Total_Deaths = parts[1];
                    } else if (lineRead.contains("\"recovered\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Cured_Cases = parts[1].substring(0, parts[1].length() - 1);
                        else Cured_Cases = parts[1];
                    }
                    else if(lineRead.contains("\"diseaseTesting\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                PCR_Test_Required="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                PCR_Test_Required="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"tracingApplication\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                Mobile_Tracing_App="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                Mobile_Tracing_App="false";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("Recommended")){
                                Mobile_Tracing_App="Recommended";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"quarantineModality\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"eligiblePerson\"") && !lineRead.contains("None")){
                                String[] parts=lineRead.split(" : ");
                                quarantine=parts[1].substring(1, parts[1].length()-2);
                                break;
                            }
                            else if(lineRead.contains("\"eligiblePerson\"") && lineRead.contains("None")){
                                quarantine="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"mask\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                mask="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                mask="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"diseaseVaccination\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                vaccine="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                vaccine="false";
                                break;
                            }
                        }
                    }
                    else if (lineRead.contains("\"curfew\"")) {
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                curfew="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                curfew="false";
                                break;
                            }
                        }
                    }
                    else if (lineRead.contains("\"errors\"")) error=true;
                }
                if (error!=false) {
                    try {
                        stmt.executeUpdate("insert into covid_cases values ('" + Date_Updated + "', '" + Country_Name + "', '" + Total_Confirmed_Cases + "', '" + Active_Cases + "', '" + Cured_Cases + "', '" + Total_Deaths + "', '" + PCR_Test_Required + "', '" + Mobile_Tracing_App + "', '" + quarantine + "', '" + mask + "', '" + vaccine + "', '" + curfew +"')");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                i++;
            }
            System.out.println("Done");
            p.destroy();
        } else if (!rs.getString("Date_Updated").matches(LocalDate.now().toString())) {
            String command = "curl \"https://test.api.amadeus.com/v1/security/oauth2/token\" -H \"Content-Type: application/x-www-form-urlencoded\" -d \"grant_type=client_credentials&client_id=JGudQKMibNnIzujFTrWJdhVZpB1GgeJB&client_secret=1GgEGmzWeZ2uNfxC\"";
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader authProcessReturn = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String lineRead;
            String authToken = null;
            while ((lineRead = authProcessReturn.readLine()) != null) {
                if (lineRead.contains("\"access_token\"")) {
                    String[] temp = lineRead.split(": ");
                    authToken = temp[1].substring(1, temp[1].length() - 2);
                    break;
                }
            }
            authProcessReturn.close();
            String[] locales= {"AL", "AD", "AT", "BY", "BE", "BA", "BG", "HR", "CZ", "DK", "EE", "FI", "FR", "DE", "GR", "VA", "HU", "IS", "IE", "IT", "LV", "LI", "LT", "LU", "MT", "MD", "MC", "ME", "NL", "MK", "NO", "PL", "PT", "RO", "RU", "SM", "RS", "SK", "SI", "ES", "SE", "CH", "UA", "GB"};
            String countryInLocale;
            int i=0;
            while (i < locales.length) {
                countryInLocale=locales[i];
                System.out.println(countryInLocale);
                String getInfoCommand = "curl -X GET \"https://test.api.amadeus.com/v1/duty-of-care/diseases/covid19-area-report?countryCode=" + countryInLocale + "\" -H \"Authorization: Bearer " + authToken + "\"";
                Locale obj = new Locale("", countryInLocale);
                String Date_Updated = LocalDate.now().toString();
                String Country_Name = obj.getDisplayCountry();
                String Total_Confirmed_Cases="unknown";
                String Active_Cases="unknown";
                String Cured_Cases="unknown";
                String Total_Deaths="unknown";
                String PCR_Test_Required="unknown";
                String Mobile_Tracing_App="unknown";
                String quarantine="unknown";
                String mask="unknown";
                String vaccine="unknown";
                String curfew="unknown";
                boolean error=false;
                p = Runtime.getRuntime().exec(getInfoCommand);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((lineRead = in.readLine()) != null) {
                    if (lineRead.equals("{") || lineRead.contains("}") || lineRead.contains("iataCode") || lineRead.contains("\"date\"") || lineRead.contains("areaType") || lineRead.contains("\"links\"") || lineRead.contains("meta") || lineRead.contains("\"self\" :") || lineRead.contains("\"data\"") || lineRead.contains("\"area\"") || lineRead.contains("\"name\" :") || lineRead.contains("\"summary\"") || lineRead.contains("diseaseRiskLevel") || lineRead.contains("diseaseInfection") || lineRead.contains("\"level\" :") || lineRead.contains("\"rate\"") || lineRead.contains("diseaseCases") || lineRead.contains("dataSources") || lineRead.contains("covidDashboardLink") || lineRead.contains("heathDepartementSiteLink") || lineRead.contains("governmentSiteLink") || lineRead.contains("areaRestrictions") || lineRead.contains("\"text\"") || lineRead.contains("areaAccessRestrictions") || lineRead.contains("declarationDocuments") || lineRead.contains("documentRequired") || lineRead.contains("\"entry\"") || lineRead.contains("\"ban\"") || lineRead.contains("\"rules\"") || lineRead.contains("borderBan") || lineRead.contains("borderType") || lineRead.contains("\"status\"") || lineRead.contains("minimumAge") || lineRead.contains("testType") || lineRead.contains("validityPeriod") || lineRead.contains("\"delay\"") || lineRead.contains("\"exit\"") || lineRead.contains("specialRequirements") || lineRead.contains("isBanned") || lineRead.contains("otherRestriction") || lineRead.contains("referenceLink") || lineRead.contains("qualifiedVaccines") || lineRead.contains("areaPolicy") || lineRead.contains("startDate") || lineRead.contains("endDate") || lineRead.contains("relatedArea") || lineRead.contains("\"methods\"") || lineRead.contains("\"rel\"") || lineRead.contains("\"areaVaccinated\"") || lineRead.contains("\"type\"")){
                        continue;
                    }
                    if (lineRead.contains("\"active\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Active_Cases = parts[1].substring(0, parts[1].length() - 1);
                        else Active_Cases = parts[1];
                    } else if (lineRead.contains("\"confirmed\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Total_Confirmed_Cases = parts[1].substring(0, parts[1].length() - 1);
                        else Total_Confirmed_Cases = parts[1];
                    } else if (lineRead.contains("\"deaths\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Total_Deaths = parts[1].substring(0, parts[1].length() - 1);
                        else Total_Deaths = parts[1];
                    } else if (lineRead.contains("\"recovered\" :")) {
                        String[] parts = lineRead.split(": ");
                        if (parts[1].contains(","))
                            Cured_Cases = parts[1].substring(0, parts[1].length() - 1);
                        else Cured_Cases = parts[1];
                    }
                    else if(lineRead.contains("\"diseaseTesting\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                PCR_Test_Required="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                PCR_Test_Required="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"tracingApplication\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                Mobile_Tracing_App="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                Mobile_Tracing_App="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"quarantineModality\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"eligiblePerson\"") && !lineRead.contains("None")){
                                String[] parts=lineRead.split(" : ");
                                quarantine=parts[1].substring(1, parts[1].length()-2);
                                break;
                            }
                            else if(lineRead.contains("\"eligiblePerson\"") && lineRead.contains("None")){
                                quarantine="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"mask\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                mask="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                mask="false";
                                break;
                            }
                        }
                    }
                    else if(lineRead.contains("\"diseaseVaccination\"")){
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                vaccine="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                vaccine="false";
                                break;
                            }
                        }
                    }
                    else if (lineRead.contains("\"curfew\"")) {
                        while (!(lineRead = in.readLine()).contains("}")){
                            if(lineRead.contains("\"isRequired\"") && lineRead.contains("Yes")){
                                curfew="true";
                                break;
                            }
                            else if(lineRead.contains("\"isRequired\"") && lineRead.contains("No")){
                                curfew="false";
                                break;
                            }
                        }
                    }
                    else if (lineRead.contains("\"errors\"")) error=true;
                }
                if (error==false) {
                    try {
                        stmt.executeUpdate("update covid_cases set Date_Updated='" + Date_Updated + "', Total_Confirmed_Cases='" + Total_Confirmed_Cases + "', Active_Cases='" + Active_Cases + "', Cured_Cases='" + Cured_Cases + "', Total_Deaths='" + Total_Deaths + "', PCR_Test_Required='" + PCR_Test_Required + "', Mobile_Tracing_App='" + Mobile_Tracing_App + "', quarantine='" + quarantine + "', mask='" + mask + "', vaccine='" + vaccine + "', curfew='" + curfew + "' where Country_Name='" + Country_Name + "'");
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                i++;
            }
            System.out.println("Done");
            stmt.close();
            p.destroy();
        }
        return null;
    }
}

public class MTDL_Project extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Connection data = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtdl", "root", "");
            Statement stmt = data.createStatement();
            ResultSet rs=stmt.executeQuery("select Password from users where Email='testConnection'");
            if (rs.next()) {
                FXMLLoader loadLoginScreen = new FXMLLoader(MTDL_Project.class.getResource("LoginPage.fxml"));
                Scene login = new Scene(loadLoginScreen.load());
                stage.setScene(login);
                stage.setResizable(false);
                stage.show();
                stage.setTitle("Log in");
                stage.setOnCloseRequest(e -> System.exit(0));
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(new MyCallable());
            }
        } catch(SQLException | IOException a){
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            wrong.setTitle("Connection error");
            wrong.setHeaderText("We can't connect to the server");
            wrong.setContentText("Please check your internet connection and try again. If you are connected to the internet, give us a few minutes and try again.");
            wrong.show();
            wrong.setOnCloseRequest(e -> System.exit(503));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}