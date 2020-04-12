package seedu.address.model.modelAssignment;

import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.Date;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Assignment in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Assignment extends ModelObject {

    // Identity fields
    private final String ENTITY_NAME = "assignment";
    private final Name name;
    private final ID id;
    private ID assignedCourseID;
    private final Date deadline;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Assignment(Name name, Date deadline, Set<Tag> tags) throws ParseException {
        requireAllNonNull(name, deadline, tags);
        this.name = name;
        this.id = UuidManager.assignNewUUID(this);
        this.deadline = deadline;
        this.tags.addAll(tags);
    }

    /**
     * Overload Assignment constructor
     */
    public Assignment(Name name, ID assignmentId, Date deadline, Set<Tag> tags) {
        requireAllNonNull(name, assignmentId, deadline, tags);
        this.name = name;
        this.id = assignmentId;
        this.deadline = deadline;
        this.tags.addAll(tags);
    }

    /**
     * Overload Assignment constructor
     */
    public Assignment(Name name, ID assignmentId, ID courseID, Date deadline, Set<Tag> tags) {
        requireAllNonNull(name, assignmentId, deadline, tags);
        this.name = name;
        this.id = assignmentId;
        this.assignedCourseID = courseID;
        this.deadline = deadline;
        this.tags.addAll(tags);
    }

    /**
     * Creates and returns a copy of this assignment.
     *
     * @return a clone of this instance.
     */
    public Assignment clone() {
        Assignment cloned = new Assignment(name, id, assignedCourseID, deadline, tags);
        return cloned;
    }

    /**
     * Get Name of an assignment.
     *
     * @return name of this assignment.
     */
    public Name getName() {
        return name;
    }

    /**
     * Get ID of an assignment.
     *
     * @return ID of this assignment.
     */
    public ID getId() {
        return id;
    }

    /**
     * Get deadline of an assignment.
     *
     * @return deadline of this assignment.
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Add a course ID to an assignment.
     *
     * @param courseID the course ID for this assignment
     */
    public void addCourseID(ID courseID) {
        this.assignedCourseID = courseID;
    }

    /**
     * Get ID course of an assignment.
     *
     * @return ID course of the assignment.
     */
    public ID getAssignedCourseID() {
        return this.assignedCourseID;
    }

    /**
     * Remove the course ID in this assignment
     */
    public void removeCourseID(ID courseID) {
        this.assignedCourseID = null;
    }

    /**
     * Indicate whether this assignment is assigned to a course
     *
     * @return true if this assignment is assigned to a course, false otherwise.
     */
    public boolean isAssignedToCourse() {
        return this.assignedCourseID != null;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Indicate whether both courses of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two courses.
     *
     * @return true if there is another similar identity field other than name, false otherwise.
     */
    @Override
    public boolean weakEquals(ModelObject otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        if (!(otherAssignment instanceof Assignment)) {
            return false;
        }
        Assignment otherAssignmentCast = (Assignment) otherAssignment;
        return otherAssignmentCast != null
                && otherAssignmentCast.getName().equals(getName())
                && otherAssignmentCast.getId().equals(getId());
    }

    /**
     * Indicate whether both assignments have the same identity and data fields. This defines a stronger
     * notion of equality between two courses.
     *
     * @return true if both assignments have same identity and data fields, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return otherAssignment.getName().equals(getName())
                && otherAssignment.getId().equals(getId())
                && otherAssignment.getDeadline().equals(getDeadline())
                && otherAssignment.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, deadline, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append("Assignment: ")
                .append(getName())
                .append("\n")
                .append("Assignment ID: ")
                .append(getId())
                .append("\n")
                .append("Deadline: ")
                .append(getDeadline().toString())
                .append("\n")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
