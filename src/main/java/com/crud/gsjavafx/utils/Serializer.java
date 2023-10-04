package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;

/** Serialize and deserialize animal objects (see: {@link AnimalList}). */
public final class Serializer {
    private static final String PATH = "data.bin";

    /** Serializer is static-only, and not to be instantiated. */
    private Serializer() {}

    public static void serialize(ObservableList<RescueAnimal> animals) throws IOException {
        try(var serializer = new ObjectOutputStream(new FileOutputStream(PATH, false))) {
            for (RescueAnimal animal : animals) {
                serializer.writeObject(animal);
            }
        }
    }

    public static ArrayList<RescueAnimal> deserialize() throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(PATH))) {
            ArrayList<RescueAnimal> deserializedArrayList = new ArrayList<>();
            while (true) {
                try {
                    RescueAnimal deserializedAnimal = (RescueAnimal) objectIn.readObject();
                    deserializedArrayList.add(deserializedAnimal);
                } catch (EOFException eof) {
                    break;
                }
            }
            return deserializedArrayList;
        }
    }
}
