package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditAssignmentDescriptor;
import seedu.address.model.*;
import seedu.address.model.assignment.Assignment;

import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalAssignments.getTypicalAssignmentSchedule;
import static seedu.address.testutil.TypicalIndexes.*;

public class EditAssignmentCommandTest {
    private Model model = new ModelManager(new AddressBook(),
        new RestaurantBook(),
        getTypicalAssignmentSchedule(),
        new EventSchedule(),
        new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAssignment = Index.fromOneBased(model.getFilteredAssignmentList().size());
        Assignment lastAssignment = model.getFilteredAssignmentList().get(indexLastAssignment.getZeroBased());

        AssignmentBuilder assignmentInList = new AssignmentBuilder(lastAssignment);
        Assignment editedAssignment = assignmentInList.withTitle(VALID_TITLE_CS2103).withWorkload(VALID_WORKLOAD_CS2103)
            .withDeadline(VALID_DEADLINE_CS3243).withStatus(VALID_STATUS_CS2103).build();

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS2103)
            .withWorkload(VALID_WORKLOAD_CS2103).withDeadline(VALID_DEADLINE_CS3243).withStatus(VALID_STATUS_CS2103)
            .build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(indexLastAssignment, descriptor);

        CommandResult expectedCommandResult = new CommandResult(String.format(EditAssignmentCommand
            .MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment), false, false, false, true, false, false, false, false);

        Model expectedModel =
            new ModelManager(new AddressBook(),
                new RestaurantBook(),
                new AssignmentSchedule(model.getAssignmentSchedule()),
                new EventSchedule(),
                new UserPrefs());

        expectedModel.setAssignment(lastAssignment, editedAssignment);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAssignment = Index.fromOneBased(model.getFilteredAssignmentList().size());
        Assignment lastAssignment = model.getFilteredAssignmentList().get(indexLastAssignment.getZeroBased());

        AssignmentBuilder assignmentInList = new AssignmentBuilder(lastAssignment);
        Assignment editedAssignment = assignmentInList.withTitle(VALID_TITLE_CS2103)
            .withWorkload(VALID_WORKLOAD_CS2103).build();

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS2103)
            .withWorkload(VALID_WORKLOAD_CS2103).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(indexLastAssignment, descriptor);

        CommandResult expectedCommandResult = new CommandResult(String.format(EditAssignmentCommand
            .MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment), false, false, false, true, false, false, false, false);

        Model expectedModel =
            new ModelManager(new AddressBook(),
                new RestaurantBook(),
                new AssignmentSchedule(model.getAssignmentSchedule()),
                new EventSchedule(),
                new UserPrefs());

        expectedModel.setAssignment(lastAssignment, editedAssignment);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateAssignmentUnfilteredList_failure() {
        Assignment firstAssignment = model.getFilteredAssignmentList().get(FIRST_INDEX.getZeroBased());
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(firstAssignment).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(SECOND_INDEX, descriptor);

        assertCommandFailure(editCommand, model, EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_invalidAssignmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS2103).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditAssignmentCommand standardCommand = new EditAssignmentCommand(FIRST_INDEX, DESC_CS2103);

        // same values -> returns true
        EditAssignmentDescriptor copyDescriptor = new EditAssignmentDescriptor(DESC_CS2103);
        EditAssignmentCommand commandWithSameValues = new EditAssignmentCommand(FIRST_INDEX, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(SECOND_INDEX, DESC_CS2103)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(FIRST_INDEX, DESC_CS3243)));
    }
}
