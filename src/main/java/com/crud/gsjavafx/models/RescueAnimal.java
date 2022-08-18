package com.crud.gsjavafx.models;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 */
public class RescueAnimal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String animalType;
    private String gender;
    private String age;
    private String weight;
    private String acquisitionDate;
    private String location;
    private String trainingStatus;
    private String reserved;

    /**
     * Default constructor.
     *
     * @param name name of the animal.
     * @param animalType species of the animal.
     * @param gender gender of the animal.
     * @param age animal's age.
     * @param weight weight in pounds.
     * @param acquisitionDate date animal was acquired as MM/DD/YY.
     * @param location city where the animal is located.
     * @param trainingStatus level of training completed.
     * @param reserved reserve status.
     */
    public RescueAnimal(String name, String animalType, String gender, String age, String weight,
                        String acquisitionDate, String location, String trainingStatus,
                        String reserved) {
        setName(name);
        setAnimalType(animalType);
        setGender(gender);
        setAge(age);
        setWeight(weight);
        setAcquisitionDate(acquisitionDate);
        setLocation(location);
        setTrainingStatus(trainingStatus);
        setReserved(reserved);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
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
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
