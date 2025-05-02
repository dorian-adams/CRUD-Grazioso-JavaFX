package com.example.gsjavafx.controllers;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.time.LocalDate;

/**
 * Provides input validation for various fields and displays
 * appropriate warning tooltips when validation fails.
 *
 * @param <T> the type of {@link Node} that this controller will validate.
 */
public class InputValidationController<T extends Node> {
    private boolean error;
    private final Tooltip toolTip = new Tooltip();

    /**
     * Constructs an {@code InputValidationController} for the given {@link TextField}.
     *
     * @param field the {@link TextField} to validate and annotate with a tooltip.
     */
    public InputValidationController (TextField field) {
        validString(field);
        toolTip.setText("Input is required and may not contain digits.");
        field.setTooltip(toolTip);
    }

    /**
     * Constructs an {@code InputValidationController} for the given {@link DatePicker}.
     *
     * @param date the {@link DatePicker} to validate and annotate with a tooltip.
     */
    public InputValidationController (DatePicker date) {
        validDate(date);
        toolTip.setText("Cannot choose a future date.");
        date.setTooltip(toolTip);
    }

    /**
     * Constructs an {@code InputValidationController} for the given {@link Spinner}.
     *
     * @param numField the {@link Spinner} to validate and annotate with a tooltip.
     */
    public InputValidationController (Spinner<Integer> numField) {
        validNumber(numField);
        toolTip.setText("The input must be numeric and fall within the specified range.");
        numField.setTooltip(toolTip);
    }

    /**
     * Applies validation to a {@link TextField} to ensure the input is an
     * alphabetic string with a maximum length of 20 characters.
     *
     * @param field the {@link TextField} to which the validation is applied.
     */
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

    /**
     * Applies validation to a {@link DatePicker} to ensure the selected date is not in the future.
     * Resets any future date input to today's date.
     *
     * @param date the {@link DatePicker} to which the validation is applied.
     */
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

    /**
     * Applies numeric input validation to a {@link Spinner} of type {@link Integer}.
     * Ensures that the spinner's editor only accepts digits.
     * Resets any invalid input to default value of 0.
     *
     * @param numField the {@link Spinner} to which the validation is applied.
     */
    private void validNumber(Spinner<Integer> numField) {
        final String DEFAULT_VALUE = "0";

        numField.getEditor().textProperty().addListener((observable, oldV, newV) -> {
            if (!newV.matches("\\d*") || newV.isEmpty()) {
                raiseWarning(numField);
                numField.getEditor().setText(DEFAULT_VALUE);
            }
        });
    }

    /**
     * Displays a warning tooltip near the specified UI node.
     * This method is expected to be called when any validation method fails.
     *
     * @param node the {@link Node} near which the warning tooltip should appear.
     */
    private void raiseWarning(Node node) {
        double x = node.getScene().getWindow().getX() + node.getLayoutX();
        double y = node.getScene().getWindow().getY() + node.getLayoutY();
        toolTip.setAutoHide(true);
        toolTip.show(node, x, y);
    }

    /**
     * Resets the error flag to {@code false}.
     */
    private void suppressError() {
       error = false;
    }

    /**
     * Returns the current error status.
     *
     * @return {@code true} if there is an error, {@code false} otherwise.
     */
    public boolean getError() {
        return this.error;
    }
}
