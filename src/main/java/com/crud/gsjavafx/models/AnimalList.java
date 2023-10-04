package com.crud.gsjavafx.models;

import com.crud.gsjavafx.utils.Serializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * A global list of RescueAnimal objects; stores all animals in an ArrayList for serialization.
 */
public final class AnimalList {
    public static ObservableList<RescueAnimal> allAnimals = FXCollections.observableArrayList();

    /** AnimalList is static-only, and not to be instantiated. */
    private AnimalList() {}

    /** Populate public static field, allAnimals, on program start. */
    public static void initializeList() {
        try {
            allAnimals.addAll(Serializer.deserialize());
        } catch(Exception e) {
        }
    }

    /** Add new animal to the list, allAnimals. */
    public static void setAllAnimals(RescueAnimal animal) {
        allAnimals.add(animal);
    }

    /** Serialize all objects in allAnimals */
    public static void saveAnimalList() {
        try {
            Serializer.serialize(allAnimals);
        } catch(IOException e) {
        }
    }
}
