package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.CommandTestUtil;
import csdev.couponstash.testutil.PersonBuilder;
import csdev.couponstash.testutil.TypicalPersons;

public class CouponTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Coupon coupon = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> coupon.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(TypicalPersons.ALICE.isSamePerson(TypicalPersons.ALICE));

        // null -> returns false
        assertFalse(TypicalPersons.ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Coupon editedAlice = new PersonBuilder(TypicalPersons.ALICE)
                .withPhone(CommandTestUtil.VALID_PHONE_BOB).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Coupon aliceCopy = new PersonBuilder(TypicalPersons.ALICE).build();
        assertTrue(TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TypicalPersons.ALICE.equals(TypicalPersons.ALICE));

        // null -> returns false
        assertFalse(TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        assertFalse(TypicalPersons.ALICE.equals(5));

        // different coupon -> returns false
        assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        Coupon editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }
}
