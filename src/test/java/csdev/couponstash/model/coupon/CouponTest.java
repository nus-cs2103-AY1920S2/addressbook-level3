package csdev.couponstash.model.coupon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static csdev.couponstash.testutil.Assert.assertThrows;

import csdev.couponstash.logic.commands.CommandTestUtil;
import csdev.couponstash.testutil.Assert;
import csdev.couponstash.testutil.PersonBuilder;
import csdev.couponstash.testutil.TypicalPersons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CouponTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Coupon coupon = new PersonBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> coupon.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        Assertions.assertTrue(TypicalPersons.ALICE.isSamePerson(TypicalPersons.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Coupon editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Coupon aliceCopy = new PersonBuilder(TypicalPersons.ALICE).build();
        Assertions.assertTrue(TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalPersons.ALICE.equals(TypicalPersons.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.equals(5));

        // different coupon -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        Coupon editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }
}
