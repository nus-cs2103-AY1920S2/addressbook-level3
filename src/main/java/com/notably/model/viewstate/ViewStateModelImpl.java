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

    private StringProperty input;
    private Property<Optional<String>> responseTextProperty;

    private final BooleanProperty helpOpen;

    public ViewStateModelImpl() {
        this("", false);
        responseTextProperty = new SimpleObjectProperty<>(Optional.empty());
    }

    private ViewStateModelImpl(String initialInput, boolean initialHelpBool) {
        this.input = new SimpleStringProperty(initialInput);
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
        return responseTextProperty;
    }

    @Override
    public void setResponseText(String responseText) {
        Objects.requireNonNull(responseText);
        responseTextProperty.setValue(Optional.of(responseText));
    }

    @Override
    public void clearResponseText() {
        responseTextProperty.setValue(Optional.empty());
    }
}

