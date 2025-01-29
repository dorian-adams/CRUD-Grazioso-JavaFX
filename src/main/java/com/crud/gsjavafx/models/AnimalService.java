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
    private static final AnimalService instance = new AnimalService();
    private final RescueAnimalDAO animalDAO;
    private final ObservableList<RescueAnimal> allAnimals = FXCollections.observableArrayList();

    private AnimalService() {
        this.animalDAO = new RescueAnimalDAO();
        loadFromDb();
    }

    public static AnimalService getInstance() {
        return instance;
    }

    /** Populate allAnimals from the database. */
    private void loadFromDb() {
        allAnimals.addAll(animalDAO.getAll());
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
