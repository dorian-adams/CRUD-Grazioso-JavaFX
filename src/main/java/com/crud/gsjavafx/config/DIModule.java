package com.crud.gsjavafx.config;

import com.crud.gsjavafx.dao.RescueAnimalDAO;
import com.crud.gsjavafx.models.AnimalService;
import com.zaxxer.hikari.HikariDataSource;
import dev.mccue.feather.Provides;
import jakarta.inject.Singleton;
import javafx.fxml.FXMLLoader;

public class DIModule {
    private final FXMLLoader loader;

    public DIModule(FXMLLoader loader) {
        this.loader = loader;
    }

    @Provides
    @Singleton
    public HikariDataSource ds() {
        return DataSourceConfig.getDataSource();
    }

    @Provides
    @Singleton
    public RescueAnimalDAO dao(HikariDataSource ds) {
        return new RescueAnimalDAO(ds);
    }

    @Provides
    @Singleton
    public AnimalService animalService(RescueAnimalDAO dao) {
        return new AnimalService(dao);
    }
}

