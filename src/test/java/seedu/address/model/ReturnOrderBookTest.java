package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReturnOrders.ALICE_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.parcel.exceptions.DuplicateReturnOrderException;
import seedu.address.model.parcel.returnorder.ReturnOrder;
import seedu.address.testutil.ReturnOrderBuilder;

public class ReturnOrderBookTest {

    private final ReturnOrderBook returnOrderBook = new ReturnOrderBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), returnOrderBook.getReturnOrderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> returnOrderBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyReturnOrderBook_replacesData() {
        ReturnOrderBook newData = getTypicalReturnOrderBook();
        returnOrderBook.resetData(newData);
        assertEquals(newData, returnOrderBook);
    }

    @Test
    public void resetData_withDuplicateReturnOrders_throwsDuplicateReturnOrderException() {
        // Two orders with the same identity fields
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC)
                .build();
        List<ReturnOrder> newReturnOrders = Arrays.asList(ALICE_RETURN, editedAlice);
        ReturnOrderBookStub newData = new ReturnOrderBookStub(newReturnOrders);

        assertThrows(DuplicateReturnOrderException.class, () -> returnOrderBook.resetData(newData));
    }

    @Test
    public void hasReturnOrder_nullReturnOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> returnOrderBook.hasReturnOrder(null));
    }

    @Test
    public void hasReturnOrder_returnOrderNotInReturnOrderBook_returnsFalse() {
        assertFalse(returnOrderBook.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasReturnOrder_returnOrderInReturnOrderBook_returnsTrue() {
        returnOrderBook.addReturnOrder(ALICE_RETURN);
        assertTrue(returnOrderBook.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasReturnOrder_returnOrderWithSameIdentityFieldsInReturnOrderBook_returnsTrue() {
        returnOrderBook.addReturnOrder(ALICE_RETURN);
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN)
                .withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC)
                .build();
        assertTrue(returnOrderBook.hasReturnOrder(editedAlice));
    }

    @Test
    public void getReturnOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> returnOrderBook.getReturnOrderList().remove(0));
    }

    /**
     * A stub ReadOnlyOrderBook whose orders list can violate interface constraints.
     */
    private static class ReturnOrderBookStub implements ReadOnlyReturnOrderBook {
        private final ObservableList<ReturnOrder> returnOrders = FXCollections.observableArrayList();

        ReturnOrderBookStub(Collection<ReturnOrder> returnOrders) {
            this.returnOrders.setAll(returnOrders);
        }

        @Override
        public ObservableList<ReturnOrder> getReturnOrderList() {
            return returnOrders;
        }

    }

}
