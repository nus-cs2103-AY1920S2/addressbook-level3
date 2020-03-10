package seedu.address.model.expenditure;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Expenditure in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenditure {

    // Identity fields
    private final Name name;
    private final Id id;
    private final Amount amount;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Expenditure(Name name, Id id, Amount amount, Address address, Set<Tag> tags) {
        requireAllNonNull(name, id, amount, address, tags);
        this.name = name;
        this.id = id;
        this.amount = amount;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Id getId() {
        return id;
    }

    public Amount getAmount() {
        return amount;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Expenditure otherExpenditure) {
        if (otherExpenditure == this) {
            return true;
        }

        return otherExpenditure != null
                && otherExpenditure.getName().equals(getName())
                && otherExpenditure.getId().equals(getId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expenditure)) {
            return false;
        }

        Expenditure otherExpenditure = (Expenditure) other;
        return otherExpenditure.getName().equals(getName())
                && otherExpenditure.getId().equals(getId())
                && otherExpenditure.getAmount().equals(getAmount())
                && otherExpenditure.getAddress().equals(getAddress())
                && otherExpenditure.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, amount, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Id: ")
                .append(getId())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
