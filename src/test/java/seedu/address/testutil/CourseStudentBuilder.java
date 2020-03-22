package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import seedu.address.model.modelCourseStudent.CourseStudent;
import seedu.address.model.person.AssignedCourse;
import seedu.address.model.person.Courseid;
import seedu.address.model.person.Name;
import seedu.address.model.person.Studentid;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CourseStudent objects.
 */
public class CourseStudentBuilder {

    public static final String DEFAULT_COURSEID = "829";
    public static final String DEFAULT_STUDENTID = "33";

    private Courseid courseid;
    private Studentid studentid;
    private Set<Tag> tags;

    public CourseStudentBuilder() {
        courseid = new Courseid(DEFAULT_COURSEID);
        studentid = new Studentid(DEFAULT_STUDENTID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CourseStudentBuilder with the data of {@code courseCourseStudentToCopy}.
     */
    public CourseStudentBuilder(CourseStudent courseCourseStudentToCopy) {
        courseid = courseCourseStudentToCopy.getCourseid();
        studentid = courseCourseStudentToCopy.getStudentid();
        tags = new HashSet<>(courseCourseStudentToCopy.getTags());
    }

    /**
     * Sets the {@code courseid} of the {@code CourseStudent} that we are building.
     */
    public CourseStudentBuilder withCourseID(String courseid) {
        this.courseid = new Courseid(courseid);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code CourseStudent} that we are building.
     */
    public CourseStudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code AssignedCourse} of the {@code CourseStudent} that we are building.
     */
    public CourseStudentBuilder withStudentID(String studentID) {
        this.studentid = new Studentid(studentID);
        return this;
    }

    public CourseStudent build() {
        return new CourseStudent(courseid, studentid, tags);
    }

}
