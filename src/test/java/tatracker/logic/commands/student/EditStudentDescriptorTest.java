//@@author fatin99

package tatracker.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tatracker.logic.commands.CommandTestUtil.DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.testutil.student.EditStudentDescriptorBuilder;

public class EditStudentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStudentDescriptor descriptorWithSameValues =
                new EditStudentCommand.EditStudentDescriptor(DESC_AMY);
        assertEquals(DESC_AMY, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_AMY, DESC_AMY);

        // null -> returns false
        assertNotEquals(null, DESC_AMY);

        // different types -> returns false
        assertNotEquals(5, DESC_AMY);

        // different values -> returns false
        assertNotEquals(DESC_AMY, DESC_BOB);

        // different name -> returns false
        EditStudentDescriptor editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different phone -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different email -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different tags -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different rating -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withRating(VALID_RATING_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);
    }
}
