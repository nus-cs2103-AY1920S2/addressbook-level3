package com.notably.model.viewstate;

import java.util.Objects;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents the data that the user can manipulate through interaction with front-facing components
 * such as buttons and text fields.
 */
public class ViewStateModelImpl implements ViewStateModel {

    private final StringProperty input;
    private final Property<Optional<String>> responseText;
    private final BooleanProperty helpOpen;

    public ViewStateModelImpl() {
        this("", Optional.empty(), false);
    }

    private ViewStateModelImpl(String initialInput, Optional<String> initialResponseText, boolean initialHelpBool) {
        this.input = new SimpleStringProperty(initialInput);
        responseText = new SimpleObjectProperty<>(initialResponseText);
        this.helpOpen = new SimpleBooleanProperty(initialHelpBool);
    }

    //=========== CommandInputModel ===============================================================
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

    //=========== HelpFlagModel ===================================================================
    @Override
    public BooleanProperty helpOpenProperty() {
        return this.helpOpen;
    }

    @Override
    public Boolean isHelpOpen() {
        return this.helpOpen.getValue();
    }

    @Override
    public void setHelpOpen(Boolean bool) {
        this.helpOpen.setValue(bool);
    }

    //============ Response Text ===========================================================
    @Override
    public Property<Optional<String>> responseTextProperty() {
        return responseText;
    }

    @Override
    public void setResponseText(String responseTextStr) {
        Objects.requireNonNull(responseTextStr);
        responseText.setValue(Optional.of(responseTextStr));
    }

    @Override
    public void clearResponseText() {
        responseText.setValue(Optional.empty());
    }
}

