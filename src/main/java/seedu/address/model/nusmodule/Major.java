package seedu.address.model.nusmodule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a major in NUS.
 */
public class Major {

    public static final String MESSAGE_CONSTRAINTS =
            "Please enter a valid major name in NUS (e.g. Computer Science)";

    public final String nameOfMajor;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Major(String name) {
        requireNonNull(name);
        checkArgument(isValidMajor(name), MESSAGE_CONSTRAINTS);
        nameOfMajor = name;
    }

    /**
     * Returns true if a given string is a valid major.
     */
    public static boolean isValidMajor(String test) {
        //implement later
        return true;
    }


    @Override
    public String toString() {
        return nameOfMajor;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && nameOfMajor.equals(((Major) other).nameOfMajor)); // state check
    }

    @Override
    public int hashCode() {
        return nameOfMajor.hashCode();
    }
}
