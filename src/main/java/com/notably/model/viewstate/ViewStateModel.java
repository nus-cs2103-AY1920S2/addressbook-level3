package com.notably.model.viewstate;

import java.util.Optional;

import javafx.beans.property.Property;

/**
 * API for the ViewStateModel component.
 */
public interface ViewStateModel extends CommandInputModel, HelpFlagModel, BlockEditFlagModel {

    /**
     * Shows user the meaning of their input, e.g. "Create a new note".
     * When input cannot get corrected in SuggestionEngine and/ or Parser, the "error" message will be shown
     * in responseTextProperty.
     *
     * @return The meaning of the user input.
     */
    Property<Optional<String>> responseTextProperty();

    /**
     * Updates the responseTextProperty.
     *
     * @param responseTextStr A response text to the user, which could be an explanation of the typed command
     * or an error message.
     */
    void setResponseText(String responseTextStr);

    /**
     * Resets the response text when the user clears the input text field.
     */
    void clearResponseText();
}
