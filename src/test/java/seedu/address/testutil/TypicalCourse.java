package seedu.address.testutil;

import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Course} objects to be used in tests.
 */
public class TypicalCourse {

    public static final Course COURSE_ALICE = new CourseBuilder().withName("Alice Pauline")
            .withID("1000")
            .withTags("friends").build();
    public static final Course COURSE_BENSON = new CourseBuilder().withName("Benson Meier")
            .withID("1001")
            .withTags("owesMoney", "friends").build();
    public static final Course CARL = new CourseBuilder().withName("Carl Kurz")
            .withID("1002").build();
    public static final Course DANIEL = new CourseBuilder().withName("Daniel Meier")
            .withID("1003").withTags("friends").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCourse() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical courses.
     */
    public static CourseAddressBook getTypicalCourseAddressBook() {
        CourseAddressBook ab = new CourseAddressBook();
        for (Course course : getTypicalCourses()) {
            ab.add(course);
        }
        return ab;
    }

    public static List<Course> getTypicalCourses() {
        return new ArrayList<>(Arrays.asList(COURSE_ALICE, COURSE_BENSON));
    }
}
