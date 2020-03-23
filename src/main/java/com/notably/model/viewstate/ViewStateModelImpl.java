package com.notably.model.viewstate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents the data that the user can manipulate through interaction with front-facing components
 * such as buttons and text fields.
 */
public class ViewStateModelImpl implements ViewStateModel {

    private final StringProperty input;
    private final BooleanProperty bool;

    public ViewStateModelImpl() {
        this("", false);
    }

    private ViewStateModelImpl(String initialInput, boolean initialHelpBool) {
        this.input = new SimpleStringProperty(initialInput);
        this.bool = new SimpleBooleanProperty(initialHelpBool);
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
    public BooleanProperty boolProperty() {
        return this.bool;
    }

    @Override
    public Boolean getBool() {
        return this.bool.getValue();
    }

    @Override
    public void setBool(Boolean bool) {
        this.bool.setValue(bool);
    }
}

