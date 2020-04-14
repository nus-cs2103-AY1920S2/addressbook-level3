package fithelper.model.entry;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Entry's remark in FitHelper.
 * Guarantees: immutable; is always valid
 */
public class Remark {
    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * check if a remark is valid or not
     */
    public static boolean isValidRemark(String remark) {
        boolean isValid;
        if (remark == null) {
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
