package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ESTHOURS_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CS2103;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.CS2103_TP;
import static seedu.address.testutil.TypicalAssignments.CS2106_ASSIGNMENT;
import static seedu.address.testutil.TypicalAssignments.getTypicalScheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.testutil.AssignmentBuilder;

public class SchedulerTest {

    private final AssignmentSchedule assignmentSchedule = new AssignmentSchedule();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), assignmentSchedule.getAssignmentsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentSchedule.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyScheduler_replacesData() {
        AssignmentSchedule newData = getTypicalScheduler();
        assignmentSchedule.resetData(newData);
        assertEquals(newData, assignmentSchedule);
    }

    @Test
    public void resetData_withDuplicateAssignments_throwsDuplicateAssignmentException() {
        // Two assignments with the same title and deadline
        Assignment editedCs2103Tp = new AssignmentBuilder(CS2103_TP).withHours(VALID_ESTHOURS_CS2103)
                .withStatus(VALID_STATUS_CS2103).build();
        List<Assignment> newAssignments = Arrays.asList(CS2103_TP, editedCs2103Tp);
        AssignmentScheduleStub newData = new AssignmentScheduleStub(newAssignments);

        assertThrows(DuplicateAssignmentException.class, () -> assignmentSchedule.resetData(newData));
    }

    @Test
    public void hasAssignment_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentSchedule.hasAssignment(null));
    }

    @Test
    public void hasAssignment_assignmentNotInScheduler_returnsFalse() {
        assertFalse(assignmentSchedule.hasAssignment(CS2103_TP));
    }

    @Test
    public void hasAssignment_assignmentInScheduler_returnsTrue() {
        assignmentSchedule.addAssignment(CS2103_TP);
        assertTrue(assignmentSchedule.hasAssignment(CS2103_TP));
    }

    @Test
    public void hasAssignment_assignmentWithSameTitleAndDeadlineInScheduler_returnsTrue() {
        assignmentSchedule.addAssignment(CS2106_ASSIGNMENT);
        Assignment editedCs2106Assignment = new AssignmentBuilder(CS2106_ASSIGNMENT).withHours(VALID_ESTHOURS_CS2103)
                .withStatus(VALID_STATUS_CS2103).build();
        assertTrue(assignmentSchedule.hasAssignment(editedCs2106Assignment));
    }

    @Test
    public void getAssignmentsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> assignmentSchedule.getAssignmentsList().remove(0));
    }

    /**
     * A stub ReadOnlyAssignmentSchedule whose assignment list can violate interface constraints.
     */
    private static class AssignmentScheduleStub implements ReadOnlyAssignmentSchedule {
        private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();

        AssignmentScheduleStub(Collection<Assignment> assignments) {
            this.assignments.setAll(assignments);
        }

        @Override
        public ObservableList<Assignment> getAssignmentsList() {
            return assignments;
        }
    }
}
