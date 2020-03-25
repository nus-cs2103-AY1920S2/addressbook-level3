package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.CourseName;

/**
 * Represents the in-memory model of the course list data.
 */
public class CourseManager {

    private static CourseList courseList;

    public CourseManager(CourseList courseList) {
        requireNonNull(courseList);
        this.courseList = courseList;
    }

    public CourseManager() {
        this(new CourseList());
    }

    public static Course getCourse(CourseName courseName) throws ParseException {
        return courseList.getCourse(courseName);
    }

    public static CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        return courseList.getCourseFocusArea(focusAreaName);
    }
    /*
    public static CourseList getCourseList() {
        return null;
    }
     */
}
