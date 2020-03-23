package seedu.eylah.expensesplitter.model;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Receipt receipt;
    private final UserPrefs userPrefs;
    private final PersonAmountBook personAmountBook;

    /**
     * Initializes a ModelManager with a new Receipt (and data from existing storage).
     */
    public ModelManager(Receipt receipt, ReadOnlyPersonAmountBook personAmountBook, ReadOnlyUserPrefs userPrefs) {
        super();

        logger.fine("Initializing new receipt (and data from existing storage");

        this.personAmountBook = new PersonAmountBook(personAmountBook);
        this.receipt = receipt;
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new Receipt(), new PersonAmountBook(), new UserPrefs());
    }

    //=========== Receipt ===============================================================================

    /**
     * Adds an entry to the receipt.
     *
     * @param entry consists of an Item and ArrayList of Person sharing the cost of the item.
     */
    @Override
    public void addEntry(Entry entry) {
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

    /**
     * Intentionally left empty.
     * To be implemented later.
     */
    @Override
    public void listReceipt() {

        System.out.println(receipt);

    }

    /**
     * Command for listing all person with their amount.
     */
    @Override
    public void listAmount() {


    }

    /**
     * Intentionally left empty.
     * To be implemented later.
     */
    @Override
    public void paidPerson(Person p) {

    }

    /**
     * Intentionally left empty.
     * To be implemented later.
     */
    @Override
    public void backToMainMenu() {

    }

    //=========== PersonAmountBook ===============================================================================


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
        personAmountBook.addPerson(person);
    }

    @Override
    public void addAmount(Person person, Amount amount) {
        personAmountBook.addAmount(person, amount);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return receipt.equals(other.receipt);
    }

}
