package com.crud.gsjavafx;

import com.crud.gsjavafx.controllers.MainViewController;
import com.crud.gsjavafx.utils.DependencyFactory;
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
        loader.setControllerFactory(c -> new MainViewController(DependencyFactory.createAnimalService()));
        Parent root = loader.load();
        primaryStage.setTitle("Grazioso Salvare - Training Rescue Animals Since 1965");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}