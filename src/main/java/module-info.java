module phonebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;
    requires slf4j.api;
    requires commons.validator;

    opens hu.unideb.inf to javafx.fxml, gson, slf4j.api;
    exports hu.unideb.inf;
}