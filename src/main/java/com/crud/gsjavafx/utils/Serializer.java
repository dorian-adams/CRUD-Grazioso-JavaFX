package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.AnimalList;
import com.crud.gsjavafx.models.RescueAnimal;

import java.io.*;
import java.util.ArrayList;

/** Serialize and deserialize ArrayList AnimalList.allAnimals (see: {@link AnimalList}). */
public class Serializer {
    private static final String PATH = "data.bin";
    private static ArrayList<RescueAnimal> allAnimals = new ArrayList<RescueAnimal>();

    public static void serialize(ArrayList<RescueAnimal> animals) throws IOException {
        try(var serializer = new ObjectOutputStream(new FileOutputStream(PATH, false))) {
            serializer.writeObject(animals);
        }
    }

    public static ArrayList<RescueAnimal> deserialize() throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(PATH))) {
            ArrayList<RescueAnimal> animals = (ArrayList<RescueAnimal>) objectIn.readObject();
            return animals;
        }
    }
}
