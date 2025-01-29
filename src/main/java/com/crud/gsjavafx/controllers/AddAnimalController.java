package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalService;
import com.crud.gsjavafx.models.RescueAnimal;
import com.crud.gsjavafx.utils.RescueAnimalDAO;
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

/** Create and add animal to AnimalList.allAnimals then serialize. */
public class AddAnimalController implements Initializable {
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
    private final ArrayList<InputValidationController<Node>> nodes = new ArrayList<>();
    private final AnimalService animalService;

    public AddAnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /** Allows MainViewController to set the instance that's being edited. */
    public void setSelectedAnimal(RescueAnimal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
    }

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

    /** Populates fields with the passed instance. */
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

    /** Handles action for Save button */
    public void saveAnimal() {
        // Update existing
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
            // Create new
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

    public void closeWindow() {
        Window window = animalName.getScene().getWindow();
        window.hide();
    }

}
