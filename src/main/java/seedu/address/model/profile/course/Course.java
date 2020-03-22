package seedu.address.model.profile.course;

import java.util.List;

/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final String courseName;

    private final List<CourseRequirement> requirements;


    public Course(String courseName, List<CourseRequirement> requirements) {
        this.courseName = courseName;
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(courseName);

        for (CourseRequirement requirement : requirements) {
            output.append("\n");
            output.append(CourseRequirement);
        }

        output.append("\n");
        return output.toString();
    }

    public CourseName getCourseName() {
        return new CourseName(courseName);
    }
}
