module com.example.mtdl_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.java;
    requires commons.validator;

    opens com.example.mtdl_project to javafx.fxml;
    exports com.example.mtdl_project;
}