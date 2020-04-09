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

    private final Scheduler scheduler = new Scheduler();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), scheduler.getAssignmentsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scheduler.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyScheduler_replacesData() {
        Scheduler newData = getTypicalScheduler();
        scheduler.resetData(newData);
        assertEquals(newData, scheduler);
    }

    @Test
    public void resetData_withDuplicateAssignments_throwsDuplicateAssignmentException() {
        // Two assignments with the same title and deadline
        Assignment editedCs2103Tp = new AssignmentBuilder(CS2103_TP).withHours(VALID_ESTHOURS_CS2103)
                .withStatus(VALID_STATUS_CS2103).build();
        List<Assignment> newAssignments = Arrays.asList(CS2103_TP, editedCs2103Tp);
        SchedulerTest.SchedulerStub newData = new SchedulerTest.SchedulerStub(newAssignments);

        assertThrows(DuplicateAssignmentException.class, () -> scheduler.resetData(newData));
    }

    @Test
    public void hasAssignment_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scheduler.hasAssignment(null));
    }

    @Test
    public void hasAssignment_assignmentNotInScheduler_returnsFalse() {
        assertFalse(scheduler.hasAssignment(CS2103_TP));
    }

    @Test
    public void hasAssignment_assignmentInScheduler_returnsTrue() {
        scheduler.addAssignment(CS2103_TP);
        assertTrue(scheduler.hasAssignment(CS2103_TP));
    }

    @Test
    public void hasAssignment_assignmentWithSameTitleAndDeadlineInScheduler_returnsTrue() {
        scheduler.addAssignment(CS2106_ASSIGNMENT);
        Assignment editedCs2106Assignment = new AssignmentBuilder(CS2106_ASSIGNMENT).withHours(VALID_ESTHOURS_CS2103)
                .withStatus(VALID_STATUS_CS2103).build();
        assertTrue(scheduler.hasAssignment(editedCs2106Assignment));
    }

    @Test
    public void getAssignmentsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> scheduler.getAssignmentsList().remove(0));
    }

    /**
     * A stub ReadOnlyScheduler whose assignment list can violate interface constraints.
     */
    private static class SchedulerStub implements ReadOnlyScheduler {
        private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();

        SchedulerStub(Collection<Assignment> assignments) {
            this.assignments.setAll(assignments);
        }

        @Override
        public ObservableList<Assignment> getAssignmentsList() {
            return assignments;
        }
    }
}
