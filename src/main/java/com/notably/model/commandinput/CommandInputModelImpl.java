package com.notably.model.commandinput;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains the data that the user sees as text within the text field of the CommandInputView
 * as well as some supporting operations.
 *
 * CommandInputModelImpl and CommandInputView both contain a String Property that have been
 * bidirectionally bound to each other.
 *
 * ie. When the user types text in the text field of CommandInputView, the changes are propagated
 * to CommandInputModelImpl. Conversely, changes to CommandInputModelImpl (e.g. when the user selects suggestions)
 * are reflected within the text field of CommandInputView to achieve autofill functionality.
 */
public class CommandInputModelImpl implements CommandInputModel {

    private final StringProperty input;

    public CommandInputModelImpl() {
        this("");
    }

    private CommandInputModelImpl(String initialInput) {
        this.input = new SimpleStringProperty(initialInput);
    }

    @Override
    public StringProperty inputProperty() {
        return this.input;
    }

    @Override
    public String getInput() {
        return this.input.getValue();
    }

    @Override
    public void setInput(String input) {
        this.input.setValue(input);
    }
}
