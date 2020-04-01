package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RETURNS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BENSON;
import static seedu.address.testutil.TypicalReturnOrders.ALICE_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.BENSON_RETURN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.parcel.OrderContainsKeywordsPredicate;
import seedu.address.testutil.OrderBookBuilder;
import seedu.address.testutil.ReturnOrderBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new OrderBook(), modelManager.getOrderBook());
        assertEquals(new ReturnOrderBook(), modelManager.getReturnOrderBook());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setOrderBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setReturnOrderBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setOrderBookFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setReturnOrderBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setOrderBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setOrderBookFilePath(null));
        assertThrows(NullPointerException.class, () -> modelManager.setReturnOrderBookFilePath(null));
    }

    @Test
    public void setOrderBookFilePath_validPath_setsOrderBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setOrderBookFilePath(path);
        assertEquals(path, modelManager.getOrderBookFilePath());

        modelManager.setReturnOrderBookFilePath(path);
        assertEquals(path, modelManager.getReturnOrderBookFilePath());
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasOrder(null));
        assertThrows(NullPointerException.class, () -> modelManager.hasReturnOrder(null));
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(modelManager.hasOrder(ALICE));
        assertFalse(modelManager.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        modelManager.addOrder(ALICE);
        assertTrue(modelManager.hasOrder(ALICE));

        modelManager.addReturnOrder(ALICE_RETURN);
        assertTrue(modelManager.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredOrderList().remove(0));
    }

    @Test
    public void getFilteredReturnOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredReturnOrderList().remove(0));
    }

    @Test
    public void addReturnOrder_thenSetReturnOrderBook_returnCorrectReturnOrderBook() {
        modelManager.addReturnOrder(ALICE_RETURN);
        modelManager.setReturnOrderBook(new ReturnOrderBook());
        assertEquals(new ReturnOrderBook(), modelManager.getReturnOrderBook());
    }

    @Test
    public void equals() {
        OrderBook deliveryOrderBook = new OrderBookBuilder().withOrder(ALICE).withOrder(BENSON).build();
        ReturnOrderBook returnOrderBook = new ReturnOrderBookBuilder().withReturnOrder(ALICE_RETURN)
                .withReturnOrder(BENSON_RETURN).build();
        OrderBook differentDeliveryOrderBook = new OrderBook();
        ReturnOrderBook differentReturnOrderBook = new ReturnOrderBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(deliveryOrderBook, returnOrderBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(deliveryOrderBook, returnOrderBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different deliveryOrderBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDeliveryOrderBook, returnOrderBook, userPrefs)));

        // different returnOrderBook -> return false
        assertFalse(modelManager.equals(new ModelManager(deliveryOrderBook, differentReturnOrderBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredOrderList(new OrderContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(deliveryOrderBook, returnOrderBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setOrderBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(deliveryOrderBook, returnOrderBook, differentUserPrefs)));
    }
}
