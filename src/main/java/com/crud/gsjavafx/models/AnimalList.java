package com.crud.gsjavafx.models;

import com.crud.gsjavafx.utils.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A global list of RescueAnimal objects; stores all animals in an ArrayList for serialization.
 */
public class AnimalList implements Serializable {
    private static final long serialVersionUID = 2L;
    public static ArrayList<RescueAnimal> allAnimals = new ArrayList<RescueAnimal>();

    /**
     * Populate public static field, allAnimals, on program start.
     *
     * @return the deserialized ArrayList composed of RescueAnimal(s).
     */
    public static ArrayList<RescueAnimal> initializeList() {
        try {
            allAnimals = Serializer.deserialize();
        } catch(Exception e) {
        }
        return allAnimals;
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
