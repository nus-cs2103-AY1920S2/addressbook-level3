package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ESTHOURS_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CS2103;
import static seedu.address.testutil.TypicalAssignments.CS2103_TP;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AssignmentBuilder;

public class AssignmentTest {

    @Test
    public void isSameAssignment() {
        // same object -> returns true
        assertTrue(CS2103_TP.isSameAssignment(CS2103_TP));

        // null -> returns false
        assertFalse(CS2103_TP.isSameAssignment(null));

        // different title  -> returns false
        Assignment editedAssignment = new AssignmentBuilder(CS2103_TP).withTitle("CS3243 Proj").build();
        assertFalse(CS2103_TP.isSameAssignment(editedAssignment));

        // different deadline -> returns false
        editedAssignment = new AssignmentBuilder(CS2103_TP).withDeadline("2020-03-31 23:59").build();
        assertFalse(CS2103_TP.isSameAssignment(editedAssignment));

        // same title, same deadline, different status and workload -> returns true
        editedAssignment = new AssignmentBuilder(CS2103_TP).withHours(VALID_ESTHOURS_CS2103)
                .withStatus(VALID_STATUS_CS2103).build();
        assertTrue(CS2103_TP.isSameAssignment(editedAssignment));
    }
}
