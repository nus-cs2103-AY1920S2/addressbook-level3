package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.getTypicalScheduler;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class DoneCommandTest {
    private Model model = new ModelManager(new AddressBook(),
            new RestaurantBook(),
            getTypicalScheduler(),
            new UserPrefs());

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getAssignmentList().size() + 1);
        DoneCommand doneCommand = new DoneCommand(outOfBoundIndex);

        assertCommandFailure(doneCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_markingCompletedAssignment_throwsCommandException() {
        Index assignmentIndex = Index.fromOneBased(model.getAssignmentList().size());
        DoneCommand doneCommand = new DoneCommand(assignmentIndex);

        assertCommandFailure(doneCommand, model, DoneCommand.MESSAGE_ALREADY_DONE);
    }

    @Test
    public void execute_markUncompletedAssignment_success() {
        Index assignmentIndex = Index.fromOneBased(1);
        DoneCommand doneCommand = new DoneCommand(assignmentIndex);

        Assignment assignmentInList = model.getAssignmentList().get(assignmentIndex.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder(assignmentInList).withStatus("Completed").build();

        Model expectedModel = new ModelManager(new AddressBook(),
                new RestaurantBook(),
                getTypicalScheduler(),
                new UserPrefs());
        expectedModel.setAssignment(model.getAssignmentList().get(assignmentIndex.getZeroBased()), editedAssignment);

        String expectedMessage = String.format(DoneCommand.MESSAGE_UPDATE_STATUS_SUCCESS, editedAssignment.getTitle());

        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }
}
