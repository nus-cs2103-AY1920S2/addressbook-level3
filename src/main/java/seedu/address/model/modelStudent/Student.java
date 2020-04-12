package seedu.address.model.modelStudent;

import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.Gender;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Student in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Student extends ModelObject {

    // Identity fields
    private final Name name;
    private final ID id;
    private final Gender gender;
    private Set<ID> assignedCoursesID = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Gender gender, Set<Tag> tags) throws ParseException {
        requireAllNonNull(name, gender, tags);
        this.name = name;
        this.id = UuidManager.assignNewUUID(this);
        this.gender = gender;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Student(Name name, ID id, Gender gender, Set<Tag> tags) {
        requireAllNonNull(name, id, tags);
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Student(Name name, ID id, Gender gender, Set<ID> assignedCoursesID, Set<Tag> tags) {
        requireAllNonNull(name, id, tags);
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.assignedCoursesID.addAll(assignedCoursesID);
        this.tags.addAll(tags);
    }

    /**
     * Creates and returns a copy of this student.
     *
     * @return a clone of this instance.
     */
    public Student clone() {
        Student cloned = new Student(name, id, gender, assignedCoursesID, tags);
        return cloned;
    }

    /**
     * Get Name of a student.
     *
     * @return name of this student.
     */
    public Name getName() {
        return name;
    }

    /**
     * Get ID of a student.
     *
     * @return ID of this student.
     */
    public ID getId() {
        return id;
    }

    /**
     * Get Gender of a student.
     *
     * @return gender of a student.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Indicate if this student contains a course.
     *
     * @param courseID the ID of the course to check.
     * @return true if this student has this courseID.
     */
    public boolean containsCourse(ID courseID) {
        return this.assignedCoursesID.contains(courseID);
    }

    /**
     * Returns an immutable ID set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     *
     * @return immutable ID set of assigned courses to this student
     */
    public Set<ID> getAssignedCoursesID() {
        return Collections.unmodifiableSet(assignedCoursesID);
    }

    /**
     * Get List of String of the ID.
     *
     * @return Array of String.
     */
    public List<String> getAssignedCoursesIDString() {
        List<String> IDList = new ArrayList<>();
        for (ID id : assignedCoursesID) {
            IDList.add(id.toString());
        }
        return IDList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Add a course to student.
     *
     * @param courseID the course to be added to this student.
     */
    public void addCourse(ID courseID) {
        this.assignedCoursesID.add(courseID);
    }

    /**
     * Remove a course to student.
     *
     * @param courseID the course to be removed from this student.
     */
    public void removeCourse(ID courseID) {
        this.assignedCoursesID.remove(courseID);
    }

    /**
     * Add a set of courses to student.
     *
     * @param courseIDs the set of courses to be added to this student.
     */
    public void addCourses(Set<ID> courseIDs) {
        this.assignedCoursesID.addAll(courseIDs);
    }

    /**
     * Returns true if both students of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two students.
     *
     * @return true if both students of same name have at least one other identity field the same
     * false otherwise.
     */
    public boolean weakEquals(ModelObject otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        if (!(otherStudent instanceof Student)) {
            return false;
        }

        Student otherStudentCast = (Student) otherStudent;
        return otherStudentCast != null
                && otherStudentCast.getName().equals(getName())
                && otherStudentCast.getId().equals(getId())
                && otherStudentCast.getTags().equals(getTags());
    }

    /**
     * Returns true if both students have the same identity and data fields. This defines a stronger
     * notion of equality between two students.
     *
     * @param other the student to compare
     * @return true if both students have the same identity and data fields, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
