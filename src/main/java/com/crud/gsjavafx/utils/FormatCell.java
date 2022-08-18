package com.crud.gsjavafx.utils;

import com.crud.gsjavafx.models.RescueAnimal;
import javafx.scene.control.ListCell;

/** Format ListView cell, to show animal name in each row. */
public class FormatCell extends ListCell<RescueAnimal> {
    public FormatCell() {}

    @Override
    protected void updateItem(RescueAnimal item, boolean empty) {
        super.updateItem(item, empty);
        setText(item == null ? "" : item.getName());
    }
}
