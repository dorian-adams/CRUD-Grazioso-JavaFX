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

    opens com.crud.gsjavafx to javafx.fxml;
    exports com.crud.gsjavafx;
    exports com.crud.gsjavafx.controllers;
    opens com.crud.gsjavafx.controllers to javafx.fxml;
    exports com.crud.gsjavafx.utils;
    opens com.crud.gsjavafx.utils to javafx.fxml;
}