package com.crud.gsjavafx.models;

import javafx.beans.property.SimpleStringProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 *
 */
public class RescueAnimal implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    transient private SimpleStringProperty animalName;
    transient private SimpleStringProperty animalSpecies;
    transient private SimpleStringProperty location;
    private String serializableName;
    private String serializableSpecies;
    private String gender;
    private String age;
    private String weight;
    private String acquisitionDate;
    private String serializableLocation;
    private String trainingStatus;
    private String reserved;

    /**
     * Default constructor.
     *
     * @param name name of the animal.
     * @param species species of the animal.
     * @param gender gender of the animal.
     * @param age animal's age.
     * @param weight weight in pounds.
     * @param acquisitionDate date animal was acquired as MM/DD/YY.
     * @param location city where the animal is located.
     * @param trainingStatus level of training completed.
     * @param reserved reserve status.
     */
    public RescueAnimal(String name, String species, String gender, String age, String weight,
                        String acquisitionDate, String location, String trainingStatus,
                        String reserved) {
        // SimpleStringProperty.
        this.animalName = new SimpleStringProperty(name);
        this.animalSpecies = new SimpleStringProperty(species);
        this.location = new SimpleStringProperty(location);
        // String substitutes for SimpleStringProperty for serialization purposes.
        this.serializableName = getName();
        this.serializableSpecies = getAnimalType();
        this.serializableLocation = getLocation();
        // Standard String fields not used in TableView.
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.acquisitionDate = acquisitionDate;
        this.trainingStatus = trainingStatus;
        this.reserved = reserved;
    }

    // Retrieve SimpleStringProperty:
    public SimpleStringProperty animalNameProperty() {
        return animalName;
    }

    public SimpleStringProperty animalSpeciesProperty() {
        return animalSpecies;
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    // Getters and setters for String substitutes for SimpleStringProperty.
    public String getSerializableName() {
        return serializableName;
    }

    public void setSerializableName(String name) {
        this.serializableName = name;
    }

    public String getSerializableSpecies() {
        return serializableSpecies;
    }

    public void setSerializableSpecies(String species) {
        this.serializableSpecies = species;
    }

    public String getSerializableLocation() {
        return serializableLocation;
    }

    public void setSerializableLocation(String location) {
        this.serializableLocation = location;
    }

    // Standard getters and setters.
    public String getName() {
        return animalName.get();
    }

    public void setName(String name) {
        animalName.set(name);
    }

    public String getAnimalType() {
        return animalSpecies.get();
    }

    public void setAnimalType(String species) {
        animalSpecies.set(species);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }
}
