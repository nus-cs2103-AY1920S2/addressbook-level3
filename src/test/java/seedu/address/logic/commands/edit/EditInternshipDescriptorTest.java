package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_NINJAVAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NINJA_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_NINJA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO_2;
import static seedu.address.testutil.TypicalEditInternshipDescriptor.NINJA_VAN;
import static seedu.address.testutil.TypicalEditInternshipDescriptor.PAY_PAL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditInternshipDescriptorBuilder;

public class EditInternshipDescriptorTest {
    @Test
    public void isAnyFieldMissing_noFieldEdited_returnsTrue() {
        assertFalse(new EditInternshipDescriptorBuilder().build().isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditInternshipDescriptor descriptorWithSameValues = new EditInternshipDescriptor(PAY_PAL);
        assertEquals(PAY_PAL, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(PAY_PAL, PAY_PAL);

        // null -> returns false
        assertNotEquals(null, PAY_PAL);

        // different types -> returns false
        assertNotEquals(5, PAY_PAL);

        // different values -> returns false
        assertNotEquals(PAY_PAL, NINJA_VAN);

        // different name -> returns false
        EditInternshipDescriptor editedPayPal = new EditInternshipDescriptorBuilder(PAY_PAL)
                .withName(VALID_INTERNSHIP_NAME_NINJAVAN).build();
        assertNotEquals(PAY_PAL, editedPayPal);

        // different role -> returns false
        editedPayPal = new EditInternshipDescriptorBuilder(PAY_PAL)
                .withRole(VALID_INTERNSHIP_ROLE_NINJA).build();
        assertNotEquals(PAY_PAL, editedPayPal);

        // different from -> returns false
        editedPayPal = new EditInternshipDescriptorBuilder(PAY_PAL)
                .withFrom(VALID_FROM_2).build();
        assertNotEquals(PAY_PAL, editedPayPal);

        // different to -> returns false
        editedPayPal = new EditInternshipDescriptorBuilder(PAY_PAL)
                .withTo(VALID_TO_2).build();
        assertNotEquals(PAY_PAL, editedPayPal);

        // different description -> returns false
        editedPayPal = new EditInternshipDescriptorBuilder(PAY_PAL)
                .withDescription(VALID_INTERNSHIP_NINJA_DESCRIPTION).build();
        assertNotEquals(PAY_PAL, editedPayPal);

        // different tags -> returns false
        editedPayPal = new EditInternshipDescriptorBuilder(PAY_PAL)
                .withTags(VALID_TAG_UX, VALID_TAG_TECH).build();
        assertNotEquals(PAY_PAL, editedPayPal);
    }

    @Test
    public void field_isAnyNonNull_falseIfAllNull() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptor();
        assertEquals(editInternshipDescriptor.isAnyFieldEdited(), false);
    }
}
