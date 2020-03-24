package seedu.address.model.profile.course;

import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final String courseName;
    private final List<CourseRequirement> requirements;
    private final List<CourseFocusArea> focusAreas;

    public Course(String courseName, List<CourseRequirement> requirements,
                  List<CourseFocusArea> focusAreas) {
        this.courseName = courseName;
        this.requirements = requirements;
        this.focusAreas = focusAreas;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(courseName);

        if (!requirements.isEmpty()) {
            for (CourseRequirement requirement : requirements) {
                output.append("\n");
                output.append(requirement);
            }
        }

        if (!focusAreas.isEmpty()) {
            for (CourseFocusArea focusArea : focusAreas) {
                output.append("\n");
                output.append(focusArea);
            }
        }

        output.append("\n");
        return output.toString();
    }

    public CourseName getCourseName() throws ParseException {
        return new CourseName(courseName);
    }
}
