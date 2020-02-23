package seedu.address.model.entry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {

    private final String name;
    private final String time;

    private final String location;
    private final String calorie;
    private Remark remark = new Remark("");

    /**
     * Every field must be present and not null.
     */
    public Entry(String name, String time, String location, String calorie) {
        requireAllNonNull(name, location, time, calorie);
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public String getCalorie() {
        return calorie;
    }

    public void addRemark(Remark remark) {
        this.remark = remark;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both Entry of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two entries.
     */
    public boolean isSameEntry(Entry anotherEntry) {
        if (anotherEntry == this) {
            return true;
        }

        return anotherEntry != null
                && anotherEntry.getName().equals(getName())
                && (anotherEntry.getLocation().equals(getLocation()))
                && (anotherEntry.getTime().equals(getTime()));
    }

    /**
     * Returns true if both entries have the same identity and data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherPerson = (Entry) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getLocation().equals(getLocation())
                && otherPerson.getTime().equals(getTime())
                && otherPerson.getCalorie().equals(getCalorie());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, location, calorie);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Location: ")
                .append(getLocation())
                .append(" Calorie: ")
                .append(getCalorie())
                .append(" Remark: ")
                .append(getRemark().toString());
        return builder.toString();
    }

}
