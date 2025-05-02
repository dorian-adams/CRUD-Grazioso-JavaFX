package com.crud.gsjavafx.dao;

import com.crud.gsjavafx.models.RescueAnimal;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Data Access Object (DAO) for managing {@link RescueAnimal} entities in the database.
 * Handles all CRUD (Create, Read, Update, Delete) operations for the {@code rescue_animal} table.
 */
@SuppressWarnings("SpellCheckingInspection")
public class RescueAnimalDAO {
    private final HikariDataSource ds;
    private static final String TABLE = "rescue_animal";

    /**
     * Constructs the DAO with the injected {@link HikariDataSource} for database access.
     * Checks for the existence of the {@code rescue_animal} table and creates it if necessary.
     *
     * @param ds the {@link HikariDataSource} instance used to establish database connections.
     * @throws IllegalArgumentException if {@code ds} is null.
     */
    @Inject
    public RescueAnimalDAO(HikariDataSource ds) {
        if (ds == null) {
            throw new IllegalArgumentException("DataSource must not be null");
        }
        this.ds = ds;

        if (!tableExists()) {
            createTable();
        }
    }

    /**
     * Retrieves all {@link RescueAnimal} records from the database.
     *
     * @return an {@link ObservableList} containing all animals from the {@code rescue_animal} table.
     */
    public ObservableList<RescueAnimal> getAll() {
        ObservableList<RescueAnimal> animals = FXCollections.observableArrayList();

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rescue_animal")) {

            while (rs.next()) {
                RescueAnimal animal = new RescueAnimal(
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("gender"),
                        rs.getInt("age"),
                        rs.getInt("weight"),
                        rs.getDate("acquisition_date"),
                        rs.getString("location"),
                        rs.getInt("training_status"),
                        rs.getBoolean("reserved"),
                        rs.getInt("id")
                );
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * Inserts a new {@link RescueAnimal} into the database.
     * Executes an {@code INSERT} statement on the {@code rescue_animal} table using the provided
     * animal's attributes. If the insertion is successful, the method attempts to retrieve and return
     * the generated primary key (ID) of the new record.
     *
     * @param animal the {@link RescueAnimal} instance to insert into the database.
     * @return the generated ID of the inserted animal, or {@code 0} if the insertion failed.
     */
    public int insert(RescueAnimal animal) {
        String sql = "INSERT INTO rescue_animal (" +
                "name, " +
                "species, " +
                "gender, " +
                "age, " +
                "weight, " +
                "acquisition_date, " +
                "location, " +
                "training_status, " +
                "reserved) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getName());
            stmt.setString(2, animal.getAnimalType());
            stmt.setString(3, animal.getGender());
            stmt.setInt(4, animal.getAge());
            stmt.setInt(5, animal.getWeight());
            stmt.setDate(6, animal.getAcquisitionDate());
            stmt.setString(7, animal.getLocation());
            stmt.setInt(8, animal.getTrainingStatus());
            stmt.setBoolean(9, animal.getReserved());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Updates an existing {@link RescueAnimal} record in the database.
     * Executes an {@code UPDATE} statement on the {@code rescue_animal} table using the fields of the
     * provided {@code animal} object.
     *
     * @param animal the {@link RescueAnimal} instance containing updated values to persist.
     */
    public void update(RescueAnimal animal) {
        String sql = "UPDATE rescue_animal SET " +
                "name=?, " +
                "species=?, " +
                "gender=?, " +
                "age=?, " +
                "weight=?, " +
                "acquisition_date=?, " +
                "location=?, " +
                "training_status=?, " +
                "reserved=? " +
                "WHERE id=?;";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getName());
            stmt.setString(2, animal.getAnimalType());
            stmt.setString(3, animal.getGender());
            stmt.setInt(4, animal.getAge());
            stmt.setInt(5, animal.getWeight());
            stmt.setDate(6, animal.getAcquisitionDate());
            stmt.setString(7, animal.getLocation());
            stmt.setInt(8, animal.getTrainingStatus());
            stmt.setBoolean(9, animal.getReserved());
            stmt.setInt(10, animal.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the specified {@link RescueAnimal} from the database.
     * Executes a {@code DELETE} statement on the {@code rescue_animal} table using the animal's {@code id}.
     *
     * @param animal the {@link RescueAnimal} instance to delete.
     */
    public void delete (RescueAnimal animal) {
        String sql = "DELETE from rescue_animal WHERE id=?;";

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, animal.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether the {@code rescue_animal} table exists in the database.
     * Used to determine whether the expected table must be created.
     *
     * @return {@code true} if the table exists, {@code false} otherwise or if an error occurs.
     */
    private boolean tableExists() {
        try (Connection conn = ds.getConnection();
             ResultSet rs = conn.getMetaData().getTables(null, null, TABLE, new String[]{"TABLE"});) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates the {@code rescue_animal} table in the database.
     * Called automatically by the constructor if the {@code rescue_animal} table does not exist.
     */
    private void createTable() {
        String createTableSQL = "CREATE TABLE rescue_animal (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "species VARCHAR(50), " +
                "gender VARCHAR(10), " +
                "age INT, " +
                "weight INT, " +
                "acquisition_date DATE, " +
                "location VARCHAR(100), " +
                "training_status INT, " +
                "reserved BOOLEAN" +
                ");";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
