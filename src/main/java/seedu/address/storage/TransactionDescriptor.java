package seedu.address.storage;

import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;

/**
 * A helper class to hold Transaction details.
 */
public class TransactionDescriptor {
    public final Description description;
    public final Amount amount;
    public final Date date;
    public final Tag tag;

    public TransactionDescriptor(Description description, Amount amount, Date date, Tag tag) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.tag = tag;
    }
}
