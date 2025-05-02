package com.example.gsjavafx.controllers;

import com.example.gsjavafx.models.AnimalService;
import com.example.gsjavafx.models.RescueAnimal;
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
import java.util.ResourceBundle;

/**
 * Controller class for the application's primary UI.
 * Manages the display and interaction logic for the {@link TableView} containing {@link RescueAnimal} entries.
 */
public class MainViewController implements Initializable {
    @FXML private TableView<RescueAnimal> tableView;
    @FXML private TableColumn<RescueAnimal, String> colName;
    @FXML private TableColumn<RescueAnimal, String> colSpecies;
    @FXML private TableColumn<RescueAnimal, String> colLocation;
    private final AnimalService animalService;
    private final DependencyInjector injector;

    /**
     * Constructs the controller with injected dependencies.
     *
     * @param animalService the service layer for managing rescue animal data.
     * @param injector the dependency injector to pass the same instance to other controllers.
     */
    @Inject
    public MainViewController(AnimalService animalService, DependencyInjector injector) {
        this.animalService = animalService;
        this.injector = injector;
    }

    /**
     * Initializes the {@link TableView} by setting up column cell factories and
     * populating the table with data via {@link AnimalService}.
     */
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

        final int DOUBLE_CLICK = 2;
        tableView.setOnMouseClicked(click -> {
            if (click.getClickCount() == DOUBLE_CLICK) {
                editAnimalWindow(getSelection());
            }
        });
    }

    /**
     * Returns the currently selected {@link RescueAnimal} from the table view.
     *
     * @return the selected {@link RescueAnimal}, or {@code null} if no selection is made.
     */
    public RescueAnimal getSelection() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    /**
     * Handles action for the Add Button.
     * Opens the animal editing window to create a new entry (no pre-selected animal).
     */
    public void addNewButton() {
        editAnimalWindow(null);
    }

    /**
     * Handles action for the Edit Button.
     * Opens the animal editing window to modify the selected entry.
     */
    public void editButton() {
        editAnimalWindow(getSelection());
    }

    /**
     * Opens the animal editing window.
     *
     * @param selectedAnimal the {@link RescueAnimal} to edit, or {@code null} to create a new entry.
     */
    public void editAnimalWindow(RescueAnimal selectedAnimal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gsjavafx/animalView.fxml"));
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

    /**
     * Prompts the user for confirmation and deletes the selected animal if confirmed.
     */
    public void deleteSelection() {
        RescueAnimal selectedAnimal = getSelection();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion:");
        alert.setHeaderText("Warning: Clicking 'OK' will remove " + selectedAnimal.getName() + " from record.");
        alert.setContentText("Click 'OK' to continue or 'Cancel' to cancel this request.");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> animalService.doDelete(selectedAnimal));
    }
}