package com.notably.model.viewstate;

import java.util.Optional;

import javafx.beans.property.Property;

/**
 * API for the ViewStateModel component.
 */
public interface ViewStateModel extends CommandInputModel, HelpFlagModel {

    /**
     * Shows user the meaning of their input, e.g. "Create a new note".
     * When input cannot get corrected, the "error" message will be shown in commandTextProperty.
     * TODO: add logo.
     * @return The meaning of the user input.
     */
    Property<Optional<String>> responseTextProperty();

    /**
     * Updates the responseTextProperty.
     * @param responseText The meaning of the "command" that the user inputs.
     */
    void setResponseText(String responseText);

    /**
     * Resets the command text when the user clears the input text field.
     */
    void clearResponseText();
}
