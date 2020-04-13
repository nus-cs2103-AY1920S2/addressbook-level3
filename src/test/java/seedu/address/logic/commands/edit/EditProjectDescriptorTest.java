package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_DUKE;
import static seedu.address.testutil.TypicalEditProjectDescriptor.DUKE;
import static seedu.address.testutil.TypicalEditProjectDescriptor.ORBITAL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditProjectDescriptorBuilder;

public class EditProjectDescriptorTest {
    @Test
    public void isAnyFieldMissing_noFieldEdited_returnsTrue() {
        assertFalse(new EditProjectDescriptorBuilder().build().isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditProjectDescriptor descriptorWithSameValues = new EditProjectDescriptor(ORBITAL);
        assertEquals(ORBITAL, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(ORBITAL, ORBITAL);

        // null -> returns false
        assertNotEquals(null, ORBITAL);

        // different types -> returns false
        assertNotEquals(5, ORBITAL);

        // different values -> returns false
        assertNotEquals(ORBITAL, DUKE);

        // different name -> returns false
        EditProjectDescriptor editedOrbital = new EditProjectDescriptorBuilder(ORBITAL)
                .withName(VALID_PROJECT_NAME_DUKE).build();
        assertNotEquals(ORBITAL, editedOrbital);

        // different time -> returns false
        editedOrbital = new EditProjectDescriptorBuilder(ORBITAL)
                .withTime(VALID_TIME_2).build();
        assertNotEquals(ORBITAL, editedOrbital);

        // different website -> returns false
        editedOrbital = new EditProjectDescriptorBuilder(ORBITAL)
                .withWebsite(VALID_WEBSITE_DUKE).build();
        assertNotEquals(ORBITAL, editedOrbital);

        // different description -> returns false
        editedOrbital = new EditProjectDescriptorBuilder(ORBITAL)
                .withDescription(VALID_DESCRIPTION_DUKE).build();
        assertNotEquals(ORBITAL, editedOrbital);

        // different tags -> returns false
        editedOrbital = new EditProjectDescriptorBuilder(ORBITAL)
                .withTags(VALID_TAG_TECH, VALID_TAG_JAVA).build();
        assertNotEquals(ORBITAL, editedOrbital);
    }

    @Test
    public void field_isAnyNonNull_falseIfAllNull() {
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();
        assertEquals(editProjectDescriptor.isAnyFieldEdited(), false);
    }
}

