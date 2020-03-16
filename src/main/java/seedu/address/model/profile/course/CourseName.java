package seedu.address.model.profile.course;

/**
 * Represents a Course's name
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)} // to be implemented
public class CourseName {

    public final String courseName;

    /**
     * Constructs a {@code CourseName}.
     *
     * @param courseName A valid course name.
     */
    public CourseName(String courseName) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return courseName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseName // instanceof handles nulls
                && courseName.equals(((CourseName) other).courseName)); // state check
    }

    // methods to be implemented
    // isValidTitle()
    // hashCode()
}
