//@@author aakanksha-rai

package tatracker.model.module;

import java.util.Objects;

import javafx.collections.ObservableList;

import tatracker.model.group.Group;
import tatracker.model.group.UniqueGroupList;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Represents a module in the TAT.
 */
public class Module {

    public static final String CONSTRAINTS_MODULE_CODE = "Module codes cannot be blank";
    public static final String CONSTRAINTS_MODULE_NAME = "Module names cannot be blank";

    private static final String DEFAULT_NAME = "";

    private final String identifier;
    private String name;
    private final UniqueGroupList groups;

    /**
     * Constructs a module object with no name.
     */
    public Module(String identifier) {
        this(identifier, DEFAULT_NAME);
    }

    /**
     * Constructs a module object.
     *
     * @param identifier identifies the module.
     *                   Usually equal to the module code.
     * @param name the name of the module.
     */
    public Module(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
        this.groups = new UniqueGroupList();
    }

    /**
     * Constructor for use in testing.
     */
    public Module(String identifier, String name, UniqueGroupList groups) {
        this.identifier = identifier;
        this.name = name;
        this.groups = groups;
    }

    /**
     * Returns the module identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns module name.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the module name.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Returns module at index n.
     */
    public Group get(int n) {
        return groups.get(n);
    }

    public Student getStudent(Matric matric, String groupCode) {
        return groups.get(groupCode).getStudent(matric);
    }

    /**
     * Returns the group list.
     */
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    /**
     * Returns the unique group list.
     */
    public UniqueGroupList getUniqueGroupList() {
        return groups;
    }


    public boolean hasStudent(Matric matric, String targetGroup) {
        return groups.get(targetGroup).hasStudent(matric);
    }

    /**
     * Adds a group to the list of module groups.
     * Returns the unique sessions list.
     */
    public void setStudent(Student student, Student editedStudent, String targetGroup) {
        groups.get(targetGroup).setStudent(student, editedStudent);
    }

    public boolean hasGroup(Group group) {
        return groups.contains(group);
    }

    /**
     * Adds a group to the list of module groups.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Returns the group in this module with the given group id.
     * Returns null if no such group exists.
     */
    public Group getGroup(String groupId) {
        return groups.get(groupId);
    }

    /**
     * Deletes the given group from the list of module groups,
     * if it exists.
     */
    public void deleteGroup(Group group) {
        groups.remove(group);
    }

    /**
     * Replaces the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the list of groups.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the module.
     */
    public void setGroup(Group target, Group editedGroup) {
        groups.setGroup(target, editedGroup);
    }

    /**
     * Sorts students in the groups alphabetically.
     */
    public void sortGroupsAlphabetically() {
        for (Group group : groups) {
            group.sortStudentsAlphabetically();
        }
    }

    /**
     * Sorts the students in the groups by rating in ascending order.
     */
    public void sortGroupsByRatingAscending() {
        for (Group group : groups) {
            group.sortStudentsByRatingAscending();
        }
    }

    /**
     * Sorts the students in the groups by rating in descending order.
     */
    public void sortGroupsByRatingDescending() {
        for (Group group : groups) {
            group.sortStudentsByRatingDescending();
        }
    }

    /**
     * Sorts the students in the groups by matric number in descending order.
     */
    public void sortGroupsByMatricNumber() {
        for (Group group : groups) {
            group.sortStudentsByMatricNumber();
        }
    }

    /**
     * Returns true if both modules have the same identifiers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return this.identifier.equals(otherModule.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, identifier);
    }

}
