package seedu.address.model.facilitator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.ALICE;
import static seedu.address.testutil.TypicalFacilitators.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FacilitatorBuilder;

public class FacilitatorTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Facilitator facilitator = new FacilitatorBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> facilitator.getModuleCodes().remove(0));
    }

    @Test
    public void isSameFacilitator() {
        // same object -> returns true
        assertTrue(ALICE.isSameFacilitator(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameFacilitator(null));

        // different name -> returns false
        Facilitator editedAlice = new FacilitatorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameFacilitator(editedAlice));

        // same name, different attributes -> returns true
        editedAlice = new FacilitatorBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .build();
        assertTrue(ALICE.isSameFacilitator(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new FacilitatorBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withOffice(VALID_OFFICE_BOB)
                .build();
        assertTrue(ALICE.isSameFacilitator(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new FacilitatorBuilder(ALICE).withPhone(VALID_PHONE_BOB).withOffice(VALID_OFFICE_BOB)
                .build();
        assertTrue(ALICE.isSameFacilitator(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new FacilitatorBuilder(ALICE).withOffice(VALID_OFFICE_BOB)
                .build();
        assertTrue(ALICE.isSameFacilitator(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Facilitator aliceCopy = new FacilitatorBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different facilitator -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Facilitator editedAlice = new FacilitatorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new FacilitatorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new FacilitatorBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different office -> returns false
        editedAlice = new FacilitatorBuilder(ALICE).withOffice(VALID_OFFICE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different module codes -> returns false
        editedAlice = new FacilitatorBuilder(ALICE).withModuleCodes(VALID_MODULE_CODE_CS2101).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
