package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.util.CalculateUtil;
import seedu.eylah.expensesplitter.model.ModelStub;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.testutil.TypicalItem;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class AddItemCommandTest {

    private final Item item = TypicalItem.STEAMBOAT;
    private final ArrayList<Person> persons = TypicalPersons.getTypicalPersonsArrayList();
    private final Amount amount = CalculateUtil.calculatePricePerPerson(item.getItemPrice().getItemPrice(),
            new BigDecimal(persons.size()));
    private final Entry entry = new Entry(item, persons);


    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(null, null, null));
    }


    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(null, persons, amount));
    }

    @Test
    public void constructor_nullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(item, null, amount));
    }

    @Test
    public void constructor_nullAmount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(item, persons, null));
    }

    @Test
    public void constructor_nullItemNullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(null, null, amount));
    }

    @Test
    public void constructor_nullItemNullAmount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(null, persons, null));
    }

    @Test
    public void constructor_nullPersonsNullAmount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddItemCommand(item, null, null));
    }


    @Test
    public void execute_receiptIsDone_addUnsuccessful() throws Exception {
        ModelStubReceiptDone modelStub = new ModelStubReceiptDone();
        modelStub.getReceipt().makeDone();

        CommandResult commandResult = new AddItemCommand(item, persons, amount).execute(modelStub);

        assertEquals(AddItemCommand.MESSAGE_RECEIPT_DONE, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptAddEntry_addSuccessful() throws Exception {
        ModelStubAcceptingEntry modelStub = new ModelStubAcceptingEntry();
        modelStub.addEntry(entry);

        CommandResult commandResult = new AddItemCommand(item, persons, amount).execute(modelStub);

        assertEquals(String.format(AddItemCommand.MESSAGE_SUCCESS, entry), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptAddEntryDoesNotHavePersonsAddPersons_addSuccessful() throws Exception {
        ModelStubAcceptingEntry modelStub = new ModelStubAcceptingEntry();
        modelStub.addEntry(entry);
        for (Person person : persons) {
            modelStub.addPerson(person);
        }
        assertEquals(persons, modelStub.getPersonAmountBook().getPersonList());

        CommandResult commandResult = new AddItemCommand(item, persons, amount).execute(modelStub);

        assertEquals(String.format(AddItemCommand.MESSAGE_SUCCESS, entry), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptAddEntryHavePersonsAddAmount_addSuccessful() throws Exception {
        ModelStubAcceptingEntry modelStub = new ModelStubAcceptingEntry();
        modelStub.addEntry(entry);
        for (Person person : persons) {
            modelStub.addPerson(person);
        }
        for (Person person : persons) {
            modelStub.addAmount(person, amount);
        }
        assertEquals(persons, modelStub.getPersonAmountBook().getPersonList());

        CommandResult commandResult = new AddItemCommand(item, persons, amount).execute(modelStub);

        assertEquals(String.format(AddItemCommand.MESSAGE_SUCCESS, entry), commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        AddItemCommand addItemCommand = new AddItemCommand(item, persons, amount);

        //same object -> returns true
        assertTrue(addItemCommand.equals(addItemCommand));

        //different types -> returns false
        assertFalse(addItemCommand.equals(1));

        //null -> returns false
        assertFalse(addItemCommand.equals(null));
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
     * This model stub accepts adding of entries.
     */
    private class ModelStubAcceptingEntry extends ModelStub {
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
        public ReadOnlyPersonAmountBook getPersonAmountBook() {
            return personAmountBook;
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            this.receipt.addEntry(entry);
        }
    }
}
