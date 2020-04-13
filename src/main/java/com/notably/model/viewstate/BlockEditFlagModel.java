package com.notably.model.viewstate;

import javafx.beans.property.BooleanProperty;

/**
 * API for the BlockEditFlagModel.
 *
 * Represents the state of the Block Edit modal, which can be activated when the user types
 * the "edit" command. This state is then used to determine whether the modal is displayed.
 * Also contains supporting operations.
 */
public interface BlockEditFlagModel {

    /**
     * Represents a flag determining whether or not a Edit modal should be opened.
     *
     * @return A javafx BooleanProperty object.
     */
    BooleanProperty blockEditableProperty();

    /**
     * Gets the flag representing whether the Block Edit Modal should be displayed or not.
     *
     * @return A Boolean. true if the Help Model should be displayed, false otherwise.
     */
    Boolean isBlockEditable();

    /**
     * Sets the value for the boolean flag representing whether the Block Edit Modal should be displayed or not.
     *
     * @param bool true if the Edit Modal is to be displayed, false otherwise.
     */
    void setBlockEditable(Boolean bool);
}

