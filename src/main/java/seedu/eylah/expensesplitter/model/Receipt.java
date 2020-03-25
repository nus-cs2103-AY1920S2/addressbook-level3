package seedu.eylah.expensesplitter.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Receipt class in the ExpenseSplitter of EYLAH.
 */
public class Receipt {

    private ArrayList<Entry> receipt;

    /**
     * Initialises a Receipt object with an ArrayList to contain Entries.
     */
    public Receipt() {
        this.receipt = new ArrayList<>();
    }

    /**
     * Adds an Entry to the receipt.
     *
     * @param entry Entry to be added.
     */
    void addEntry(Entry entry) {
        this.receipt.add(entry);
    }

    /**
     * Deletes an Entry from the receipt via its index.
     *
     * @param index Index of Entry to be deleted.
     */
    void deleteEntry(int index) {
        receipt.remove(index);
    }

    public Entry getEntry(int index) {
        return this.receipt.get(index);
    }

    /**
     * Returns the ArrayList of Entries.
     *
     * @return the ArrayList of Entries.
     */
    public ArrayList<Entry> getReceipt() {
        return this.receipt;
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
        return otherItem.getReceipt().equals(getReceipt());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(receipt);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Receipt:\n");
        for (Entry entry : receipt) {
            int index = receipt.indexOf(entry) + 1;
            builder.append("    ")
                    .append(index)
                    .append(". ")
                    .append(entry.toString())
                    .append("\n");
        }
        return builder.toString();
    }

}
