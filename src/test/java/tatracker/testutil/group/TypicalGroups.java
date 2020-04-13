//@@author aakanksha-rai

package tatracker.testutil.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.testutil.student.TypicalStudents;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group ONE_STUDENT = new GroupBuilder().withIdentifier("G03")
            .withGroupType(GroupType.LAB)
            .withStudent(TypicalStudents.ALICE)
            .build();

    public static final Group NO_STUDENTS = new GroupBuilder().withIdentifier("G04")
            .withGroupType(GroupType.TUTORIAL)
            .build();

    public static final Group MANY_STUDENTS = new GroupBuilder().withIdentifier("G05")
            .withGroupType(GroupType.OTHER)
            .withStudent(TypicalStudents.ALICE)
            .withStudent(TypicalStudents.BENSON)
            .withStudent(TypicalStudents.ELLE)
            .withStudent(TypicalStudents.GEORGE)
            .withStudent(TypicalStudents.FIONA)
            .withStudent(TypicalStudents.DANIEL)
            .build();

    public static final Group MANY_STUDENTS_COPY = new GroupBuilder().withIdentifier("G05")
            .withGroupType(GroupType.TUTORIAL)
            .withStudent(TypicalStudents.DANIEL)
            .build();

    private TypicalGroups() {} //Prevents instantiation

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(ONE_STUDENT, NO_STUDENTS, MANY_STUDENTS));
    }
}
