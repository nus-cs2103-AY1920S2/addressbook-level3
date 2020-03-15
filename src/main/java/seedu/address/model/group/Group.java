package seedu.address.model.group;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Represents a group in TAT.
 * A group is anything that would include a
 * group of students such as a lab or tutorial.
 */
public class Group {

    /**
     * Represents a group type.
     * Can be a lab or a tutorial.
     */
    public enum GroupType {
        LAB,
        TUTORIAL;
    }

    private final String identifier;
    private final GroupType groupType;
    //TODO: ArrayList will contain Students
    //have to change it to use UniqueStudentList!!!
    private final ArrayList students;


    /**
     * Constructs a group object.
     *
     * @param identifier identifies the group. For example,
     *                   the tutorial code for a tutorial, etc.
     */
    public Group(String identifier, GroupType groupType) {
        this.identifier = identifier;
        students = new ArrayList<>();
        this.groupType = groupType;
    }

    //TODO: the following methods
    /**
     * Adds student to students.
     */
    public void addStudent() {
    }

    /**
     * Gets student with given matric number.
     */
    public void getStudent() {
    }

    /**
     * Returns the student list.
     */
    public void getStudentList() {
    }

    /**
     * Returns the group identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns true if both groups have the same identifiers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getIdentifier().equals(this.getIdentifier());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(identifier);
    }

    //TODO: edit once Student is made
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getIdentifier());
        return builder.toString();
    }
}
