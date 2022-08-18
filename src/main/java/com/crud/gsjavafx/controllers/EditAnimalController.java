package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.RescueAnimal;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/** Controller for editAnimalWindow() of MainViewController. Handles editing of existing animal. */
public class EditAnimalController {
    public RescueAnimal animal;
    //@FXML Button saveButton;
    @FXML TextField animalName;
    @FXML TextField species;
    @FXML ChoiceBox gender;
    @FXML TextField age;
    @FXML TextField weight;
    @FXML TextField acquisitionDate;
    @FXML TextField animalLocation;
    @FXML TextField trainingStatus;
    @FXML TextField reserved;

    /** Populates fields with the passed instance. */
    public void setFields() {
        animalName.setText(animal.getName());
        species.setText(animal.getAnimalType());
    }

    public void setAnimal(RescueAnimal animal) {
        this.animal = animal;
    }
}
