package com.crud.gsjavafx.models;

import com.crud.gsjavafx.utils.Serializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;

/**
 * A global list of RescueAnimal objects; stores all animals in an ArrayList for serialization.
 */
public class AnimalList {
    public static ObservableList<RescueAnimal> allAnimals = FXCollections.observableArrayList();

    /**
     * Populate public static field, allAnimals, on program start.
     *
     * @return the deserialized ArrayList composed of RescueAnimal(s).
     */
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

    /** Serialize the list, allAnimals. */
    public static void saveAnimalList() {
        try {
            Serializer.serialize(allAnimals);
        } catch(IOException e) {
        }
    }
}
