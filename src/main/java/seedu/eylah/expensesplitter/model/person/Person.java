package seedu.eylah.expensesplitter.model.person;

import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.util.Objects;

import seedu.eylah.commons.util.CalculateUtil;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;

    // Data fields
    private Amount amount;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Amount amount) {
        requireAllNonNull(name);
        this.name = name;
        this.amount = amount;
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" owes: ")
                .append(getAmount());
        return builder.toString();
    }

    /**
     * Adds {@code amount} to the current amount.
     */
    public void addAmount(Amount amount) {
        BigDecimal currAmount = this.amount.getBigDecimal();
        BigDecimal amountToBeAdded = amount.getBigDecimal();
        this.amount = CalculateUtil.addAmount(currAmount, amountToBeAdded);
    }

    /**
     * Removes {@code amount} from the current amount.
     */
    public void removeAmount(Amount amount) {
        BigDecimal currAmount = this.amount.getBigDecimal();
        BigDecimal amountToBeRemoved = amount.getBigDecimal();
        this.amount = CalculateUtil.removeAmount(currAmount, amountToBeRemoved);
    }
}
