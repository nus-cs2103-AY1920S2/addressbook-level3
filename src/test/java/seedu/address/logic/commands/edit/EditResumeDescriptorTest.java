package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.testutil.TypicalEditResumeDescriptor.ME_RESUME;
import static seedu.address.testutil.TypicalEditResumeDescriptor.SE_RESUME;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditResumeDescriptorBuilder;

public class EditResumeDescriptorTest {
    @Test
    public void isAnyFieldMissing_noFieldEdited_returnsTrue() {
        assertFalse(new EditResumeDescriptorBuilder().build().isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditResumeDescriptor descriptorWithSameValues = new EditResumeDescriptor(ME_RESUME);
        assertEquals(ME_RESUME, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(ME_RESUME, ME_RESUME);

        // null -> returns false
        assertNotEquals(null, ME_RESUME);

        // different types -> returns false
        assertNotEquals(5, ME_RESUME);

        // different values -> returns false
        assertNotEquals(ME_RESUME, SE_RESUME);

        // different name -> returns false
        EditResumeDescriptor editedMe = new EditResumeDescriptorBuilder(ME_RESUME)
                .withName(VALID_RESUME_NAME_SE).build();
        assertNotEquals(ME_RESUME, editedMe);

        // different tags -> returns false
        editedMe = new EditResumeDescriptorBuilder(ME_RESUME)
                .withTags(VALID_TAG_TECH).build();
        assertNotEquals(ME_RESUME, editedMe);
    }

    @Test
    public void field_isAnyNonNull_falseIfAllNull() {
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptor();
        assertEquals(editResumeDescriptor.isAnyFieldEdited(), false);
    }
}
