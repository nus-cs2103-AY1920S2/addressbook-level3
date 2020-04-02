package tatracker.logic.commands.student;

import static tatracker.logic.commands.CommandTestUtil.DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.testutil.student.EditStudentDescriptorBuilder;

public class EditStudentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStudentDescriptor descriptorWithSameValues =
                new EditStudentCommand.EditStudentDescriptor(DESC_AMY);
        Assertions.assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        Assertions.assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        Assertions.assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        Assertions.assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditStudentCommand.EditStudentDescriptor editedAmy =
                new EditStudentDescriptorBuilder(DESC_AMY)
                        .withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY)
                .withPhone(VALID_PHONE_BOB).build();
        Assertions.assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY)
                .withEmail(VALID_EMAIL_BOB).build();
        Assertions.assertFalse(DESC_AMY.equals(editedAmy));

        // different matric -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY)
                .withMatric(VALID_MATRIC_BOB).build();
        Assertions.assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY)
                .withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(DESC_AMY.equals(editedAmy));
    }
}
