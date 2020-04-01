package seedu.address.model.Parcel.returnorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReturnOrders.ALICE_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.BOB_RETURN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Parcel.exceptions.DuplicateReturnOrderException;
import seedu.address.model.Parcel.exceptions.OrderNotFoundException;
import seedu.address.testutil.ReturnOrderBuilder;

public class UniqueReturnOrderListTest {

    private final UniqueReturnOrderList uniqueReturnOrderList = new UniqueReturnOrderList();

    @Test
    public void contains_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReturnOrderList.contains(null));
    }

    @Test
    public void contains_orderNotInList_returnsFalse() {
        assertFalse(uniqueReturnOrderList.contains(ALICE_RETURN));
    }

    @Test
    public void contains_orderInList_returnsTrue() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        assertTrue(uniqueReturnOrderList.contains(ALICE_RETURN));
    }

    @Test
    public void contains_returnOrderWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(uniqueReturnOrderList.contains(editedAlice));
    }

    @Test
    public void add_nullReturnOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReturnOrderList.add(null));
    }

    @Test
    public void add_duplicateReturnOrder_throwsDuplicateReturnOrderException() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        assertThrows(DuplicateReturnOrderException.class, () -> uniqueReturnOrderList.add(ALICE_RETURN));
    }

    @Test
    public void setReturnOrder_nullTargetOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReturnOrderList.setReturnOrder(null, ALICE_RETURN));
    }

    @Test
    public void setReturnOrder_nullEditedOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReturnOrderList.setReturnOrder(ALICE_RETURN, null));
    }

    @Test
    public void setReturnOrder_targetOrderNotInList_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () ->
            uniqueReturnOrderList.setReturnOrder(ALICE_RETURN, ALICE_RETURN));
    }

    @Test
    public void setReturnOrder_editedOrderIsSameOrder_success() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        uniqueReturnOrderList.setReturnOrder(ALICE_RETURN, ALICE_RETURN);
        UniqueReturnOrderList expectedUniqueReturnOrderList = new UniqueReturnOrderList();
        expectedUniqueReturnOrderList.add(ALICE_RETURN);
        assertEquals(expectedUniqueReturnOrderList, uniqueReturnOrderList);
    }

    @Test
    public void setReturnOrder_editedOrderHasSameIdentity_success() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN).withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();
        uniqueReturnOrderList.setReturnOrder(ALICE_RETURN, editedAlice);
        UniqueReturnOrderList expectedUniqueReturnOrderList = new UniqueReturnOrderList();
        expectedUniqueReturnOrderList.add(editedAlice);
        assertEquals(expectedUniqueReturnOrderList, uniqueReturnOrderList);
    }

    @Test
    public void setReturnOrder_editedOrderHasDifferentIdentity_success() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        uniqueReturnOrderList.setReturnOrder(ALICE_RETURN, BOB_RETURN);
        UniqueReturnOrderList expectedUniqueReturnOrderList = new UniqueReturnOrderList();
        expectedUniqueReturnOrderList.add(BOB_RETURN);
        assertEquals(expectedUniqueReturnOrderList, uniqueReturnOrderList);
    }

    @Test
    public void setReturnOrder_editedOrderHasNonUniqueIdentity_throwsDuplicateOrderException() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        uniqueReturnOrderList.add(BOB_RETURN);
        assertThrows(DuplicateReturnOrderException.class, () ->
            uniqueReturnOrderList.setReturnOrder(ALICE_RETURN, BOB_RETURN));
    }

    @Test
    public void remove_nullReturnOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReturnOrderList.remove(null));
    }

    @Test
    public void remove_returnOrderDoesNotExist_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> uniqueReturnOrderList.remove(ALICE_RETURN));
    }

    @Test
    public void remove_existingReturnOrder_removeRemoveOrder() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        uniqueReturnOrderList.remove(ALICE_RETURN);
        UniqueReturnOrderList expectedUniqueReturnOrderList = new UniqueReturnOrderList();
        assertEquals(expectedUniqueReturnOrderList, uniqueReturnOrderList);
    }

    @Test
    public void setReturnOrders_nullUniqueReturnOrderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueReturnOrderList.setReturns((UniqueReturnOrderList) null));
    }

    @Test
    public void setReturnOrders_uniqueReturnOrderList_throwsNullPointerException() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        UniqueReturnOrderList expectedUniqueReturnOrderList = new UniqueReturnOrderList();
        expectedUniqueReturnOrderList.add(BOB_RETURN);
        uniqueReturnOrderList.setReturns(expectedUniqueReturnOrderList);
        assertEquals(expectedUniqueReturnOrderList, uniqueReturnOrderList);
    }

    @Test
    public void setReturnOrders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReturnOrderList.setReturns((List<ReturnOrder>) null));
    }

    @Test
    public void setReturnOrders_list_replacesOwnListWithProvidedList() {
        uniqueReturnOrderList.add(ALICE_RETURN);
        List<ReturnOrder> returnOrderList = Collections.singletonList(BOB_RETURN);
        uniqueReturnOrderList.setReturns(returnOrderList);
        UniqueReturnOrderList expectedUniqueReturnOrderList = new UniqueReturnOrderList();
        expectedUniqueReturnOrderList.add(BOB_RETURN);
        assertEquals(expectedUniqueReturnOrderList, uniqueReturnOrderList);
    }

    @Test
    public void setReturnOrders_listWithDuplicateOrders_throwsDuplicateOrderException() {
        List<ReturnOrder> listWithDuplicateOrders = Arrays.asList(ALICE_RETURN, ALICE_RETURN);
        assertThrows(DuplicateReturnOrderException.class, () ->
            uniqueReturnOrderList.setReturns(listWithDuplicateOrders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueReturnOrderList.asUnmodifiableObservableList().remove(0));
    }
}
