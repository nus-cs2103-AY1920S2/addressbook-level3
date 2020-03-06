package seedu.address.model.profile.course.module;

/**
 * Represents a Module's academic year in the address book.
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidYear(String)} // to be implemented
public class AcadYear {

    public final String acadYear;

    /**
     * Constructs a {@code AcadYear}.
     *
     * @param acadYear A valid module academic year.
     */
    public AcadYear(String acadYear) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.acadYear = acadYear;
    }

    @Override
    public String toString() {
        return acadYear;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcadYear // instanceof handles nulls
                && acadYear.equals(((AcadYear) other).acadYear)); // state check
    }

    // methods to be implemented
    // isValidYear()
    // hashCode()
}
