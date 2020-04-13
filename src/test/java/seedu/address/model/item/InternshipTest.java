package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternship.GOOGLE;
import static seedu.address.testutil.TypicalInternship.NINJA_VAN;
import static seedu.address.testutil.TypicalInternship.PAYPAL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InternshipBuilder;

public class InternshipTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Internship internship = new InternshipBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> internship.getTags().remove(0));
    }

    @Test
    public void isSameInternship() {
        // same object -> returns true
        assertTrue(PAYPAL.isSame(PAYPAL));

        // null -> returns false
        assertFalse(PAYPAL.isSame(null));

        // different role and description -> returns false
        Internship editedPaypal = new InternshipBuilder(PAYPAL).withRole(VALID_INTERNSHIP_ROLE_FRONTEND)
                .withDescription(VALID_INTERNSHIP_DESCRIPTION).build();
        assertFalse(PAYPAL.isSame(editedPaypal));

        // different name -> returns false
        editedPaypal = new InternshipBuilder(PAYPAL).withName(VALID_INTERNSHIP_NAME_GOOGLE).build();
        assertFalse(PAYPAL.isSame(editedPaypal));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Internship ninjaCopy = new InternshipBuilder(NINJA_VAN).build();
        assertTrue(NINJA_VAN.equals(ninjaCopy));

        // same object -> returns true
        assertTrue(NINJA_VAN.equals(NINJA_VAN));

        // null -> returns false
        assertFalse(NINJA_VAN.equals(null));

        // different type -> returns false
        assertFalse(NINJA_VAN.equals(5));

        // different internship -> returns false
        assertFalse(NINJA_VAN.equals(GOOGLE));

        // different name -> returns false
        Internship editedNinja = new InternshipBuilder(NINJA_VAN).withName(VALID_INTERNSHIP_NAME_GOOGLE).build();
        assertFalse(NINJA_VAN.equals(editedNinja));

        // different role -> returns false
        editedNinja = new InternshipBuilder(NINJA_VAN).withRole(VALID_INTERNSHIP_ROLE_FRONTEND).build();
        assertFalse(NINJA_VAN.equals(editedNinja));

        // different from -> returns false
        editedNinja = new InternshipBuilder(NINJA_VAN).withFrom("05-2019").build();
        assertFalse(NINJA_VAN.equals(editedNinja));

        // different to -> returns false
        editedNinja = new InternshipBuilder(NINJA_VAN).withTo("02-2018").build();
        assertFalse(NINJA_VAN.equals(editedNinja));

        // different tags -> returns false
        editedNinja = new InternshipBuilder(NINJA_VAN).withTags(VALID_TAG_JAVA).build();
        assertFalse(NINJA_VAN.equals(editedNinja));
    }

}
