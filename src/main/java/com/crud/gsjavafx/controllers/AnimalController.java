package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalService;
import com.crud.gsjavafx.models.RescueAnimal;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Provides form functionality for adding or editing {@link RescueAnimal} instances.
 */
public class AnimalController implements Initializable {
    @FXML private GridPane grid;
    @FXML Button saveButton;
    @FXML TextField animalName;
    @FXML TextField animalType;
    @FXML ChoiceBox<String> gender;
    @FXML Spinner<Integer> age;
    @FXML Spinner<Integer> weight;
    @FXML DatePicker acquisitionDate;
    @FXML TextField animalLocation;
    @FXML Spinner<Integer> trainingStatus;
    @FXML CheckBox reserved;
    private RescueAnimal selectedAnimal;
    private final AnimalService animalService;
    /**
     * Stores input validation status for all form fields (Spinners, TextFields, DatePicker).
     */
    private final ArrayList<InputValidationController<Node>> nodes = new ArrayList<>();

    /**
     * Constructs the controller with the injected {@link AnimalService} for CRUD management and
     * UI synchronization.
     *
     * @param animalService the service layer for managing rescue animals.
     */
    @Inject
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * Sets the animal instance to be edited.
     *
     * @param selectedAnimal the animal selected for editing.
     */
    public void setSelectedAnimal(RescueAnimal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
    }

    /**
     * Sets up input validation controllers for all {@link Spinner}, {@link TextField}, and {@link DatePicker}
     * nodes within the {@code grid}.
     *
     * @param url the location used to resolve relative paths for the root object.
     * @param resourceBundle the resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Node node : grid.getChildren()) {
            if (node instanceof Spinner) {
                InputValidationController<Node> fieldController = new InputValidationController<>((Spinner<Integer>) node);
                nodes.add(fieldController);
            } else if (node instanceof TextField) {
                InputValidationController<Node> fieldController = new InputValidationController<>((TextField) node);
                nodes.add(fieldController);
            } else if (node instanceof DatePicker) {
                InputValidationController<Node> fieldController = new InputValidationController<>((DatePicker) node);
                nodes.add(fieldController);
            }
        }
    }

    /**
     * Populates the form fields with the values of the {@code selectedAnimal}.
     */
    public void setFields() {
        final int maxAge = 100;
        final int maxWeight = 999;
        final int maxTrainingLevel = 5;
        animalName.setText(selectedAnimal.getName());
        animalType.setText(selectedAnimal.getAnimalType());
        gender.setValue(selectedAnimal.getGender());
        age.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(selectedAnimal.getAge(),
                maxAge));
        weight.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(selectedAnimal.getWeight(),
                maxWeight));
        acquisitionDate.setValue(selectedAnimal.getAcquisitionDate().toLocalDate());
        animalLocation.setText(selectedAnimal.getLocation());
        trainingStatus.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                selectedAnimal.getTrainingStatus(), maxTrainingLevel));
        reserved.setSelected(selectedAnimal.getReserved());
    }

    /**
     * Validates all form fields before attempting to save.
     * If validation passes, the {@code saveAnimal} method is called to persist the data.
     */
    public void save() {
        for (InputValidationController<Node> node : nodes) {
            if (node.getError()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Save Failed");
                alert.setHeaderText("Please review the form and try again.");
                alert.setContentText("Ensure all fields are set and contain valid data.");
                alert.show();
                return;
            }
        }
        saveAnimal();
    }

    /**
     * Saves the {@code RescueAnimal} data by either updating an existing record or inserting a new one.
     * If {@code selectedAnimal} is not {@code null}, updates the existing animal using {@code animalService.doUpdate}.
     * Otherwise, creates a new {@code RescueAnimal} instance and inserts it using {@code animalService.doInsert}.
     */
    public void saveAnimal() {
        if (selectedAnimal != null) {
            animalService.doUpdate(
                    selectedAnimal,
                    animalName.getText(),
                    animalType.getText(),
                    gender.getValue(),
                    age.getValue(),
                    weight.getValue(),
                    Date.valueOf(acquisitionDate.getValue()),
                    animalLocation.getText(),
                    trainingStatus.getValue(),
                    reserved.isSelected()
            );
        } else {
            RescueAnimal newAnimal = new RescueAnimal(
                    animalName.getText(),
                    animalType.getText(),
                    gender.getValue(),
                    age.getValue(),
                    weight.getValue(),
                    Date.valueOf(acquisitionDate.getValue()),
                    animalLocation.getText(),
                    trainingStatus.getValue(),
                    reserved.isSelected()
            );
            animalService.doInsert(newAnimal);

        }
        closeWindow();
    }

    /**
     * Closes the current window containing the form.
     */
    public void closeWindow() {
        Window window = animalName.getScene().getWindow();
        window.hide();
    }
}
