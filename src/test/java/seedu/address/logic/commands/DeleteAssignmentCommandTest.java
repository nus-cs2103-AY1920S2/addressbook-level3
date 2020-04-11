package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.getTypicalSchoolworkTracker;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.model.AddressBook;
import seedu.address.model.EventSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;

public class DeleteAssignmentCommandTest {
    private Model model = new ModelManager(new AddressBook(),
        new RestaurantBook(),
        getTypicalSchoolworkTracker(),
        new EventSchedule(),
        new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Assignment assignmentToDelete = model.getFilteredAssignmentList().get(FIRST_INDEX.getZeroBased());
        DeleteAssignmentCommand deleteCommand = new DeleteAssignmentCommand(FIRST_INDEX);

        CommandResult expectedCommandResult = new CommandResult(String.format(DeleteAssignmentCommand
            .MESSAGE_DELETE_ASSIGNMENT_SUCCESS, assignmentToDelete), false, false, false,
            true, false, false, false, false);

        ModelManager expectedModel = new ModelManager(new AddressBook(),
            new RestaurantBook(),
            getTypicalSchoolworkTracker(),
            new EventSchedule(),
            new UserPrefs());
        expectedModel.deleteAssignment(assignmentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        DeleteAssignmentCommand deleteCommand = new DeleteAssignmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteAssignmentCommand deleteFirstCommand = new DeleteAssignmentCommand(FIRST_INDEX);
        DeleteAssignmentCommand deleteSecondCommand = new DeleteAssignmentCommand(SECOND_INDEX);

        // same values -> returns true
        DeleteAssignmentCommand commandWithSameValues = new DeleteAssignmentCommand(FIRST_INDEX);
        assertTrue(deleteFirstCommand.equals(commandWithSameValues));


        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different assignment -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
