package seedu.address.model.profile.course.module;

/**
 * Represents a Module's semester data in the address book.
 * Guarantees:
 */
public class SemesterData {

    public final int semNumber;

    /**
     * Constructs a {@code SemesterData}.
     *
     * @param semNumber A valid semester number
     */
    public SemesterData(String semNumber) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        int num = Integer.parseInt(semNumber);
        this.semNumber = num;
    }

    @Override
    public String toString() {
        return String.valueOf(semNumber);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterData // instanceof handles nulls
                && semNumber == (((SemesterData) other).semNumber)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
