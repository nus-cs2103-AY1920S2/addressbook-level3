package seedu.address.testutil;

import seedu.address.model.modelObjectTags.Gender;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ID = "100";
    public static final String DEFAULT_GENDER = "m";
    public static final String DEFAULT_ASSIGNEDCOURSES = "";

    private Name name;
    private ID id;
    private Gender gender;
    private Set<ID> assignedCourses;
    private Set<Tag> tags;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        gender = new Gender(DEFAULT_GENDER);
        assignedCourses = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        id = studentToCopy.getId();
        gender = studentToCopy.getGender();
        assignedCourses = studentToCopy.getAssignedCoursesID();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Student} that we are building.
     */
    public StudentBuilder withID(String id) {
        this.id = new ID(id);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Student} that we are building.
     */
    public StudentBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code AssignedCourse} of the {@code Student} that we are building.
     */
    public StudentBuilder withAssignedCourse(String assignedCourse) {
        this.assignedCourses = SampleDataUtil.getIDSet(assignedCourse);
        return this;
    }

    public Student build() {
        return new Student(name, id, gender, assignedCourses, tags);
    }

}
