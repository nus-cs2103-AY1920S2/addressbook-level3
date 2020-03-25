package tatracker.model.group;

import java.util.Objects;

import javafx.collections.ObservableList;

import tatracker.model.student.Matric;
import tatracker.model.student.Student;
import tatracker.model.student.UniqueStudentList;

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
        TUTORIAL
    }

    private final String identifier;
    private final GroupType groupType;
    private final UniqueStudentList students;

    /**
     * Constructs a group object.
     *
     * @param identifier identifies the group.
     *                   For example, the tutorial code for a tutorial, etc.
     */
    public Group(String identifier, GroupType groupType) {
        this.identifier = identifier;
        this.groupType = groupType;
        this.students = new UniqueStudentList();
    }

    /**
     * Returns the group identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the group type of this group.
     * For example, if it is a tutorial or lab.
     */
    public GroupType getGroupType() {
        return groupType;
    }

    /**
     * Returns the student list.
     */
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    /**
     * Returns the student enrolled in this module with the given
     * matriculation number (the student id).
     * Returns null if no such student exists.
     */
    public Student getStudent(Matric studentId) {
        return students.get(studentId);
    }

    /**
     * Adds a student to the list of enrolled students.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Deletes the given student from the list of enrolled students,
     * if it exists.
     */
    public void deleteStudent(Student student) {
        students.remove(student);
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
        return this.identifier.equals(otherGroup.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    //TODO: edit once Student is made
    @Override
    public String toString() {
        return String.format("%s", identifier);
    }
}
