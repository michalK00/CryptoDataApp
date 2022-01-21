module com.example.cryptodataapp {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires org.json;
    requires com.jfoenix;

    opens cryptodataapp to javafx.fxml;
    exports cryptodataapp;
    exports controllers;
    opens controllers to javafx.fxml;
}