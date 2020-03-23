package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;

/**
 * Creates a new CourseList object which contains Module objects.
 */
public class CourseList {

    private ArrayList<Course> courseList = new ArrayList<>();

    public CourseList() {}

    /**
     * Adds a course to the course list.
     */
    public void addCourse(Course course) {
        courseList.add(course);
    }

    /**
     * Returns true if a course with the same name as {@code CourseName} exists in the course list.
     */
    public boolean hasCourse(CourseName courseName) {
        requireNonNull(courseName);
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    public Course getCourse(CourseName courseName) {
        requireNonNull(courseName);
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }


}
