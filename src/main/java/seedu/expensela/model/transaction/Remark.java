package seedu.expensela.model.transaction;

/**
 * Represents a Transaction's remark which is optional.
 * Guarantees: immutable.
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS =
            "Remarks should only contain alphanumeric characters, spaces, \":+-()'\", may or may not be blank";

    public static final String VALIDATION_REGEX;

    static {
        VALIDATION_REGEX = ".*";
    }

    public final String transactionRemark;

    public Remark(String remark) {
        transactionRemark = remark;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        assert transactionRemark != null;

        return transactionRemark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && transactionRemark.equals(((Remark) other).transactionRemark)); // state check
    }

    @Override
    public int hashCode() {
        return transactionRemark.hashCode();
    }

}

