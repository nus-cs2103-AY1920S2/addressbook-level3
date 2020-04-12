package seedu.eylah.expensesplitter.model;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;


/**
 * Represents the in-memory model of the address book data.
 */
public class SplitterModelManager implements SplitterModel {
    private static final Logger logger = LogsCenter.getLogger(SplitterModelManager.class);

    private final UserPrefs userPrefs;
    private final Receipt receipt;
    private final PersonAmountBook personAmountBook;
    private final ReceiptBook receiptBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with a new Receipt (and data from existing storage).
     */
    public SplitterModelManager(ReadOnlyReceiptBook receiptBook, ReadOnlyPersonAmountBook personAmountBook,
                                ReadOnlyUserPrefs userPrefs) {

        logger.fine("Initializing receipt and data from existing storage");

        this.userPrefs = new UserPrefs(userPrefs);
        this.personAmountBook = new PersonAmountBook(personAmountBook);
        this.receiptBook = new ReceiptBook(receiptBook);
        filteredPersons = new FilteredList<>(this.personAmountBook.getPersonList());

        if (receiptBook.isContainSingleReceipt()) {
            this.receipt = this.receiptBook.getReceiptList().get(0); // current only have 1 receipt in receipt book.
        } else {
            this.receipt = new Receipt();
            this.receiptBook.addReceipt(this.receipt);
        }
    }

    public SplitterModelManager() {
        this(new ReceiptBook(), new PersonAmountBook(), new UserPrefs());
    }

    //=========== Receipt ===============================================================================

    @Override
    public ReadOnlyReceiptBook getReceiptBook() {
        return receiptBook;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    /**
     * Adds an entry to the receipt.
     *
     * @param entry consists of an Item and ArrayList of Person sharing the cost of the item.
     */
    @Override
    public void addEntry(Entry entry) {
        requireNonNull(entry);
        receipt.addEntry(entry);
    }

    /**
     * Removes an entry from the receipt via the index.
     *
     * @param index Index of the entry to be removed.
     */
    @Override
    public void deleteEntry(int index) {
        receipt.deleteEntry(index);
    }

    @Override
    public Entry getEntry(int index) {
        return receipt.getEntry(index);
    }

    /**
     * Command for listing the Receipt.
     */
    @Override
    public String listReceipt() {

        return receipt.toString();
    }

    /**
     * Command for listing all person with their amount.
     */
    @Override
    public String listAmount() {
        return personAmountBook.toString();
    }

    /**
     * This method is used to subtract the {@code amountPaid} from the current amount owed from the {@code person}
     */
    @Override
    public void paidPerson(Person person, String amountPaid) {
        requireAllNonNull(person, amountPaid);
        Amount amount = new Amount(new BigDecimal(amountPaid));
        personAmountBook.removeAmount(person, amount);

    }

    /**
     * Checks if the current receipt is marked as completed.
     */
    @Override
    public boolean isReceiptDone() {
        return this.receipt.isDone();
    }

    @Override
    public Receipt getReceipt() {
        return this.receipt;
    }

    /**
     * Clears the receipt by making a new ArrayList of Entry.
     */
    @Override
    public void clearReceipt() {
        receipt.clearReceipt();
    }

    //=========== PersonAmountBook ===============================================================================

    @Override
    public void setPersonAmountBookFilePath(Path addressBookFilePath) {
        requireAllNonNull(addressBookFilePath);
        userPrefs.setPersonAmountBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getPersonAmountBookFilePath() {
        return userPrefs.getPersonAmountBookFilePath();
    }

    @Override
    public ReadOnlyPersonAmountBook getPersonAmountBook() {
        return personAmountBook;
    }

    /**
     * Returns true if a person with the same identity as
     * {@code person} exists in the person amount book.
     */
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
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof SplitterModelManager)) {
            return false;
        }

        // state check
        SplitterModelManager other = (SplitterModelManager) obj;
        return receipt.equals(other.receipt);
    }


    //=========== Filtered Person List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
}
