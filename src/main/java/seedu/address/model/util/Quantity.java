package seedu.address.model.util;

/**
 * The API of quantity.
 */
public interface Quantity extends Comparable<Quantity> {

    String MESSAGE_CONSTRAINTS_FORMAT =
            "Quantity should take non-negative integer values (up to 1000000), and it should not be blank";

    /**
     * There must be one or more digits entered.
     */
    String VALIDATION_REGEX = "^\\d{1,7}$";

    int MAX_VALUE = 1000000;

    /**
     * Returns the value of the quantity
     */
    int getValue();

    /**
     * Returns a new quantity whose value is the difference between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    Quantity minus(Quantity q);

    /**
     * Returns a new quantity whose value is the summation between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    Quantity plus(Quantity q);

    @Override
    String toString();

    @Override
    boolean equals(Object other);

    @Override
    int hashCode();

    @Override
    int compareTo(Quantity q);
}
