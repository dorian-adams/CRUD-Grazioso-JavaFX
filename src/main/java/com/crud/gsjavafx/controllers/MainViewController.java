package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for MainView. Handles ListView display as well as initiating actions on listView. */
public class MainViewController implements Initializable {
    @FXML private TableView<RescueAnimal> tableView;
    @FXML private TableColumn<RescueAnimal, String> colName;
    @FXML private TableColumn<RescueAnimal, String> colSpecies;
    @FXML private TableColumn<RescueAnimal, String> colLocation;

    /** Initialize TableView with the saved ArrayList, AnimalList.allAnimals. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnimalList.initializeList();
            colName.setCellValueFactory(data -> data.getValue().animalNameProperty());
            colSpecies.setCellValueFactory(data -> data.getValue().animalSpeciesProperty());
            colLocation.setCellValueFactory(data -> data.getValue().locationProperty());
            colName.setCellFactory(TextFieldTableCell.forTableColumn());
            colSpecies.setCellFactory(TextFieldTableCell.forTableColumn());
            colLocation.setCellFactory(TextFieldTableCell.forTableColumn());
            tableView.setItems(AnimalList.allAnimals);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Set double-click event.
        tableView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                editAnimalWindow(getSelection());
            }
        });
    }

    public RescueAnimal getSelection() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    /** Handles action for the 'Add' Button. */
    public void addNewButton() {
        editAnimalWindow(null);
    }

    /** Handles action for the 'Edit' Button. */
    public void editButton() {
        editAnimalWindow(getSelection());
    }

    /** Opens new window to edit selected animal. */
    public void editAnimalWindow(RescueAnimal selectedAnimal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/crud/gsjavafx/addAnimalView.fxml"));
            Parent root = loader.load();
            if (selectedAnimal != null) {
                AddAnimalController controller = loader.getController();
                controller.setSelectedAnimal(selectedAnimal);
                controller.setFields();
            }
            Stage stage = new Stage();
            if (selectedAnimal != null) {
                stage.setTitle("Updating: " + selectedAnimal.getName());
            } else {
                stage.setTitle("Add New Animal to Record:");
            }
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
        alert.setContentText("Click 'OK' to continue or 'Cancel' to cancel this request.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            AnimalList.allAnimals.remove(selectedAnimal);
            AnimalList.saveAnimalList();
        } else {
            alert.close();
        }
    }
}