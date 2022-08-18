package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/** Controller for editAnimalWindow() of MainViewController. Handles editing of existing animal. */
public class EditAnimalController {
    @FXML private TextField animalName;
    @FXML private TextField species;
    @FXML private ChoiceBox<String> gender;
    @FXML private TextField age;
    @FXML private TextField weight;
    @FXML private TextField acquisitionDate;
    @FXML private TextField animalLocation;
    @FXML private TextField trainingStatus;
    @FXML private TextField reserved;
    private RescueAnimal animal;

    /** Allows MainViewController to set the instance that's being edited. */
    public void setAnimal(RescueAnimal animal) {
        this.animal = animal;
    }

    /** Populates fields with the passed instance. */
    public void setFields() {
        animalName.setText(animal.getName());
        species.setText(animal.getAnimalType());
        gender.setValue(animal.getGender());
        age.setText(animal.getAge());
        weight.setText(animal.getWeight());
        acquisitionDate.setText(animal.getAcquisitionDate());
        animalLocation.setText(animal.getLocation());
        trainingStatus.setText(animal.getTrainingStatus());
        reserved.setText(animal.getReserved());
    }

    /** Update animal and AnimalList. */
    public void updateAnimal() {
        animal.setName(animalName.getText());
        animal.setAnimalType(species.getText());
        animal.setGender(gender.getValue());
        animal.setAge(age.getText());
        animal.setWeight(weight.getText());
        animal.setAcquisitionDate(acquisitionDate.getText());
        animal.setLocation(animalLocation.getText());
        animal.setTrainingStatus(trainingStatus.getText());
        animal.setReserved(reserved.getText());

        AnimalList.saveAnimalList();

        closeWindow();
    }

    public void closeWindow() {
        Window window = animalName.getScene().getWindow();
        window.hide();
    }
}
