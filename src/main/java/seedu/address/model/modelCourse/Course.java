package seedu.address.model.modelCourse;

import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.Amount;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Course in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Course extends ModelObject {

    // Identity fields
    private final String ENTITY_NAME = "Course";
    private final Name name;
    private final ID id;
    private final Set<Tag> tags = new HashSet<>();
    private Amount amount;
    private ID assignedStaffID;
    private Set<ID> assignedStudentsID = new HashSet<>();
    private Set<ID> assignedAssignmentsID = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Course(Name name, Amount amount, Set<Tag> tags) throws ParseException {
        requireAllNonNull(name, amount, tags);
        this.name = name;
        this.id = UuidManager.assignNewUUID(this);
        this.amount = amount;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Course(Name name, ID id, Amount amount, Set<Tag> tags) {
        requireAllNonNull(name, id, amount, tags);
        this.name = name;
        this.id = id;
        this.amount = amount;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Course(Name name, ID id, Amount amount, ID assignedStaffID, Set<ID> assignedStudentsID, Set<ID> assignedAssignmentsID, Set<Tag> tags) {
        requireAllNonNull(name, id, amount, tags);
        this.name = name;
        this.id = id;
        this.amount = amount;
        this.assignedStaffID = assignedStaffID;
        this.assignedStudentsID.addAll(assignedStudentsID);
        this.assignedAssignmentsID.addAll(assignedAssignmentsID);
        this.tags.addAll(tags);
    }

    /**
     * Creates and returns a copy of this course.
     *
     * @return a clone of this instance.
     */
    public Course clone() {
        Course cloned = new Course(this.name, this.id, this.amount, this.assignedStaffID,
                this.assignedStudentsID, this.assignedAssignmentsID, this.tags);
        return cloned;
    }

    /**
     * Get Name of a course.
     *
     * @return name of this course.
     */
    public Name getName() {
        return name;
    }

    /**
     * Get ID of a course.
     *
     * @return ID of this course.
     */
    public ID getId() {
        return id;
    }

    /**
     * Get amount (student fee) of a course.
     *
     * @return amount of this course.
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Get ID staff (teacher) teaching a course.
     *
     * @return ID staff teaching the course.
     */
    public ID getAssignedStaffID() {
        return assignedStaffID;
    }

    /**
     * Add a student ID to a course.
     *
     * @param studentID the student to be added to this course.
     */
    public void addStudent(ID studentID) {
        this.assignedStudentsID.add(studentID);
    }

    /**
     * Add a set of student IDs to a course.
     *
     * @param studentIDs the set of students' IDs to be added to this course.
     */
    public void addStudents(Set<ID> studentIDs) {
        this.assignedStudentsID.addAll(studentIDs);
    }

    /**
     * Add an assignment ID to a course.
     *
     * @param assignmentID the assignment ID to be added to this course.
     */
    public void addAssignment(ID assignmentID) {
        this.assignedAssignmentsID.add(assignmentID);
    }

    /**
     * Add a set of assignment IDs to a course.
     *
     * @param assignmentIDs the assignments' IDs to be added to this course.
     */
    public void addAssignments(Set<ID> assignmentIDs) {
        this.assignedAssignmentsID.addAll(assignmentIDs);
    }

    /**
     * Indicate whether this course is assigned to a teacher.
     *
     * @return true if this course is assigned to a teacher, false otherwise.
     */
    public boolean hasTeacher() {
        return this.assignedStaffID != null && !this.assignedStaffID.equals(new ID(""));
    }

    public void addStaff(ID staffid) {
        this.assignedStaffID = staffid;
    }

    // ================================== FOR ASSIGN COMMANDS =================================

    /**
     * Indicate whether this course contains a studentID.
     *
     * @return true if this course contains a studentID, false otherwise.
     */
    public boolean containsStudent(ID studentID) {
        return assignedStudentsID.contains(studentID);
    }

    /**
     * Indicate whether this course contains an assignmentID.
     *
     * @return true if this course contains an assignmentID, false otherwise.
     */
    public boolean containsAssignment(ID assignmentID) {
        return assignedAssignmentsID.contains(assignmentID);
    }

    /**
     * Assign a staff (teacher) teaching this course.
     */
    public void assignStaff(ID staffID) {
        this.assignedStaffID = staffID;
    }

    /**
     * Indicate whether a staff is teaching this course by checking staff ID.
     *
     * @return true if this course contains staff ID, false otherwise.
     */
    public boolean containsStaff(ID staffID) {
        return assignedStaffID != null && assignedStaffID.equals(staffID);
    }

    // ================================== FOR UNASSIGN COMMANDS =================================

    /**
     * Remove an assignment from this course.
     */
    public void removeAssignment(ID assignmentID) {
        this.assignedAssignmentsID.remove(assignmentID);
    }

    /**
     * Remove a student from this course.
     */
    public void removeStudent(ID studentID) {
        this.assignedStudentsID.remove(studentID);
    }

    /**
     * Remove a staff from this course.
     */
    public void removeStaff() {
        this.assignedStaffID = null;
    }

    /**
     * Get List of String of the ID
     *
     * @return Array of String
     */
    public Set<ID> getAssignedAssignmentsID() {
        return Collections.unmodifiableSet(assignedAssignmentsID);
    }

    /**
     * Get List of String of the ID
     *
     * @return Array of String
     */
    public List<String> getAssignedStudentsIDString() {
        List<String> IDList = new ArrayList<>();
        for (ID id : assignedStudentsID) {
            IDList.add(id.toString());
        }
        return IDList;
    }

    /**
     * Returns an immutable ID set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<ID> getAssignedStudentsID() {
        return Collections.unmodifiableSet(assignedStudentsID);
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both courses of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two courses.
     */
    public boolean weakEquals(ModelObject otherCourse) {
        if (otherCourse == this) {
            return true;
        }
        if (!(otherCourse instanceof Course)) {
            return false;
        }
        Course otherCourseCast = (Course) otherCourse;
        return otherCourseCast != null
                && otherCourseCast.getName().equals(getName())
                && otherCourseCast.getId().equals(getId());
    }

    /**
     * Returns true if both courses have the same identity and data fields. This defines a stronger
     * notion of equality between two courses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Course)) {
            return false;
        }
        Course otherCourse = (Course) other;
        return otherCourse.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, amount, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Amount: ")
                .append(getAmount())
                .append(" AssignedStaff: ")
                .append(getAssignedStaffID())
                .append(" Assigned Students: ");
        getAssignedStudentsID().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
