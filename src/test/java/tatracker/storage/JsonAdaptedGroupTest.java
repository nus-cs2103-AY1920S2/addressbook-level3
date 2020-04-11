package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.storage.JsonAdaptedGroup.MESSAGE_DUPLICATE_STUDENTS;
import static tatracker.storage.JsonAdaptedGroup.MISSING_GROUP_ID;
import static tatracker.storage.JsonAdaptedGroup.MISSING_GROUP_TYPE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.NO_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.ONE_STUDENT;
import static tatracker.testutil.student.TypicalStudents.ALICE;
import static tatracker.testutil.student.TypicalStudents.BENSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;

public class JsonAdaptedGroupTest {
    private static final String INVALID_ID = "    ";
    private static final String INVALID_TYPE = "lunch";

    private static final String VALID_ID = "G05";
    private static final String VALID_TYPE = "tutorial";

    private static final List<JsonAdaptedStudent> VALID_STUDENT_LIST = Arrays.asList(
            new JsonAdaptedStudent(ALICE),
            new JsonAdaptedStudent(BENSON)
    );

    private static final List<JsonAdaptedStudent> DUPLICATE_STUDENT_LIST = Arrays.asList(
            new JsonAdaptedStudent(ALICE),
            new JsonAdaptedStudent(BENSON),
            new JsonAdaptedStudent(BENSON)
    );

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        JsonAdaptedGroup group = new JsonAdaptedGroup(NO_STUDENTS);
        assertEquals(NO_STUDENTS, group.toModelType());

        group = new JsonAdaptedGroup(ONE_STUDENT);
        assertEquals(ONE_STUDENT, group.toModelType());

        group = new JsonAdaptedGroup(MANY_STUDENTS);
        assertEquals(MANY_STUDENTS, group.toModelType());
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(INVALID_ID, VALID_TYPE,
                VALID_STUDENT_LIST);
        String expectedMessage = Group.CONSTRAINTS_GROUP_CODE;
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(null, VALID_TYPE,
                VALID_STUDENT_LIST);
        assertThrows(IllegalValueException.class, MISSING_GROUP_ID, group::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(VALID_ID, INVALID_TYPE,
                VALID_STUDENT_LIST);
        String expectedMessage = GroupType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(VALID_ID, null,
                VALID_STUDENT_LIST);
        assertThrows(IllegalValueException.class, MISSING_GROUP_TYPE, group::toModelType);
    }

    @Test
    public void toModelType_duplicateStudentList_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(VALID_ID, VALID_TYPE,
                DUPLICATE_STUDENT_LIST);
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_STUDENTS, group::toModelType);
    }
}
