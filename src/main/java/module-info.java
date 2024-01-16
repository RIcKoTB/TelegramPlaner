module com.example.telegramplaner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires telegrambots.meta;
    requires telegrambots;


    opens com.example.telegramplaner to javafx.fxml;
    exports com.example.telegramplaner;
    exports com.example.telegramplaner.ui;
    opens com.example.telegramplaner.ui to javafx.fxml;
}