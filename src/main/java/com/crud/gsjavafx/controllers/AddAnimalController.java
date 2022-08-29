package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import java.net.URL;
import java.time.LocalDate;
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
        acquisitionDate.setValue(LocalDate.parse(selectedAnimal.getAcquisitionDate()));
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
        if (selectedAnimal != null) {
            selectedAnimal.setName(animalName.getText());
            selectedAnimal.setAnimalType(animalType.getText());
            selectedAnimal.setGender(gender.getValue());
            selectedAnimal.setAge(Integer.parseInt(age.getValue().toString()));
            selectedAnimal.setWeight(Integer.parseInt(weight.getValue().toString()));
            selectedAnimal.setAcquisitionDate(acquisitionDate.getValue().toString());
            selectedAnimal.setLocation(animalLocation.getText());
            selectedAnimal.setTrainingStatus(Integer.parseInt(trainingStatus.getValue().toString()));
            selectedAnimal.setReserved(reserved.isSelected());
            selectedAnimal.setSerializableName(animalName.getText());
            selectedAnimal.setSerializableSpecies(animalType.getText());
            selectedAnimal.setSerializableLocation(animalLocation.getText());
        } else {
            RescueAnimal newAnimal = new RescueAnimal(animalName.getText(), animalType.getText(),
                    gender.getValue(), Integer.parseInt(age.getValue().toString()),
                    Integer.parseInt(weight.getValue().toString()), acquisitionDate.getValue().toString(),
                    animalLocation.getText(), Integer.parseInt(trainingStatus.getValue().toString()),
                    reserved.isSelected());

            AnimalList.setAllAnimals(newAnimal);
        }
        AnimalList.saveAnimalList();
        closeWindow();
    }

    public void closeWindow() {
        Window window = animalName.getScene().getWindow();
        window.hide();
    }
}
