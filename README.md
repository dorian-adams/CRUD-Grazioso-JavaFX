# JavaFX CRUD App
![demo](https://github.com/dorian-adams/CRUD-Grazioso-JavaFX/blob/main/crud_demo.gif)

This CRUD application is designed to intake and track an animal's training status for a mock company, Grazioso.

## Technologies
* Java
* JavaFX
* FXML
* JDBC
* MySQL
* Feather Dependency Injection

## Features
* Sortable table view
* Input validation
* GUI

## Code Snippets
Initialize the table:

```Java
    /** Initialize TableView. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            colName.setCellValueFactory(data -> data.getValue().animalNameProperty());
            colSpecies.setCellValueFactory(data -> data.getValue().animalSpeciesProperty());
            colLocation.setCellValueFactory(data -> data.getValue().locationProperty());
            colName.setCellFactory(TextFieldTableCell.forTableColumn());
            colSpecies.setCellFactory(TextFieldTableCell.forTableColumn());
            colLocation.setCellFactory(TextFieldTableCell.forTableColumn());
            tableView.setItems(animalService.getAllAnimals());
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Set double-click event.
        tableView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                editAnimalWindow(getSelection());
            }
        });
    }
```

DAO snippet:

```Java
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
```

## Relevant Docs and Resources
* [JavaFX API](https://docs.oracle.com/javase/8/javafx/api/toc.htm)
* [TableView](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html)
* [TableColumn](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html)
* [DAO](https://www.oracle.com/java/technologies/data-access-object.html)
* [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html)
