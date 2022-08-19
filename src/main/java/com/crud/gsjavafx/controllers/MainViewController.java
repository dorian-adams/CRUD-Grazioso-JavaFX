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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for MainView. Handles ListView display as well as initiating actions on listView. */
public class MainViewController implements Initializable {
    @FXML private ListView<RescueAnimal> listView;
    public ArrayList<RescueAnimal> globalList = new ArrayList<>();

    /** Initialize ListView with the saved ArrayList, AnimalList.allAnimals. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnimalList.initializeList();
            globalList = AnimalList.allAnimals;
            ObservableList<RescueAnimal> animalList = FXCollections.observableArrayList();
            animalList.addAll(globalList);
            listView.setItems(animalList);
            listView.setCellFactory(new Callback<>() {
                @Override public ListCell<RescueAnimal> call(ListView<RescueAnimal> list) {
                    return new FormatCell();
                }
            });
        } catch(Exception e) {
        }

        // Set double-click event.
        listView.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    editAnimalWindow(getSelection());
                }
            }
        });
    }

    public RescueAnimal getSelection() {
        return listView.getSelectionModel().getSelectedItem();
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
    public void animalToEdit(){
        editAnimalWindow(getSelection());
    }

    /** Opens new window to edit selected animal. */
    public void editAnimalWindow(RescueAnimal selectedAnimal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/crud/gsjavafx/editAnimalView.fxml"));
            Parent root = loader.load();
            EditAnimalController controller = loader.getController();
            controller.setAnimal(selectedAnimal);
            controller.setFields();
            Stage stage = new Stage();
            stage.setTitle("Update: " + selectedAnimal.getName());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Deletes selected animal after confirmation. */
    public void deleteSelection() {
        RescueAnimal selectedAnimal = getSelection();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion:");
        alert.setHeaderText("Warning: Clicking 'OK' will remove " + selectedAnimal.getName() + " from record.");
        alert.setContentText("Click 'OK' to continue or 'cancel' to cancel this request.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            AnimalList.allAnimals.remove(selectedAnimal);
            AnimalList.saveAnimalList();
        } else {
            alert.close();
        }
    }
}
