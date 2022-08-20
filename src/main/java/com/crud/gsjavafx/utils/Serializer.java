package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;

/** Serialize and deserialize ArrayList AnimalList.allAnimals (see: {@link AnimalList}). */
public class Serializer {
    private static final String PATH = "data.bin";

    public static void serialize(ObservableList<RescueAnimal> observableListAnimals) throws IOException {
        try(var serializer = new ObjectOutputStream(new FileOutputStream(PATH, false))) {
            ArrayList<RescueAnimal> convertedList = new ArrayList<>(observableListAnimals);
            serializer.writeObject(convertedList);
        }
    }

    public static ArrayList<RescueAnimal> deserialize() throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(PATH))) {
            ArrayList<RescueAnimal> deserializedArrayList = new ArrayList<>();
            for (RescueAnimal animal : (ArrayList<RescueAnimal>) objectIn.readObject()) {
                RescueAnimal deserializedAnimal = new RescueAnimal(
                        animal.getSerializableName(), animal.getSerializableSpecies(), animal.getGender(),
                        animal.getAge(), animal.getWeight(), animal.getAcquisitionDate(),
                        animal.getSerializableLocation(), animal.getTrainingStatus(), animal.getReserved()
                );
                deserializedArrayList.add(deserializedAnimal);
            }
            return deserializedArrayList;
        }
    }
}
