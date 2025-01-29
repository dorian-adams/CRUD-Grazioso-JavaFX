package com.crud.gsjavafx;

import com.crud.gsjavafx.models.AnimalService;
import com.crud.gsjavafx.utils.RescueAnimalDAO;
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

        RescueAnimalDAO.getConnection();
        if (!RescueAnimalDAO.tableExists()) {
            RescueAnimalDAO.createTable();
        }

        Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        primaryStage.setTitle("Grazioso Salvare - Training Rescue Animals Since 1965");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}