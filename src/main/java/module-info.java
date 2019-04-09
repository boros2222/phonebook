module phonebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;

    opens hu.unideb.inf to javafx.fxml;
    exports hu.unideb.inf;
}