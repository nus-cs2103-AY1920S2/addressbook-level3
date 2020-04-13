package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_CONSULT1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CONSULT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.consults.EditConsultCommand;
import seedu.address.testutil.EditConsultDescriptorBuilder;

public class EditConsultDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditConsultCommand.EditConsultDescriptor descriptorWithSameValues =
            new EditConsultCommand.EditConsultDescriptor(DESC_CONSULT1);

        assertTrue(DESC_CONSULT1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CONSULT1.equals(DESC_CONSULT1));

        // null -> returns false
        assertFalse(DESC_CONSULT1.equals(null));

        // different types -> returns false
        assertFalse(DESC_CONSULT1.equals(5));

        // different values -> returns false
        assertFalse(DESC_CONSULT1.equals(DESC_CONSULT2));

        // different beginTime -> returns false
        EditConsultCommand.EditConsultDescriptor editedConsult1 = new EditConsultDescriptorBuilder(DESC_CONSULT1)
            .withBeginDateTime(VALID_START_TIME_2).build();
        assertFalse(DESC_CONSULT1.equals(editedConsult1));

        // different matric number -> returns false
        editedConsult1 = new EditConsultDescriptorBuilder(DESC_CONSULT1).withEndDateTime(VALID_END_TIME_2).build();
        assertFalse(DESC_CONSULT1.equals(editedConsult1));

        // different location -> returns false
        editedConsult1 = new EditConsultDescriptorBuilder(DESC_CONSULT1).withLocation(VALID_PLACE_2).build();
        assertFalse(DESC_CONSULT1.equals(editedConsult1));
    }
}
