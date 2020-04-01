package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.Parcel.order.Order;
import seedu.address.testutil.OrderBuilder;

public class OrderTest {

    @Test
    public void isSameOrder() {
        // same object -> returns true
        assertTrue(ALICE.isSameParcel(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameParcel(null));

        // different transaction ID -> returns false
        Order editedAlice = new OrderBuilder(ALICE).withTid(VALID_TID_BOB).build();

        assertFalse(ALICE.isSameParcel(editedAlice));

        // different name -> returns false
        editedAlice = new OrderBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameParcel(editedAlice));

        // different phone -> returns false
        editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSameParcel(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withCash(VALID_COD_BOB).withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(ALICE.isSameParcel(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new OrderBuilder(ALICE).withWarehouse(VALID_WAREHOUSE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(ALICE.isSameParcel(editedAlice));

        // same name, same phone, same warehouse, different attributes -> returns true
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(ALICE.isSameParcel(editedAlice));
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
        editedAlice = new OrderBuilder(ALICE).withTid(VALID_TID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different warehouse -> returns false
        editedAlice = new OrderBuilder(ALICE).withWarehouse(VALID_WAREHOUSE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different item types -> returns false
        editedAlice = new OrderBuilder(ALICE).withItemType(VALID_TYPE_PLASTIC).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
