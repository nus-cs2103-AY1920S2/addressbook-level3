//@@author aakanksha-rai

package tatracker.testutil.group;

import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.student.Student;
import tatracker.model.student.UniqueStudentList;

/**
 * Used to build a group for testing purposes.
 */
public class GroupBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_GROUP_CODE = "G03";
    public static final GroupType DEFAULT_GROUP_TYPE = GroupType.LAB;

    private String identifier;
    private GroupType groupType;
    private UniqueStudentList students;

    public GroupBuilder() {
        this.identifier = DEFAULT_GROUP_CODE;
        this.students = new UniqueStudentList();
        this.groupType = DEFAULT_GROUP_TYPE;
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        identifier = groupToCopy.getIdentifier();
        students = groupToCopy.getUniqueStudentList();
        groupType = groupToCopy.getGroupType();
    }

    /**
     * Sets the {@code identifier} of the {@code Group} that we are building.
     */
    public GroupBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * Sets the {@code groupType} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    /**
     * Adds the {@code student} to the {@code Group} that we are building.
     */
    public GroupBuilder withStudent(Student student) {
        students.add(student);
        return this;
    }

    public Group build() {
        return new Group(identifier, groupType, students);
    }
}
