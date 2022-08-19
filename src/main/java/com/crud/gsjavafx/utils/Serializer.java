package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/** Serialize and deserialize ArrayList AnimalList.allAnimals (see: {@link AnimalList}). */
public class Serializer {
    private static final String PATH = "data.bin";

    public static void serialize(ObservableList<RescueAnimal> animals) throws IOException {
        try(var serializer = new ObjectOutputStream(new FileOutputStream(PATH, false))) {
            ArrayList<RescueAnimal> convertedList = new ArrayList<>(animals);
            serializer.writeObject(convertedList);
        }
    }

    public static ArrayList<RescueAnimal> deserialize() throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(PATH))) {
            return (ArrayList<RescueAnimal>) objectIn.readObject();
        }
    }
}
