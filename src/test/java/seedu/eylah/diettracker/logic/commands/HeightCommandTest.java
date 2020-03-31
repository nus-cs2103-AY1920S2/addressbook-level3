package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.eylah.addressbook.logic.commands.EditCommand;
import seedu.eylah.addressbook.testutil.EditPersonDescriptorBuilder;

public class HeightCommandTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditPersonDescriptor descriptorWithSameValues = new EditCommand.EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
