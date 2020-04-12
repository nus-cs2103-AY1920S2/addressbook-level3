package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.profile.course.Course;

public class CourseManagerTest {

    private CourseManager courseManager = new CourseManager();

    private class CourseListStub {
        private ArrayList<Course> courseList = new ArrayList<>();
        // Add a few courses to test

        public CourseListStub() {}


    }
}
