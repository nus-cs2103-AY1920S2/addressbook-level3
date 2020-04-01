package seedu.recipe.model.cooked;

import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.recipe.model.Date;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;

/**
 * Represents a Record in the cooked records book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity fields
    private final Name name;
    private final Date date;
    private final Set<Goal> goals = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Record(Name name, Date date, Set<Goal> goals) {
        requireAllNonNull(name);
        this.name = name;
        this.date = date;
        this.goals.addAll(goals);
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns an immutable goal set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Goal> getGoals() {
        return Collections.unmodifiableSet(goals);
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

        return otherRecord.getName().equals(getName())
                && otherRecord.getDate().equals(getDate())
                && otherRecord.getGoals().equals(getGoals());
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
                .append(getDate())
                .append("\nGoals: ");
        getGoals().forEach(builder::append);
        return builder.toString();
    }

}
