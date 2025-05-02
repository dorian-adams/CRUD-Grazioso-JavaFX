package com.example.gsjavafx;

import com.example.gsjavafx.config.DIModule;
import dev.mccue.feather.DependencyInjector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {
    @Override
    public void stop() {}

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        DIModule module = new DIModule(loader);
        DependencyInjector injector = DependencyInjector.builder()
                .module(module)
                .build();
        loader.setControllerFactory(injector::instance);
        Parent root = loader.load();
        primaryStage.setTitle("Grazioso Salvare - Training Rescue Animals Since 1965");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}