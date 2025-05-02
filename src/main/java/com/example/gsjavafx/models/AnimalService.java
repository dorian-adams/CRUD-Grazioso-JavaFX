package com.example.gsjavafx.models;

import com.example.gsjavafx.controllers.MainViewController;
import com.example.gsjavafx.dao.RescueAnimalDAO;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

/**
 * Service layer that interfaces with the {@link RescueAnimalDAO} to manage rescue animal data.
 * Maintains an {@link ObservableList} of {@link RescueAnimal} instances, which supports
 * UI binding in the {@code TableView} of {@link MainViewController}.
 * <p>
 * This class is designed to load data from the database only once during the application lifecycle,
 * using the static {@code dataLoaded} flag to prevent redundant database calls across multiple
 * instantiations of the service. This approach optimizes performance and ensures consistency in the
 * shared {@code ObservableList} state.
 */
public final class AnimalService {
    private final RescueAnimalDAO animalDAO;
    private final ObservableList<RescueAnimal> allAnimals = FXCollections.observableArrayList();
    private static boolean dataLoaded = false;

    /**
     * Constructs an {@code AnimalService} with the specified {@link RescueAnimalDAO}.
     * Uses the static boolean {@code dataLoaded} to ensure that data is loaded from the database
     * only once during the application's lifecycle.
     *
     * @param dao the {@link RescueAnimalDAO} used for database operations.
     * @throws IllegalArgumentException if {@code dao} is null.
     */
    @Inject
    public AnimalService(final RescueAnimalDAO dao) {
        if (dao == null) {
            throw new IllegalArgumentException("dao must not be null.");
        }
        this.animalDAO = dao;

        if (!dataLoaded) {
            loadFromDb();
        }
    }

    /**
     * Loads all animals from the database into the {@code allAnimals} ObservableList.
     */
    private void loadFromDb() {
        allAnimals.addAll(animalDAO.getAll());
        dataLoaded = true;
    }

    /**
     * Deletes the given {@link RescueAnimal} from the database and removes it from the {@code allAnimals}
     * ObservableList.
     *
     * @param animal the {@link RescueAnimal} object to be deleted (must not be null).
     */
    public void deleteAnimal(RescueAnimal animal) {
        animalDAO.delete(animal);
        allAnimals.remove(animal);
    }

    /**
     * Inserts the given {@link RescueAnimal} into the database and, if successful, updates its ID and
     * adds it to the {@code allAnimals} ObservableList.
     *
     * @param animal the {@link RescueAnimal} object to be inserted
     */
    public void addAnimal(RescueAnimal animal) {
        final int INSERT_FAILURE = 0;  // DAO insert returns 0 on failure

        int newAnimalId = animalDAO.insert(animal);
        if (newAnimalId != INSERT_FAILURE) {
            animal.setId(newAnimalId);
            allAnimals.add(animal);
        }
    }

    /**
     * Updates the given {@link RescueAnimal} instance with the provided attribute values and persists the
     * changes to the database.
     *
     * @param animal the {@link RescueAnimal} to update (must not be null).
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
    public void updateAnimal(RescueAnimal animal, String name, String species, String gender, int age, int weight,
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

    /**
     * Retrieves the complete list of {@link RescueAnimal} objects managed by the service.
     * <p>
     * The returned {@code allAnimals} list is mutable by design to support in-place operations
     * such as sorting within the {@code TableView} in {@link MainViewController}.
     * All structural modifications (add, remove, update) should be performed exclusively
     * through this service's methods to maintain data integrity and persistence.
     *
     * @return a mutable {@link ObservableList} containing all rescue animals.
     */
    public ObservableList<RescueAnimal> getAllAnimals() {
        return allAnimals;
    }
}
