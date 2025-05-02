package com.crud.gsjavafx.models;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a rescue animal with identifying details, training status, and reservation state.
 * This class supports JavaFX {@code TableView} through use of {@code SimpleStringProperty}.
 */
public class RescueAnimal {
    private final SimpleStringProperty animalName;
    private final SimpleStringProperty animalSpecies;
    private final SimpleStringProperty location;
    private String gender;
    private int age;
    private int weight;
    private Date acquisitionDate;
    private int trainingStatus;
    private boolean reserved;
    private int id;

    /**
     * Constructs a {@code RescueAnimal} with the provided attribute values.
     *
     * @param name name of the animal.
     * @param species species of the animal.
     * @param gender gender of the animal.
     * @param age animal's age.
     * @param weight animal's weight in pounds.
     * @param acquisitionDate date animal was acquired.
     * @param location city where the animal is located.
     * @param trainingStatus an integer from 0 to 5 representing the level of training completed.
     * @param reserved whether the animal is currently reserved.
     */
    public RescueAnimal(String name, String species, String gender, int age, int weight,
                        Date acquisitionDate, String location, int trainingStatus,
                        boolean reserved) {
        // SimpleStringProperty is required for TableView bindings
        this.animalName = new SimpleStringProperty(name);
        this.animalSpecies = new SimpleStringProperty(species);
        this.location = new SimpleStringProperty(location);
        // Standard fields not bound to the TableView
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.acquisitionDate = acquisitionDate;
        this.trainingStatus = trainingStatus;
        this.reserved = reserved;
    }

    /**
     * Constructs a {@code RescueAnimal} with the provided attributes. Used when constructing from the database.
     *
     * @param name name of the animal.
     * @param species species of the animal.
     * @param gender gender of the animal.
     * @param age animal's age.
     * @param weight animal's weight in pounds.
     * @param acquisitionDate date animal was acquired.
     * @param location city where the animal is located.
     * @param trainingStatus an integer from 0 to 5 representing the level of training completed.
     * @param reserved whether the animal is currently reserved.
     * @param id the unique identifier assigned to the animal.
     */
    public RescueAnimal(String name, String species, String gender, int age, int weight,
                        Date acquisitionDate, String location, int trainingStatus,
                        boolean reserved, int id) {
        this(name, species, gender, age, weight, acquisitionDate, location, trainingStatus, reserved);
        this.id = id;
    }

    /**
     * Generates a hash code based on the animal's name, species, and location.
     *
     * @return the hash code for {@code RescueAnimal}
     */
    @Override
    public int hashCode() {
        return Objects.hash(animalName, animalSpecies, location);
    }

    /**
     * Two {@code RescueAnimal} objects are considered equal if their name, species, and location are all equal.
     *
     * @param obj the object to compare with this instance.
     * @return {@code true} if the specified object is equal to this one; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RescueAnimal animal = (RescueAnimal) obj;
        return animalName.get().equals(animal.animalName.get()) &&
                animalSpecies.get().equals(animal.animalSpecies.get()) &&
                location.get().equals(animal.location.get());
    }

    public SimpleStringProperty animalNameProperty() {
        return animalName;
    }

    public SimpleStringProperty animalSpeciesProperty() {
        return animalSpecies;
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id; }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public boolean getReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(int trainingStatus) {
        this.trainingStatus = trainingStatus;
    }
}
