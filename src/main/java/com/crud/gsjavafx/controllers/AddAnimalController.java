package com.crud.gsjavafx.controllers;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/** Create and add animal to AnimalList.allAnimals then serialize. */
public class AddAnimalController {
    @FXML Button saveButton;
    @FXML TextField animalName;
    @FXML TextField animalType;
    @FXML ChoiceBox gender;
    @FXML TextField age;
    @FXML TextField weight;
    @FXML TextField acquisitionDate;
    @FXML TextField animalLocation;
    @FXML TextField trainingStatus;
    @FXML TextField reserved;

    public void saveAnimal() {
        RescueAnimal newAnimal = new RescueAnimal(animalName.getText(), animalType.getText(), gender.getValue().toString(),
                age.getText(), weight.getText(), acquisitionDate.getText(),
                animalLocation.getText(), trainingStatus.getText(), reserved.getText());

        // Add new instance and serialize/save the updated list.
        AnimalList.setAllAnimals(newAnimal);
        AnimalList.saveAnimalList();
    }
}
