package com.crud.gsjavafx.models;

import com.crud.gsjavafx.utils.RescueAnimalDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

/**
 * Interfaces with the DAO and manages ObservableList<RescueAnimal>,
 * which is required for the TableView in MainViewController.
 */
public final class AnimalService {
    private final RescueAnimalDAO animalDAO;
    private final ObservableList<RescueAnimal> allAnimals = FXCollections.observableArrayList();
    private static boolean dataLoaded = false;

    public AnimalService(final RescueAnimalDAO dao) {
        if (dao == null) {
            throw new IllegalArgumentException("dao must not be null.");
        }
        this.animalDAO = dao;
        animalDAO.getConnection();

        if (!dataLoaded) {
            loadFromDb();
        }
    }

    /** Populate allAnimals from the database. */
    private void loadFromDb() {
        allAnimals.addAll(animalDAO.getAll());
        dataLoaded = true;
    }

    /** Remove from list and delete from database */
    public void doDelete(RescueAnimal animal) {
        animalDAO.delete(animal);
        allAnimals.remove(animal);
    }

    public void doInsert(RescueAnimal animal) {
        int newAnimalId = animalDAO.insert(animal);
        if (newAnimalId != 0) {
            animal.setId(newAnimalId);
            allAnimals.add(animal);
        }
    }

    public void doUpdate(RescueAnimal animal, String name, String species, String gender, int age, int weight,
                                Date acquisitionDate, String location, int trainingStatus,
                                boolean reserved) {
        animal.setName(name);
        animal.setAnimalType(species);
        animal.setGender(gender);
        animal.setAge(age);
        animal.setWeight(weight);
        animal.setAcquisitionDate(acquisitionDate);
        animal.setLocation(location);
        animal.setTrainingStatus(trainingStatus);
        animal.setReserved(reserved);
        animalDAO.update(animal);
    }

    public ObservableList<RescueAnimal> getAllAnimals() {
        return allAnimals;
    }
}
