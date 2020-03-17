package seedu.address.model;

import static java.util.Objects.requireNonNull;

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

    /*
    Consider implementing
    hasCourse()
    getCourse()
    */
}
