# JavaFX CRUD App
![demo](https://github.com/dorian-adams/CRUD-Grazioso-JavaFX/blob/main/crud_demo.gif)

This CRUD application is designed to intake and track an animal's training status for a mock company, Grazioso.

## Technologies
* Java
* JavaFX
* FXML

## Features
* Serialization / deserialization
* Sortable table view
* Input validation
* User interface
* MVC design pattern
* CRUD

## Purpose
This project began as a class assignment that I expanded upon to try my hands at something more complex. The original assignment involved creating a Java application that took input from the console and managed animals for a mock company called Grazioso. To make this into a full-featured CRUD app, I added a UI via JavaFX, serialization/deserialization to save data across uses, and more. My goal was to create a more professional and complete program for better practice.

## What I Learned
In working on this project, I honed three valuable skills: research, implementing professional design patterns, and even Android development. At the onset of this project, I had no previous exposure to JavaFX. To increase my familiarity, I utilized various tools provided by the IDE and consulted official documentation when more in-depth research was required. As such, I'm now more confident in adapting to new libraries quickly. The design architecture I used, Model-View-Controller, can be applied to various languages and applications. Implementing MVC in this project was a great lesson in the benefits of creating highly focused and organized components. The result is a very easy codebase to read, expand upon, and manage. Surprisingly, I've also found many of the practices and techniques used in JavaFX are highly applicable to Android development, which I look forward to exploring soon.

## Code Snippets
Initialize the table:

```Java
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnimalList.initializeList();
            colName.setCellValueFactory(data -> data.getValue().animalNameProperty());
            colSpecies.setCellValueFactory(data -> data.getValue().animalSpeciesProperty());
            colLocation.setCellValueFactory(data -> data.getValue().locationProperty());
            colName.setCellFactory(TextFieldTableCell.forTableColumn());
            colSpecies.setCellFactory(TextFieldTableCell.forTableColumn());
            colLocation.setCellFactory(TextFieldTableCell.forTableColumn());
            tableView.setItems(AnimalList.allAnimals);
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

Date validation method:

```Java
    private void validDate (DatePicker date) {
        LocalDate today = LocalDate.now();
        date.setValue(today);
        date.setOnAction(e -> {
           if (today.isBefore(date.getValue())) {
               raiseWarning(date);
               date.setValue(today);
           } else {
               suppressError();
           }
       });
    }
```

Serialize:

```Java
    /** Serializer is static-only, and not to be instantiated. */
    private Serializer() {}

    public static void serialize(ObservableList<RescueAnimal> observableListAnimals) throws IOException {
        try(var serializer = new ObjectOutputStream(new FileOutputStream(PATH, false))) {
            ArrayList<RescueAnimal> convertedList = new ArrayList<>(observableListAnimals);
            serializer.writeObject(convertedList);
        }
    }
```

## Relevant Docs and Resources
* [Official Walkthroughs](https://docs.oracle.com/javase/8/javafx/get-started-tutorial/get_start_apps.htm#JFXST804)
* [JavaFX API](https://docs.oracle.com/javase/8/javafx/api/toc.htm)
* [SimpleStringProperty](https://docs.oracle.com/javase/8/javafx/api/javafx/beans/property/SimpleStringProperty.html)
* [TableView](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html)
* [TableColumn](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html)
* [TextFieldTableCell](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/TextFieldTableCell.html)
* [MVC Article by Eden-Rump](https://edencoding.com/mvc-in-javafx/#:~:text=MVC%20stands%20for%20Model%2DView,logic%20from%20the%20user%20interface.)
