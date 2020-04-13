package seedu.eylah.expensesplitter.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.eylah.commons.model.Model;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * The API of the Model component.
 */
public interface SplitterModel extends Model {

    @Override
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    @Override
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' food book file path.
     */
    Path getPersonAmountBookFilePath();

    /**
     * Sets the user prefs' person amount bookfile path.
     */
    void setPersonAmountBookFilePath(Path personAmountBookFilePath);

    /**
     * Adds an entry to the receipt.
     *
     * @param entry consists of an Item and ArrayList of Person sharing the cost of the item.
     */
    void addEntry(Entry entry);

    /**
     * Removes an entry from the receipt via the index.
     *
     * @param index Index of the entry to be removed.
     */
    void deleteEntry(int index);

    Entry getEntry(int index);

    /** Returns the PersonAmountBook */
    ReadOnlyPersonAmountBook getPersonAmountBook();

    /** Returns the ReceiptBook */
    ReadOnlyReceiptBook getReceiptBook();

    /**
     * Updates the amount the Person owes you. If amount is $0 then the Person is deleted.
     */
    void paidPerson(Person person, String amountPaid);

    /**
     * Command for listing all person with their amount.
     */
    String listAmount();

    /**
     * Command for listing all items in that receipt.
     */
    String listReceipt();

    boolean isReceiptDone();

    Receipt getReceipt();

    void clearReceipt();

    /**
     * Returns true if a person with the same identity as
     * {@code person} exists in the person amount book.
     */
    boolean hasPerson(Person person);
    void addPerson(Person person);
    Person getPerson(Person person);
    void addAmount(Person person, Amount amount);
    void removeAmount(Person person, Amount amount);
    void deleteAllEntries();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

}

