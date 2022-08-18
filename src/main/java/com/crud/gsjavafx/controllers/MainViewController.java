package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.utils.FormatCell;
import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/** Controller for MainView. Handles ListView display as well as initiating actions on listView. */
public class MainViewController implements Initializable {
    @FXML Button addAnimalButton;
    @FXML Button editAnimalButton;
    @FXML private ListView<RescueAnimal> listView;
    public ArrayList<RescueAnimal> globalList = new ArrayList<RescueAnimal>();

    /** Initialize ListView with the saved ArrayList, AnimalList.allAnimals. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnimalList.initializeList();
            globalList = AnimalList.allAnimals;
            ObservableList<RescueAnimal> animalList = FXCollections.observableArrayList();
            animalList.addAll(globalList);
            listView.setItems(animalList);
            listView.setCellFactory(new Callback<ListView<RescueAnimal>, ListCell<RescueAnimal>>() {
                @Override public ListCell<RescueAnimal> call(ListView<RescueAnimal> list) {
                    return new FormatCell();
                }
            });
        } catch(Exception e) {
        }

        // Set double-click event.
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    RescueAnimal selectedAnimal = listView.getSelectionModel().getSelectedItem();
                    editAnimalWindow(selectedAnimal);
                }
            }
        });
    }

    /** Handles addAnimalButton action. */
    public void addAnimalWindow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/crud/gsjavafx/addAnimalView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Intake a New Animal:");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Handles editAnimalButton action. */
    public void getSelectedAnimal(){
        RescueAnimal animal = listView.getSelectionModel().getSelectedItem();
        editAnimalWindow(animal);
    }

    /** Opens new window to edit selected animal. */
    public void editAnimalWindow(RescueAnimal selectedAnimal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/crud/gsjavafx/editAnimalView.fxml"));
            Parent root = (Parent)loader.load();
            EditAnimalController controller = loader.<EditAnimalController>getController();
            controller.setAnimal(selectedAnimal);
            controller.setFields();
            Stage stage = new Stage();
            stage.setTitle("Edit: " + selectedAnimal.getName());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
