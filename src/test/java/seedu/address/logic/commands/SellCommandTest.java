package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOOD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GOOD;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Price;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.version.StateNotFoundException;

public class SellCommandTest {
    private static String VALID_GOOD_NAME_STRING = "Test good name";
    private static String VALID_DIFF_GOOD_NAME_STRING = "Different Test good name";
    private static String VALID_GOOD_QUANTITY_STRING = "10";
    private static String VALID_DIFF_GOOD_QUANTITY_STRING = "20";
    private static String VALID_INSUFFICIENT_QUANTITY_STRING = "5";
    private static String VALID_ZERO_QUANTITY_STRING = "0";
    private static String INVALID_INDEX_GOOD_STRING = "-1";

    private static String VALID_PRICE_STRING = "5.00";
    private static String VALID_DIFF_PRICE_STRING = "6.69";

    private static GoodName VALID_GOOD_NAME = new GoodName(VALID_GOOD_NAME_STRING);
    private static GoodName VALID_DIFF_GOOD_NAME = new GoodName(VALID_DIFF_GOOD_NAME_STRING);

    private static GoodQuantity VALID_GOOD_QUANTITY = new GoodQuantity(VALID_GOOD_QUANTITY_STRING);
    private static GoodQuantity VALID_DIFF_GOOD_QUANTITY = new GoodQuantity(VALID_DIFF_GOOD_QUANTITY_STRING);
    private static GoodQuantity VALID_INSUFFICIENT_QUANTITY = new GoodQuantity(VALID_INSUFFICIENT_QUANTITY_STRING);
    private static GoodQuantity VALID_ZERO_QUANTITY = new GoodQuantity(VALID_ZERO_QUANTITY_STRING);

