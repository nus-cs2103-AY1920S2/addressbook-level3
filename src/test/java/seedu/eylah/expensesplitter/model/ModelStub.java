package seedu.eylah.expensesplitter.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;


/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements SplitterModel {

    @Override
    public void setPersonAmountBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public Path getPersonAmountBookFilePath() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void addEntry (Entry entry) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void deleteEntry (int index) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public Entry getEntry (int index) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ReadOnlyPersonAmountBook getPersonAmountBook() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ReadOnlyReceiptBook getReceiptBook() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void paidPerson(Person person, String amountPaid) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public String listAmount() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public String listReceipt() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public boolean isReceiptDone() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public Receipt getReceipt() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void clearReceipt() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public Person getPerson(Person person) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void addAmount(Person person, Amount amount) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void removeAmount(Person person, Amount amount) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called");
    }

}
