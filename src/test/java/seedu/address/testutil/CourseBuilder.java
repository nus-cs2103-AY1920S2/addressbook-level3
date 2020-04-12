package seedu.address.testutil;

import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.Amount;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Course objects.
 */
public class CourseBuilder {

    public static final String DEFAULT_NAME = "Java Programming";
    public static final String DEFAULT_ID = "102";
    public static final String DEFAULT_AMOUNT = "100";
    public static final String DEFAULT_ASSIGNEDTEACHER = "1";

    private Name name;
    private ID id;
    private Amount amount;
    private ID assignedTeacher;
    private Set<ID> assignedStudents;
    private Set<ID> assignedAssignments;
    private Set<Tag> tags;

    public CourseBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        amount = new Amount(DEFAULT_AMOUNT);
        assignedTeacher = new ID(DEFAULT_ASSIGNEDTEACHER);
        assignedStudents = new HashSet<>();
        assignedAssignments = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the CourseBuilder with the data of {@code courseToCopy}.
     */
    public CourseBuilder(Course courseToCopy) {
        name = courseToCopy.getName();
        id = courseToCopy.getId();
        amount = courseToCopy.getAmount();
        assignedTeacher = courseToCopy.getAssignedStaffID();
        assignedStudents = courseToCopy.getAssignedStudentsID();
        assignedAssignments = courseToCopy.getAssignedAssignmentsID();
        tags = new HashSet<>(courseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Assignment} that we are building.
     */
    public CourseBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withID(String id) {
        this.id = new ID(id);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code AssignedStudents} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withAssignedStudent(String assignedStudent) {
        this.assignedStudents = SampleDataUtil.getIDSet(assignedStudent);
        return this;
    }

    /**
     * Sets the {@code AssignedTeacher} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withAssignedTeacher(String assignedTeacher) {
        this.assignedTeacher = new ID(assignedTeacher);
        return this;
    }

    public Course build() {
        return new Course(name, id, amount, assignedTeacher, assignedStudents, assignedAssignments, tags);
    }

}
