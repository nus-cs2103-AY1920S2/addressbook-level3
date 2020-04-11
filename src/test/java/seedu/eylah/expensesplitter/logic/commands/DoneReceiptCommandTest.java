package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.testutil.TypicalEntries;


public class DoneReceiptCommandTest {

    private static final Entry ENTRY_ONE = TypicalEntries.ENTRY_ONE;

    @Test
    public void execute_receiptIsAlreadyDone_markAsDoneUnsuccessful() {
        ModelStubReceiptToBeMadeDone modelStub = new ModelStubReceiptToBeMadeDone();
        //add an Entry to simulate user adding an item
        modelStub.addEntry(ENTRY_ONE);
        modelStub.getReceipt().makeDone();

        CommandResult commandResult = new DoneReceiptCommand().execute(modelStub);

        // confirm that receipt is marked as done
        assertTrue(modelStub.getReceipt().isDone());

        assertEquals(DoneReceiptCommand.MESSAGE_RECEIPT_DONE, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptMadeDone_markAsDoneSuccessful() {
        ModelStubReceiptToBeMadeDone modelStub = new ModelStubReceiptToBeMadeDone();
        //add an Entry to simulate user adding an item
        modelStub.addEntry(ENTRY_ONE);

        CommandResult commandResult = new DoneReceiptCommand().execute(modelStub);

        // confirm that receipt is marked as done
        assertTrue(modelStub.getReceipt().isDone());

        assertEquals(DoneReceiptCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }





    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements SplitterModel {

        @Override
        public void addEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEntry(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Entry getEntry(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPersonAmountBook getPersonAmountBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyReceiptBook getReceiptBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void paidPerson(Person person, String amountPaid) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void listAmount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void listReceipt() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void backToMainMenu() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isReceiptDone() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Receipt getReceipt() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearReceipt() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void newReceipt() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAmount(Person person, Amount amount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAmount(Person person, Amount amount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubReceiptDone extends ModelStub {
        final Receipt receipt = new Receipt();

        @Override
        public boolean isReceiptDone() {
            return receipt.isDone();
        }

        @Override
        public Receipt getReceipt() {
            return this.receipt;
        }
    }

    /**
     * This model stub accepts adding of entries and deleting of entries, so as the mark Receipt
     * as done.
     */
    private class ModelStubReceiptToBeMadeDone extends ModelStub {
        final Receipt receipt = new Receipt();

        @Override
        public boolean isReceiptDone() {
            return receipt.isDone();
        }

        @Override
        public Receipt getReceipt() {
            return this.receipt;
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            this.receipt.addEntry(entry);
        }

        @Override
        public void listReceipt() {
            System.out.println(receipt.toString());
        }
    }
}
