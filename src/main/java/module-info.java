module com.crud.gsjavafx {
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

    opens com.crud.gsjavafx to javafx.fxml;
    exports com.crud.gsjavafx;
    exports com.crud.gsjavafx.controllers;
    opens com.crud.gsjavafx.controllers to javafx.fxml;
    exports com.crud.gsjavafx.config;
    opens com.crud.gsjavafx.config to javafx.fxml;
    exports com.crud.gsjavafx.dao;
    opens com.crud.gsjavafx.dao to javafx.fxml;
}