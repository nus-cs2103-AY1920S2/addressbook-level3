package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKLOAD_CS3243;

import org.junit.jupiter.api.Test;

import seedu.address.logic.EditAssignmentDescriptor;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditAssignmentDescriptor descriptorWithSameValues = new EditAssignmentDescriptor(DESC_CS2103);
        assertTrue(DESC_CS2103.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2103.equals(DESC_CS2103));

        // null -> returns false
        assertFalse(DESC_CS2103.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2103.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2103.equals(DESC_CS3243));

        // different title -> returns false
        EditAssignmentDescriptor editedCs2103 =
            new EditAssignmentDescriptorBuilder(DESC_CS2103).withTitle(VALID_TITLE_CS3243).build();
        assertFalse(DESC_CS2103.equals(editedCs2103));

        // different deadline -> returns false
        editedCs2103 = new EditAssignmentDescriptorBuilder(DESC_CS2103).withDeadline(VALID_DEADLINE_CS3243).build();
        assertFalse(DESC_CS2103.equals(editedCs2103));

        // different workload -> returns false
        editedCs2103 = new EditAssignmentDescriptorBuilder(DESC_CS2103).withWorkload(VALID_WORKLOAD_CS3243).build();
        assertFalse(DESC_CS2103.equals(editedCs2103));

        // different status -> returns false
        editedCs2103 = new EditAssignmentDescriptorBuilder(DESC_CS2103).withStatus(VALID_STATUS_CS3243).build();
        assertFalse(DESC_CS2103.equals(editedCs2103));
    }
}
