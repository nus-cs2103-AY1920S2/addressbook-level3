package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.Scheduler;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandTest {
    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssignmentCommand(null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();

        CommandResult commandResult = new AddAssignmentCommand(validAssignment).execute(modelStub);

        assertEquals(String.format(AddAssignmentCommand.MESSAGE_SUCCESS, validAssignment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment validAssignment = new AssignmentBuilder().build();
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(validAssignment);
        ModelStubWithAssignment modelStub = new ModelStubWithAssignment(validAssignment);

        assertThrows(CommandException.class, AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, ()
            -> addAssignmentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Assignment cs2103Tp = new AssignmentBuilder().build();
        Assignment cs3243Tut = new AssignmentBuilder().withTitle("CS3243 Tut").build();
        AddAssignmentCommand addCs2103Command = new AddAssignmentCommand(cs2103Tp);
        AddAssignmentCommand addCs3243Command = new AddAssignmentCommand(cs3243Tut);

        // same object -> returns true
        assertTrue(addCs2103Command.equals(addCs2103Command));

        // same values -> returns true
        AddAssignmentCommand addCs2103CommandCopy = new AddAssignmentCommand(cs2103Tp);
        assertTrue(addCs2103Command.equals(addCs2103CommandCopy));

        // different types -> returns false
        assertFalse(addCs2103Command.equals(1));

        // null -> returns false
        assertFalse(addCs2103Command.equals(null));

        // different person -> returns false
        assertFalse(addCs2103Command.equals(addCs3243Command));
    }


    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.isSameAssignment(assignment);
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return assignmentsAdded.stream().anyMatch(assignment::isSameAssignment);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public ReadOnlyScheduler getScheduler() {
            return new Scheduler();
        }
    }
}



