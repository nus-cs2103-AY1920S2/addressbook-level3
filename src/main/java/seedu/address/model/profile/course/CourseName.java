package seedu.address.model.profile.course;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;

import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
    public CourseName(String courseName) throws ParseException {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        String formattedCourse = format(courseName);
        if (!isValid(formattedCourse)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COURSE, NewCommand.MESSAGE_USAGE));
        }
        this.courseName = formattedCourse;
    }

    /**
     * Returns formatted courseName
     */
    public String format(String courseName) {
        courseName = courseName.trim();
        courseName = courseName.toUpperCase();
        return courseName;
    }

    public boolean isValid(String courseName) {
        return AcceptedCourses.contains(courseName);
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
