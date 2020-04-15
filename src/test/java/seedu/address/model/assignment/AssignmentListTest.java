package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKLOAD_CS2103;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.CS2103_TP;
import static seedu.address.testutil.TypicalAssignments.CS3243_TUT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.testutil.AssignmentBuilder;

public class AssignmentListTest {
    private final AssignmentList assignmentList = new AssignmentList();

    @Test
    public void contains_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.contains(null));
    }

    @Test
    public void contains_assignmentNotInList_returnsFalse() {
        assertFalse(assignmentList.contains(CS2103_TP));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        assignmentList.add(CS2103_TP);
        assertTrue(assignmentList.contains(CS2103_TP));
    }

    @Test
    public void contains_assignmentWithSameTitleAndDeadline_returnsTrue() {
        assignmentList.add(CS2103_TP);
        Assignment editedAssignment = new AssignmentBuilder(CS2103_TP).withStatus(VALID_STATUS_CS2103)
                .withWorkload(VALID_WORKLOAD_CS2103).build();
        assertTrue(assignmentList.contains(editedAssignment));
    }

    @Test
    public void add_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.add(null));
    }

    @Test
    public void add_duplicateAssignment_throwsDuplicateAssignmentException() {
        assignmentList.add(CS2103_TP);
        assertThrows(DuplicateAssignmentException.class, () -> assignmentList.add(CS2103_TP));
    }

    @Test
    public void setAssignment_nullTargetAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.setAssignment(null, CS2103_TP));
    }

    @Test
    public void setAssignment_nullEditedAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.setAssignment(CS2103_TP, null));
    }

    @Test
    public void setAssignment_targetAssignmentNotInList_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> assignmentList.setAssignment(CS3243_TUT, CS3243_TUT));
    }

    @Test
    public void setAssignment_editedAssignmentIsSameAssignment_success() {
        assignmentList.add(CS2103_TP);
        assignmentList.setAssignment(CS2103_TP, CS2103_TP);
        AssignmentList expectedAssignmentList = new AssignmentList();
        expectedAssignmentList.add(CS2103_TP);
        assertEquals(expectedAssignmentList, assignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasSameIdentity_success() {
        assignmentList.add(CS2103_TP);
        Assignment editedAssignment = new AssignmentBuilder(CS2103_TP).withStatus(VALID_STATUS_CS2103).build();
        assignmentList.setAssignment(CS2103_TP, editedAssignment);
        AssignmentList expectedAssignmentList = new AssignmentList();
        expectedAssignmentList.add(editedAssignment);
        assertEquals(expectedAssignmentList, assignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasDifferentIdentity_success() {
        assignmentList.add(CS2103_TP);
        assignmentList.setAssignment(CS2103_TP, CS3243_TUT);
        AssignmentList expectedAssignmentList = new AssignmentList();
        expectedAssignmentList.add(CS3243_TUT);
        assertEquals(expectedAssignmentList, assignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasNonUniqueIdentity_throwsDuplicateAssignmentException() {
        assignmentList.add(CS2103_TP);
        assignmentList.add(CS3243_TUT);
        assertThrows(DuplicateAssignmentException.class, () -> assignmentList.setAssignment(CS2103_TP, CS3243_TUT));
    }

    @Test
    public void remove_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.remove(null));
    }

    @Test
    public void remove_assignmentDoesNotExist_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> assignmentList.remove(CS2103_TP));
    }

    @Test
    public void remove_existingAssignment_removesAssignment() {
        assignmentList.add(CS2103_TP);
        assignmentList.remove(CS2103_TP);
        AssignmentList expectedAssignmentList = new AssignmentList();
        assertEquals(expectedAssignmentList, assignmentList);
    }

    @Test
    public void setAssignments_nullAssignmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.setAssignments((AssignmentList) null));
    }

    @Test
    public void setAssignments_uniqueAssignmentList_replacesOwnListWithProvidedAssignmentList() {
        assignmentList.add(CS2103_TP);
        AssignmentList expectedAssignmentList = new AssignmentList();
        expectedAssignmentList.add(CS3243_TUT);
        assignmentList.setAssignments(expectedAssignmentList);
        assertEquals(expectedAssignmentList, assignmentList);
    }

    @Test
    public void setAssignments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> assignmentList.setAssignments((List<Assignment>) null));
    }

    @Test
    public void setAssignments_list_replacesOwnListWithProvidedList() {
        assignmentList.add(CS2103_TP);
        List<Assignment> providedAssignmentList = Collections.singletonList(CS3243_TUT);
        assignmentList.setAssignments(providedAssignmentList);
        AssignmentList expectedAssignmentList = new AssignmentList();
        expectedAssignmentList.add(CS3243_TUT);
        assertEquals(expectedAssignmentList, assignmentList);
    }

    @Test
    public void setAssignments_listWithDuplicateAssignments_throwsDuplicateAssignmentException() {
        List<Assignment> listWithDuplicateAssignments = Arrays.asList(CS2103_TP, CS2103_TP);
        assertThrows(DuplicateAssignmentException.class, () ->
                assignmentList.setAssignments(listWithDuplicateAssignments));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> assignmentList.asUnmodifiableObservableList().remove(0));
    }
}
