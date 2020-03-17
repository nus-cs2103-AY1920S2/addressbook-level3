package seedu.eylah.expensesplitter.model;

import java.util.logging.Logger;

import seedu.eylah.addressbook.model.person.Person;
import seedu.eylah.commons.core.LogsCenter;

//import static java.util.Objects.requireNonNull;
//import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Receipt receipt;

    /**
     * Initializes a ModelManager with a new Receipt (and data from existing storage).
     */
    public ModelManager(Receipt receipt) {
        super();

        logger.fine("Initializing new receipt (and data from existing storage");

        this.receipt = receipt;
    }

    public ModelManager() {
        this(new Receipt());
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
     *
     * @param person Person who paid
     */
    @Override
    public void paidPerson(Person person) {

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
    public void listReceipt() {

    }

    /**
     * Intentionally left empty.
     * To be implemented later.
     */
    @Override
    public void backToMainMenu() {

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
