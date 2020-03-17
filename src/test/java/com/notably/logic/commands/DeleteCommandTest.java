//TODO: To be enabled or changed when refactoring is completed
//package com.notably.logic.commands;
//
//import static com.notably.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static com.notably.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//
//import com.notably.commons.core.index.Index;
//import com.notably.model.Model;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
// * {@code DeleteCommand}.
// */
//public class DeleteCommandTest {
//
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//
//
//
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() {
//
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() {
//
//        Index outOfBoundIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//    }
//
//    @Test
//    public void equals() {
//        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
//
//        // same object -> returns true
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
//
//        // same values -> returns true
//        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(deleteFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(deleteFirstCommand.equals(null));
//
//        // different person -> returns false
//        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
//    }
//
//    /**
//     * Updates {@code model}'s filtered list to show no one.
//     */
//    private void showNoPerson(Model model) {
//        model.updateFilteredPersonList(p -> false);
//
//        assertTrue(model.getFilteredPersonList().isEmpty());
//    }
//}
