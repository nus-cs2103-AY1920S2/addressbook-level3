package seedu.expensela.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's name.
 * Guarantees: immutable.
 */
public class Name {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public final String transactionName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        transactionName = name;
    }



    @Override
    public String toString() {
        return transactionName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && transactionName.equals(((Name) other).transactionName)); // state check
    }

    @Override
    public int hashCode() {
        return transactionName.hashCode();
    }

}
