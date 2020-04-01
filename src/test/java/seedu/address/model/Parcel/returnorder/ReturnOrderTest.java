package seedu.address.model.Parcel.returnorder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.testutil.TypicalReturnOrders.ALICE_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.BOB_RETURN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReturnOrderBuilder;

public class ReturnOrderTest {

    @Test
    public void isSameReturn() {
        // same object -> returns true
        assertTrue(ALICE_RETURN.isSameParcel(ALICE_RETURN));

        // null -> returns false
        assertFalse(ALICE_RETURN.isSameParcel(null));

        // different transaction ID -> returns false
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withTid(VALID_TID_BOB).build();

        assertFalse(ALICE_RETURN.isSameParcel(editedAlice));

        // different name -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_RETURN.isSameParcel(editedAlice));

        // different phone -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_RETURN.isSameParcel(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(ALICE_RETURN.isSameParcel(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withWarehouse(VALID_WAREHOUSE_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(ALICE_RETURN.isSameParcel(editedAlice));

        // same name, same phone, same warehouse, different attributes -> returns true
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(ALICE_RETURN.isSameParcel(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ReturnOrder aliceCopy = new ReturnOrderBuilder(ALICE_RETURN).build();
        assertTrue(ALICE_RETURN.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_RETURN.equals(ALICE_RETURN));

        // null -> returns false
        assertFalse(ALICE_RETURN.equals(null));

        // different type -> returns false
        assertFalse(ALICE_RETURN.equals(5));

        // different person -> returns false
        assertFalse(ALICE_RETURN.equals(BOB_RETURN));

        // different name -> returns false
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_RETURN.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_RETURN.equals(editedAlice));

        // different TID -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withTid(VALID_TID_BOB).build();
        assertFalse(ALICE_RETURN.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE_RETURN.equals(editedAlice));

        // different warehouse -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withWarehouse(VALID_WAREHOUSE_BOB).build();
        assertFalse(ALICE_RETURN.equals(editedAlice));

        // different item types -> returns false
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withItemType(VALID_TYPE_PLASTIC).build();
        assertFalse(ALICE_RETURN.equals(editedAlice));

        // same delivery status -> returns true
        editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withItemType(VALID_TYPE_PLASTIC).buildDelivered();
        aliceCopy.setDeliveryStatus(true);
        assertFalse(aliceCopy.equals(editedAlice));
    }
}
