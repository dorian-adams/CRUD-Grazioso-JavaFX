package com.example.gsjavafx.config;

import com.example.gsjavafx.dao.RescueAnimalDAO;
import com.example.gsjavafx.models.AnimalService;
import com.example.gsjavafx.models.RescueAnimal;
import com.zaxxer.hikari.HikariDataSource;
import dev.mccue.feather.Provides;
import jakarta.inject.Singleton;
import javafx.fxml.FXMLLoader;

/**
 * Provides dependency injection for core application components.
 * All provided instances are singletons to ensure resource reuse and consistent state management.
 * <p>
 * Circumvents JavaFX's limitation of automatically instantiating controllers using parameterless
 * constructors, which offer limited control over the instantiation process.
 * Dependency injection via Feather enables a more maintainable and testable way of supplying
 * dependencies to these controllers.
 */
public class DIModule {
    private final FXMLLoader loader;

    public DIModule(FXMLLoader loader) {
        this.loader = loader;
    }

    /**
     * Provides a singleton {@link HikariDataSource} for database access.
     *
     * @return the application's {@link HikariDataSource} (thread safe) instance.
     */
    @Provides
    @Singleton
    public HikariDataSource ds() {
        return DataSourceConfig.getDataSource();
    }

    /**
     * Provides a singleton {@link RescueAnimalDAO} configured with the shared {@link HikariDataSource}.
     *
     * @param ds the shared {@link HikariDataSource} used for database connections.
     * @return a configured {@link RescueAnimalDAO} instance for database operations.
     */
    @Provides
    @Singleton
    public RescueAnimalDAO dao(HikariDataSource ds) {
        return new RescueAnimalDAO(ds);
    }

    /**
     * Provides a singleton {@link AnimalService} with its required DAO.
     *
     * @param dao the data access object for rescue animals.
     * @return the application's service layer for managing {@link RescueAnimal} objects.
     */
    @Provides
    @Singleton
    public AnimalService animalService(RescueAnimalDAO dao) {
        return new AnimalService(dao);
    }
}

