module phonebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;
    requires slf4j.api;
    requires log4j.slf4j.impl;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens hu.unideb.inf to javafx.fxml, gson, slf4j.api;
    exports hu.unideb.inf;
}