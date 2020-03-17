package seedu.address.model.profile.course;

// To be implemented

import java.util.List;

/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final String courseName;
    private List<CourseFocusArea> focusAreas;

    public Course(String courseName, List<CourseFocusArea> focusAreas) {
        this.courseName = courseName;
        this.focusAreas = focusAreas;
    }

    @Override
    public String toString() {
        return this.courseName;
    }
}
