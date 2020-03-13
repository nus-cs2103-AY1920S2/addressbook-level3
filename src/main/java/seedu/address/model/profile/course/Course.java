package seedu.address.model.profile.course;

// To be implemented

/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final String course;

    public Course(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return this.course;
    }
}
