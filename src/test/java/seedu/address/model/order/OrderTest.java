package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Order order = new OrderBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> order.getTags().remove(0));
    }

    @Test
    public void isSameOrder() {
        // same object -> returns true
        assertTrue(ALICE.isSameOrder(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameOrder(null));

        // different phone and address -> returns false
        Order editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // different name -> returns false
        editedAlice = new OrderBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameOrder(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameOrder(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameOrder(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Order aliceCopy = new OrderBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Order editedAlice = new OrderBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different TID -> returns false
        editedAlice = new OrderBuilder(ALICE).withTID(VALID_TID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new OrderBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
