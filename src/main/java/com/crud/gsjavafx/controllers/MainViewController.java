package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalService;
import com.crud.gsjavafx.models.RescueAnimal;
import dev.mccue.feather.DependencyInjector;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private final AnimalService animalService;
    private FXMLLoader loader;
    private final DependencyInjector injector;

    @Inject
    public MainViewController(AnimalService animalService, DependencyInjector injector) {
        this.animalService = animalService;
        this.injector = injector;
    }

    /** Initialize TableView. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            colName.setCellValueFactory(data -> data.getValue().animalNameProperty());
            colSpecies.setCellValueFactory(data -> data.getValue().animalSpeciesProperty());
            colLocation.setCellValueFactory(data -> data.getValue().locationProperty());
            colName.setCellFactory(TextFieldTableCell.forTableColumn());
            colSpecies.setCellFactory(TextFieldTableCell.forTableColumn());
            colLocation.setCellFactory(TextFieldTableCell.forTableColumn());
            tableView.setItems(animalService.getAllAnimals());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/crud/gsjavafx/animalView.fxml"));
            loader.setControllerFactory(injector::instance);
            Parent root = loader.load();
            if (selectedAnimal != null) {
                AnimalController controller = loader.getController();
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
            animalService.doDelete(selectedAnimal);
        } else {
            alert.close();
        }
    }
}