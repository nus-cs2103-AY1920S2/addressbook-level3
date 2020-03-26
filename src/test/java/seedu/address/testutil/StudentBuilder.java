package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.Address;
import seedu.address.model.person.AssignedCourse;
import seedu.address.model.person.Email;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ID = "100";

    private Name name;
    private ID id;
    private Set<Tag> tags;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        id = studentToCopy.getID();
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
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code AssignedCourse} of the {@code Student} that we are building.
     */
    public StudentBuilder withID(String id) {
        this.id = new ID(id);
        return this;
    }

    public Student build() {
        return new Student(name, id, tags);
    }

}
