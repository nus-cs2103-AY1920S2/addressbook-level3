package com.notably.model.viewstate;

import javafx.beans.property.BooleanProperty;

/**
 * API for the HelpFlagModel.
 *
 * Represents the state of the Help modal in the View, which can be modified in response to user
 * events (user selects the Help tab via keyboard/mouse, or by typing the "Help" command). This
 * state is then used to determine whether the modal is displayed. Also contains supporting operations.
 */
public interface HelpFlagModel {

    /**
     * Represents a flag determining whether or not a help modal should be opened.
     *
     * @return A javafx BooleanProperty object.
     */
    BooleanProperty helpOpenProperty();

    /**
     * Gets the flag representing whether the Help Window should be displayed or not.
     *
     * @return A Boolean. true if the Help Window should be displayed, false otherwise.
     */
    Boolean isHelpOpen();

    /**
     * Sets the value for the boolean flag representing whether the Help Window should be displayed or not.
     *
     * @param bool true if the Help Window is to be displayed, false otherwise.
     */
    void setHelpOpen(Boolean bool);
}
