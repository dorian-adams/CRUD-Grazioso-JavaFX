package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.RescueAnimal;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("SpellCheckingInspection")
public class RescueAnimalDAO {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost/graz?user="+USERNAME+"&password="+PASSWORD;
    private static final String TABLE = "rescue_animal";
    private static Connection conn = null;

    public RescueAnimalDAO() {}

    public static void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<RescueAnimal> getAll() {
        ObservableList<RescueAnimal> animals = FXCollections.observableArrayList();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM rescue_animal");
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

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

    public void delete (RescueAnimal animal) {
        String sql = "DELETE from rescue_animal WHERE id=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, animal.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean tableExists() {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, TABLE, new String[]{"TABLE"})) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createTable() {
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

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
