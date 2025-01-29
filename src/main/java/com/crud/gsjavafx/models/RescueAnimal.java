package com.crud.gsjavafx.models;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 */
public class RescueAnimal implements Serializable {
    transient private SimpleStringProperty animalName;
    transient private SimpleStringProperty animalSpecies;
    transient private SimpleStringProperty location;
    private String gender;
    private int age;
    private int weight;
    private Date acquisitionDate;
    private int trainingStatus;
    private boolean reserved;
    private int id;

    /**
     * Default constructor.
     *
     * @param name name of the animal.
     * @param species species of the animal.
     * @param gender gender of the animal.
     * @param age animal's age.
     * @param weight animal's weight in pounds.
     * @param acquisitionDate date animal was acquired as MM/DD/YY.
     * @param location city where the animal is located.
     * @param trainingStatus level of training completed.
     * @param reserved reserve status.
     */
    public RescueAnimal(String name, String species, String gender, int age, int weight,
                        Date acquisitionDate, String location, int trainingStatus,
                        boolean reserved) {
        // SimpleStringProperty.
        this.animalName = new SimpleStringProperty(name);
        this.animalSpecies = new SimpleStringProperty(species);
        this.location = new SimpleStringProperty(location);
        // Standard fields not used in TableView.
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.acquisitionDate = acquisitionDate;
        this.trainingStatus = trainingStatus;
        this.reserved = reserved;
    }

    public RescueAnimal(String name, String species, String gender, int age, int weight,
                        Date acquisitionDate, String location, int trainingStatus,
                        boolean reserved, int id) {
        this(name, species, gender, age, weight, acquisitionDate, location, trainingStatus, reserved);
        this.id = id;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        out.writeObject(animalName.get());
        out.writeObject(animalSpecies.get());
        out.writeObject(location.get());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        String name = (String) in.readObject();
        String species = (String) in.readObject();
        String loc = (String) in.readObject();

        animalName = new SimpleStringProperty(name);
        animalSpecies = new SimpleStringProperty(species);
        location = new SimpleStringProperty(loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalName, animalSpecies, location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RescueAnimal animal = (RescueAnimal) obj;
        return animalName.get().equals(animal.animalName.get()) &&
                animalSpecies.get().equals(animal.animalSpecies.get()) &&
                location.get().equals(animal.location.get());
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

    // Standard getters and setters.
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
