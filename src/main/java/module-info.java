module com.example.cryptodataapp {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires org.json;
    requires com.jfoenix;

    opens com.example.cryptodataapp to javafx.fxml;
    exports com.example.cryptodataapp;
    exports controllers;
    opens controllers to javafx.fxml;
}