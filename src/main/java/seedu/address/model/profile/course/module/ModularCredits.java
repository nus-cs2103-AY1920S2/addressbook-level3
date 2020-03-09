package seedu.address.model.profile.course.module;

/**
 * Represents a Module's number of modular credits in the address book.
 * Guarantees:
 */
public class ModularCredits {

    public final int modularCredits;

    /**
     * Constructs a {@code ModularCredits}.
     *
     * @param modularCredits A valid String Integer of modular credits
     */
    public ModularCredits(String modularCredits) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        int credits = Integer.parseInt(modularCredits);
        this.modularCredits = credits;
    }

    @Override
    public String toString() {
        return String.valueOf(modularCredits);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModularCredits // instanceof handles nulls
                && modularCredits == (((ModularCredits) other).modularCredits)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
