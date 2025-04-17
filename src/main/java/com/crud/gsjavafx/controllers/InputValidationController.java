package com.crud.gsjavafx.controllers;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.time.LocalDate;

public class InputValidationController<T extends Node> {
    private boolean error;
    private final Tooltip toolTip = new Tooltip();

    /** TextField constructor. */
    public InputValidationController (TextField field) {
        validString(field);
        toolTip.setText("Input is required and may not contain digits.");
        field.setTooltip(toolTip);
    }

    /** DatePicker Constructor. */
    public InputValidationController (DatePicker date) {
        validDate(date);
        toolTip.setText("Cannot choose a future date.");
        date.setTooltip(toolTip);
    }

    /** Spinner Constructor. */
    public InputValidationController (Spinner<Integer> numField) {
        validNumber(numField);
        toolTip.setText("Input may not contain digits and must be within specified range.");
        numField.setTooltip(toolTip);
    }

    private void validString(TextField field) {
        final int MAX_STRING_LEN = 20;

        if (field.getText() == null) {
            error = true;
        }

        field.setTextFormatter(new TextFormatter<>(c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().isEmpty()) {
                    error = true;
                    raiseWarning(field);
                    return c;
                }
                if (c.getControlNewText().isEmpty()) {
                    return c;
                }
                if (c.getControlNewText().length() > MAX_STRING_LEN) {
                    return null;
                }
                if (!c.getControlNewText().matches("^[A-Za-z]+$")) {
                    raiseWarning(field);
                    return null;
                } else {
                    suppressError();
                    return c;
                }
            }
            return c;
        }));
    }

    private void validDate(DatePicker date) {
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

    private void validNumber(Spinner<Integer> numField) {
        final String DEFAULT_VALUE = "0";

        numField.getEditor().textProperty().addListener((observable, oldV, newV) -> {
            if (!newV.matches("\\d*") || newV.isEmpty()) {
                raiseWarning(numField);
                numField.getEditor().setText(DEFAULT_VALUE);
            }
        });
    }

    private void raiseWarning(Node node) {
        double x = node.getScene().getWindow().getX() + node.getLayoutX();
        double y = node.getScene().getWindow().getY() + node.getLayoutY();
        toolTip.setAutoHide(true);
        toolTip.show(node, x, y);
    }

    private void suppressError() {
       error = false;
    }

    public boolean getError() {
        return this.error;
    }
}
