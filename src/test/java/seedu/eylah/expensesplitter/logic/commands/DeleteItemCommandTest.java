package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.util.CalculateUtil;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.testutil.TypicalItem;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class DeleteItemCommandTest {

    private final Item item = TypicalItem.STEAMBOAT;
    private final ArrayList<Person> persons = TypicalPersons.getTypicalPersonsArrayList();
    private final Amount amount = CalculateUtil.calculatePricePerPerson(item.getItemPrice().getItemPrice(),
            new BigDecimal(persons.size()));
    private final Entry entry = new Entry(item, persons);


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteItemCommand(null));
    }


    @Test
    public void execute_receiptIsDone_deleteUnsuccessful() throws Exception {
        ModelStubReceiptDone modelStub = new ModelStubReceiptDone();
        modelStub.getReceipt().makeDone();
        Index index = new Index(0);

        CommandResult commandResult = new DeleteItemCommand(index).execute(modelStub);

        assertEquals(DeleteItemCommand.MESSAGE_RECEIPT_DONE, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptDeleteEntry_deleteSuccessful() throws Exception {
        ModelStubAcceptingEntryDeletingEntry modelStub = new ModelStubAcceptingEntryDeletingEntry();
        modelStub.addEntry(entry);
        for (Person person : persons) {
            modelStub.addPerson(person);
        }
        Index index = new Index(0);

        CommandResult commandResult = new DeleteItemCommand(index).execute(modelStub);

        assertEquals(String.format(DeleteItemCommand.MESSAGE_DELETE_PERSON_SUCCESS, index.getOneBased()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptDeleteEntryRemoveAmount_addSuccessfulremoveSuccessful() throws Exception {
        ModelStubAcceptingEntryDeletingEntry modelStub = new ModelStubAcceptingEntryDeletingEntry();
        modelStub.addEntry(entry);
        for (Person person : persons) {
            modelStub.addPerson(person);
        }
        for (Person person : persons) {
            modelStub.addAmount(person, amount);
        }
        Index index = new Index(0);

        CommandResult commandResult = new DeleteItemCommand(index).execute(modelStub);

        assertEquals(persons, modelStub.getPersonAmountBook().getPersonList());
        assertEquals(String.format(DeleteItemCommand.MESSAGE_DELETE_PERSON_SUCCESS, index.getOneBased()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Index index = new Index(0);
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(index);

        //same object -> returns true
        assertTrue(deleteItemCommand.equals(deleteItemCommand));

        //different types -> returns false
        assertFalse(deleteItemCommand.equals(1));

        //null -> returns false
        assertFalse(deleteItemCommand.equals(null));
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
     * This model stub accepts adding of entries and deleting of entries.
     */
    private class ModelStubAcceptingEntryDeletingEntry extends ModelStub {
        final Receipt receipt = new Receipt();
        final PersonAmountBook personAmountBook = new PersonAmountBook();

        @Override
        public boolean isReceiptDone() {
            return receipt.isDone();
        }

        @Override
        public Receipt getReceipt() {
            return this.receipt;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personAmountBook.hasPerson(person);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personAmountBook.addPerson(person);
        }

        @Override
        public Person getPerson(Person person) {
            requireNonNull(person);
            return personAmountBook.getPerson(person);
        }

        @Override
        public void addAmount(Person person, Amount amount) {
            requireAllNonNull(person, amount);
            personAmountBook.addAmount(person, amount);
        }

        @Override
        public void removeAmount(Person person, Amount amount) {
            requireAllNonNull(person, amount);
            personAmountBook.removeAmount(person, amount);
        }

        @Override
        public ReadOnlyPersonAmountBook getPersonAmountBook() {
            return personAmountBook;
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            this.receipt.addEntry(entry);
        }

        @Override
        public void deleteEntry(int index) {
            receipt.deleteEntry(index);
        }

        @Override
        public Entry getEntry(int index) {
            return receipt.getEntry(index);
        }
    }
}
