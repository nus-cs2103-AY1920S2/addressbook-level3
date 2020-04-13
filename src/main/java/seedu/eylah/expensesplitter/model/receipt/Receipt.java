package seedu.eylah.expensesplitter.model.receipt;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Receipt class in the ExpenseSplitter of EYLAH.
 */
public class Receipt {

    private ArrayList<Entry> receipt;
    private boolean isDone;

    /**
     * Initialises a Receipt object with an ArrayList to contain Entries.
     * Initialised as undone.
     */
    public Receipt() {
        this.receipt = new ArrayList<>();
        this.isDone = false;
    }

    /**
     * Every field must be present.
     * Array of Entry and isDone must not be null.
     */
    public Receipt(ArrayList<Entry> receipt, boolean isDone) {
        this.receipt = receipt;
        this.isDone = isDone;
    }

    /**
     * Adds an Entry to the receipt.
     *
     * @param entry Entry to be added.
     */
    public void addEntry(Entry entry) {
        this.receipt.add(entry);
    }

    /**
     * Deletes an Entry from the receipt via its index.
     *
     * @param index Index of Entry to be deleted.
     */
    public void deleteEntry(int index) {
        receipt.remove(index);
    }

    public Entry getEntry(int index) {
        return this.receipt.get(index);
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void makeDone() {
        this.isDone = true;
    }

    public void makeUndone() {
        this.isDone = false;
    }

    /**
     * Clears the receipt by making a new ArrayList of Entry.
     */
    public void clearReceipt() {
        this.receipt = new ArrayList<>();
        this.isDone = false;
    }

    /**
     * Returns the ArrayList of Entries.
     *
     * @return the ArrayList of Entries.
     */
    public ArrayList<Entry> getReceipt() {
        return this.receipt;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameReceipt(Receipt otherReceipt) {
        if (otherReceipt == this) {
            return true;
        }

        return otherReceipt != null
                && otherReceipt.getReceipt().equals(getReceipt());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Receipt)) {
            return false;
        }

        Receipt otherItem = (Receipt) other;

        //return otherItem.getReceipt().equals(getReceipt());
        /*
        The above .equals() method do not work because you are using the inherent AL .equals() method.
        A Receipt should be equal if the Entries all are equals, but if you are using the inherent .equals()
        method then it won't work.
         */

        for (int i = 0; i < getReceipt().size(); i++) {
            if (getReceipt().get(i).equals(otherItem.receipt.get(i))) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(receipt);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        int indexToBeDisplayed = 1;
        builder.append("Receipt:\n");
        for (Entry entry : receipt) {
            builder.append("    ")
                    .append(indexToBeDisplayed)
                    .append(". ")
                    .append(entry.toString())
                    .append("\n");
            indexToBeDisplayed++;
        }
        return builder.toString();
    }

    //for testing purposes
    public boolean hasEntry(Entry entry) {
        return receipt.contains(entry);
    }
}
