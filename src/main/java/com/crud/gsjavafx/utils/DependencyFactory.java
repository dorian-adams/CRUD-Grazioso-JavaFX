package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.AnimalService;

public class DependencyFactory {
    public static AnimalService createAnimalService() {
        RescueAnimalDAO dao = new RescueAnimalDAO();
        return new AnimalService(dao);
    }
}
