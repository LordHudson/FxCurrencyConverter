module com.example.fxcurrencyconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.fxcurrencyconverter to javafx.fxml;
    exports com.example.fxcurrencyconverter;
}