package seedu.nova.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD:src/test/java/seedu/nova/logic/commands/EditPersonDescriptorTest.java
import seedu.nova.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.nova.testutil.EditPersonDescriptorBuilder;
=======
import seedu.nova.logic.commands.AbEditCommand.EditPersonDescriptor;
import seedu.nova.testutil.EditPersonDescriptorBuilder;
>>>>>>> c6c0bb78e07ef00942b0263e80b55d6c724c2c2b:src/test/java/seedu/address/logic/commands/EditPersonDescriptorTest.java

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
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
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
