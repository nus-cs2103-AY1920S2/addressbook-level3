package seedu.address.model.profile.course;

// To be implemented

import java.util.List;

/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final String courseName;
    private List<CourseRequirement> requirements;

    public Course(String courseName, List<CourseRequirement> requirements) {
        this.courseName = courseName;
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        return this.courseName;
    }
}
