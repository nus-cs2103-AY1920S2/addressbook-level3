package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalEditNoteDescriptor.FINISH_CS_2103;
import static seedu.address.testutil.TypicalEditNoteDescriptor.FINISH_HOMEWORK;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditNoteDescriptorBuilder;

public class EditNoteDescriptorTest {
    @Test
    public void isAnyFieldMissing_noFieldEdited_returnsTrue() {
        assertFalse(new EditNoteDescriptorBuilder().build().isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditNoteDescriptor descriptorWithSameValues = new EditNoteDescriptor(FINISH_HOMEWORK);
        assertEquals(FINISH_HOMEWORK, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(FINISH_HOMEWORK, FINISH_HOMEWORK);

        // null -> returns false
        assertNotEquals(null, FINISH_HOMEWORK);

        // different types -> returns false
        assertNotEquals(5, FINISH_HOMEWORK);

        // different values -> returns false
        assertNotEquals(FINISH_HOMEWORK, FINISH_CS_2103);

        // different name -> returns false
        EditNoteDescriptor editedFinishHomework = new EditNoteDescriptorBuilder(FINISH_HOMEWORK)
                .withName("Finish CS2103").build();
        assertNotEquals(FINISH_HOMEWORK, editedFinishHomework);

        // different time -> returns false
        editedFinishHomework = new EditNoteDescriptorBuilder(FINISH_HOMEWORK)
                .withTime("12-2020").build();
        assertNotEquals(FINISH_HOMEWORK, editedFinishHomework);

        // different tags -> returns false
        editedFinishHomework = new EditNoteDescriptorBuilder(FINISH_HOMEWORK)
                .withTags("urgent").build();
        assertNotEquals(FINISH_HOMEWORK, editedFinishHomework);
    }

    @Test
    public void field_isAnyNonNull_falseIfAllNull() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();
        assertEquals(editNoteDescriptor.isAnyFieldEdited(), false);
    }
}
