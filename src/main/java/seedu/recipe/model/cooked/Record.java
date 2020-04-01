package seedu.recipe.model.cooked;

import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.recipe.model.Date;
import seedu.recipe.model.recipe.Name;

/**
 * Represents a Record in the cooked records book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity fields
    private final Name name;
    private final Date date;

    /**
     * Every field must be present and not null.
     */
    public Record(Name name, Date date) {
        requireAllNonNull(name);
        this.name = name;
        this.date = date;
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }



    /**
     * Returns true if both recipes of the same name and same date.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.getName().equals(getName())
                && otherRecord.getDate().equals(getDate());
    }

    /**
     * Returns true if both recipes have the same identity and data fields.
     * This defines a stronger notion of equality between two recipes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;

        return otherRecord.getName().equals(getName()) && otherRecord.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nDate: ")
                .append(getDate());
        return builder.toString();
    }
}
