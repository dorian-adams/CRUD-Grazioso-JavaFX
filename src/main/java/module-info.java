module com.example.gsjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.j;
    requires com.zaxxer.hikari;
    requires dev.mccue.feather;

    opens com.example.gsjavafx to javafx.fxml;
    exports com.example.gsjavafx;
    exports com.example.gsjavafx.controllers;
    opens com.example.gsjavafx.controllers to javafx.fxml;
    exports com.example.gsjavafx.config;
    opens com.example.gsjavafx.config to javafx.fxml;
    exports com.example.gsjavafx.dao;
    opens com.example.gsjavafx.dao to javafx.fxml;
    exports com.example.gsjavafx.di;
    opens com.example.gsjavafx.di to javafx.fxml;
}