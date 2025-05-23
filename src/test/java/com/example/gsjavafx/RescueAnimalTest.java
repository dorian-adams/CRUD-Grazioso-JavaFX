package com.example.gsjavafx;

import com.example.gsjavafx.models.RescueAnimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Model Test: RescueAnimal")
public class RescueAnimalTest {
    private RescueAnimal sut;
    private final String animalName = "Java";
    private final String animalSpecies = "Dog";
    private final String location = "Pennsylvania";
    private final String gender = "Male";
    private final int age = 5;
    private final int weight = 10;
    private final Date acquisitionDate = Date.valueOf("2023-10-04");
    private final int trainingStatus = 2;
    private final boolean reserved = false;


    @BeforeEach
    void setUp() throws Exception {
        sut = new RescueAnimal(
                animalName, animalSpecies, gender, age, weight, acquisitionDate, location, trainingStatus,
                reserved
        );
    }

    @Test
    @Tag("Getter")
    @DisplayName("Test getter methods")
    void testGetterMethods() {
        assertAll(
                () -> assertEquals(animalName, sut.getName()),
                () -> assertEquals(animalSpecies, sut.getAnimalType()),
                () -> assertEquals(gender, sut.getGender()),
                () -> assertEquals(age, sut.getAge()),
                () -> assertEquals(weight, sut.getWeight()),
                () -> assertEquals(acquisitionDate, sut.getAcquisitionDate()),
                () -> assertEquals(location, sut.getLocation()),
                () -> assertEquals(trainingStatus, sut.getTrainingStatus()),
                () -> assertEquals(reserved, sut.getReserved())
        );
    }

    @Test
    @Tag("Setter")
    @DisplayName("Test setter methods")
    void testSetterMethods() {
        assertAll(
                () -> {
                    sut.setName("New Name");
                    assertEquals("New Name", sut.getName());
                },
                () -> {
                    sut.setAnimalType("Cat");
                    assertEquals("Cat", sut.getAnimalType());
                },
                () -> {
                    sut.setGender("Female");
                    assertEquals("Female", sut.getGender());
                },
                () -> {
                    sut.setAge(10);
                    assertEquals(10, sut.getAge());
                },
                () -> {
                    sut.setWeight(20);
                    assertEquals(20, sut.getWeight());
                },
                // Replace test for acq date
                () -> {
                    sut.setLocation("Denver");
                    assertEquals("Denver", sut.getLocation());
                },
                () -> {
                    sut.setTrainingStatus(4);
                    assertEquals(4, sut.getTrainingStatus());
                },
                () -> {
                    sut.setReserved(true);
                    assertTrue(sut.getReserved());
                }
        );
    }
}