    private static Good soldGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static Good soldGoodDiffGoodName = Good.newGoodEntry(VALID_DIFF_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static Good soldGoodDiffGoodQuantity = Good.newGoodEntry(VALID_GOOD_NAME, VALID_DIFF_GOOD_QUANTITY);
    private static Good existingGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_GOOD_QUANTITY);
    private static Good sellExistingGoodResultGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_ZERO_QUANTITY);
    private static Good insufficientQuantityGood = Good.newGoodEntry(VALID_GOOD_NAME, VALID_INSUFFICIENT_QUANTITY);

    private static Price VALID_PRICE = new Price(VALID_PRICE_STRING);
    private static Price VALID_DIFF_PRICE = new Price(VALID_DIFF_PRICE_STRING);

    @Test
    public void constructor_nullSupplier_throwsNullPointerException() {
        // missing quantity
        assertThrows(NullPointerException.class,
                () -> new SellCommand(null, VALID_PRICE, INDEX_FIRST_GOOD));

        // midding price
        assertThrows(NullPointerException.class,
                () -> new SellCommand(VALID_GOOD_QUANTITY, null, INDEX_FIRST_GOOD));

        // midding index
        assertThrows(NullPointerException.class,
                () -> new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, null));
    }

    @Test
    public void equals() {
        SellCommand sellCommand = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, INDEX_FIRST_GOOD);

        SellCommand sellCommandDiffQty = new SellCommand(VALID_DIFF_GOOD_QUANTITY, VALID_PRICE, INDEX_FIRST_GOOD);
        SellCommand sellCommandDiffPrice = new SellCommand(VALID_GOOD_QUANTITY, VALID_DIFF_PRICE, INDEX_FIRST_GOOD);
        SellCommand sellCommandDiffIndex = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, INDEX_SECOND_GOOD);

        // same object -> returns true
        assertTrue(sellCommand.equals(sellCommand));

        // same values -> returns true
        SellCommand sellCommandCopy = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, INDEX_FIRST_GOOD);
        assertTrue(sellCommand.equals(sellCommandCopy));

        // different types -> returns false
        assertFalse(sellCommand.equals(1));
        assertFalse(sellCommand.equals("string"));

        // null -> returns false
        assertFalse(sellCommand.equals(null));

        // different GoodQuantity -> returns false
        assertFalse(sellCommand.equals(sellCommandDiffQty));

        // different price -> returns false
        assertFalse(sellCommand.equals(sellCommandDiffPrice));

        // different index -> returns false
        assertFalse(sellCommand.equals(sellCommandDiffIndex));
    }

    @Test
    public void execute_sellExistingGood_sellSuccessful() throws CommandException {
        ModelStubWithExistingGood modelStub = new ModelStubWithExistingGood();

        CommandResult commandResult = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, INDEX_FIRST_GOOD)
                .execute(modelStub);

        String expectedFeedback = String.format(SellCommand.MESSAGE_SUCCESS,
                soldGood.getGoodQuantity().goodQuantity, soldGood.getGoodName().fullGoodName,
                VALID_PRICE.toString());

        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(sellExistingGoodResultGood), modelStub.inventory);
    }

    @Test
    public void execute_sellMoreThanInventoryQuantity_throwCommandException() {
        ModelStubInsufficientInventory modelStub = new ModelStubInsufficientInventory();

        SellCommand sellCommand = new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, INDEX_FIRST_GOOD);

        assertThrows(CommandException.class,
                SellCommand.MESSAGE_INSUFFICIENT_QUANTITY, () -> sellCommand.execute(modelStub));
    }

    @Test
    public void execute_validTransaction_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        modelStub.addGood(soldGood);
        new SellCommand(VALID_GOOD_QUANTITY, VALID_PRICE, INDEX_FIRST_GOOD).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    private class ModelStubInsufficientInventory extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();

        public ModelStubInsufficientInventory() {
            inventory.add(insufficientQuantityGood);
        }

        @Override
        public boolean hasGood(Good good) {
            return inventory.stream().anyMatch(good::isSameGood);
        }

        @Override
        public int indexOfGood(Good good) {
            return 0;
        }

        @Override
        public ObservableList<Good> getFilteredGoodList() {
            ObservableList<Good> goodsList = FXCollections.observableArrayList();
            goodsList.add(insufficientQuantityGood);
            return goodsList;
        }

        @Override
        public void addTransaction(Transaction transaction) {
            //dummy method
        }
    }

    private class ModelStubWithExistingGood extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();

        public ModelStubWithExistingGood() {
            inventory.add(existingGood);
        }

        @Override
        public boolean hasGood(Good good) {
            requireNonNull(good);
            return inventory.stream().anyMatch(good::isSameGood);
        }

        @Override
        public int indexOfGood(Good good) {
            return 0;
        }

        @Override
        public ObservableList<Good> getFilteredGoodList() {
            ObservableList<Good> goodsList = FXCollections.observableArrayList();
            goodsList.add(existingGood);
            return goodsList;
        }

        @Override
        public void setGood(Good target, Good editedGood) {
            // test calling this method should modify the only good in inventory
            inventory.clear();
            inventory.add(editedGood);
        }

        @Override
        public void addTransaction(Transaction transaction) {
            //dummy method
        }
    }

    private class ModelStubEmptyInventory extends ModelStub {
        private ArrayList<Good> inventory = new ArrayList<>();

        @Override
        public boolean hasGood(Good good) {
            requireNonNull(good);
            return inventory.stream().anyMatch(good::isSameGood);
        }

        @Override
        public void addGood(Good good) {
            inventory.add(good);
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyList<Supplier> addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyList<Supplier> getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSupplier(Supplier target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSupplier(Supplier target, Supplier editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInventoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryFilePath(Path inventoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventory(ReadOnlyList<Good> inventory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyList<Good> getInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGood(Good good) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGood(Good target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGood(Good good) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int indexOfGood(Good good) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGood(Good target, Good editedGood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Good> getFilteredGoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGoodList(Predicate<Good> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTransactionHistoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactionHistoryFilePath(Path transactionHistoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactionHistory(ReadOnlyList<Transaction> transactionHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyList<Transaction> getTransactionHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit() {
            return;
        }

        @Override
        public void undo() throws StateNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redo() throws StateNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

    }
}


