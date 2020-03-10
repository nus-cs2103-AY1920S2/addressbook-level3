package com.notably.logic.commands;

import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {

    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {

    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {

    }

    @Test
    public void execute_filteredList_success() {

    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {




    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {

    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {



    }

    @Test
    public void equals() {

        // same values -> returns true
    }

}
