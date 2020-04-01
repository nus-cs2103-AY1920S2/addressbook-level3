package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.DESC_EXAM;
import static nasa.logic.commands.CommandTestUtil.DESC_HWK;
import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_TUTORIAL;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_NOTES_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.EditActivityCommand.EditActivityDescriptor;
import nasa.testutil.EditActivityDescriptorBuilder;

public class EditActivityDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditActivityDescriptor descriptorWithSameValues = new EditActivityDescriptor(DESC_EXAM);
        assertTrue(DESC_EXAM.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EXAM.equals(DESC_EXAM));

        // null -> returns false
        assertFalse(DESC_EXAM.equals(null));

        // different types -> returns false
        assertFalse(DESC_EXAM.equals(5));

        // different values -> returns false
        assertFalse(DESC_EXAM.equals(DESC_HWK));

        // different name -> returns false
        EditActivityDescriptor editedExam = new EditActivityDescriptorBuilder(DESC_EXAM)
                .withName(VALID_ACTIVITY_NAME_TUTORIAL).build();
        assertFalse(DESC_EXAM.equals(editedExam));

        // different date -> returns false
        editedExam = new EditActivityDescriptorBuilder(DESC_EXAM).withDate(VALID_DATE_TEST_2).build();
        assertFalse(DESC_EXAM.equals(editedExam));

        // different note -> returns false
        editedExam = new EditActivityDescriptorBuilder(DESC_EXAM).withNote(VALID_NOTES_TEST_2).build();
        assertFalse(DESC_EXAM.equals(editedExam));

        // different priority -> returns false
        editedExam = new EditActivityDescriptorBuilder(DESC_EXAM).withPriority(VALID_PRIORITY_LOW).build();
        assertFalse(DESC_EXAM.equals(editedExam));
    }
}
