package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.storage.JsonAdaptedModule.MESSAGE_DUPLICATE_GROUPS;
import static tatracker.storage.JsonAdaptedModule.MISSING_MODULE_ID;
import static tatracker.storage.JsonAdaptedModule.MISSING_MODULE_NAME;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.NO_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.ONE_STUDENT;
import static tatracker.testutil.module.TypicalModules.NO_GROUPS;
import static tatracker.testutil.module.TypicalModules.ONE_GROUP_NO_STUDENTS;
import static tatracker.testutil.module.TypicalModules.ONE_GROUP_WITH_STUDENTS;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.module.Module;

public class JsonAdaptedModuleTest {
    private static final String INVALID_ID = "    ";
    private static final String INVALID_NAME = "    ";

    private static final String VALID_ID = "CS1231";
    private static final String VALID_NAME = "Discrete Structures";

    private static final List<JsonAdaptedGroup> VALID_GROUP_LIST = Arrays.asList(
            new JsonAdaptedGroup(NO_STUDENTS),
            new JsonAdaptedGroup(ONE_STUDENT),
            new JsonAdaptedGroup(MANY_STUDENTS)
    );

    private static final List<JsonAdaptedGroup> DUPLICATE_GROUP_LIST = Arrays.asList(
            new JsonAdaptedGroup(NO_STUDENTS),
            new JsonAdaptedGroup(ONE_STUDENT),
            new JsonAdaptedGroup(ONE_STUDENT),
            new JsonAdaptedGroup(MANY_STUDENTS)
    );

    // public static final Session AFTERNOON_TUTORIAL = new Session(
    //         LocalDateTime.of(2020, 4, 4, 12, 0),
    //         LocalDateTime.of(2020, 4, 4, 14, 0),
    //         SessionType.TUTORIAL,
    //         0,
    //         VALID_ID,
    //         "With Alice"
    // );
    //
    // private static final Session AFTERNOON_LAB = new Session(
    //         LocalDateTime.of(2020, 4, 4, 14, 0),
    //         LocalDateTime.of(2020, 4, 4, 16, 0),
    //         SessionType.LAB,
    //         1,
    //         VALID_ID,
    //         "With Bob"
    // );
    //
    // private static final List<JsonAdaptedSession> VALID_SESSION_LIST = Arrays.asList(
    //         new JsonAdaptedSession(AFTERNOON_TUTORIAL),
    //         new JsonAdaptedSession(AFTERNOON_LAB)
    // );
    //
    // private static final List<JsonAdaptedSession> DUPLICATE_SESSION_LIST = Arrays.asList(
    //         new JsonAdaptedSession(AFTERNOON_TUTORIAL),
    //         new JsonAdaptedSession(AFTERNOON_LAB),
    //         new JsonAdaptedSession(AFTERNOON_LAB)
    // );

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(NO_GROUPS);
        assertEquals(NO_GROUPS, module.toModelType());

        module = new JsonAdaptedModule(ONE_GROUP_NO_STUDENTS);
        assertEquals(ONE_GROUP_NO_STUDENTS, module.toModelType());

        module = new JsonAdaptedModule(ONE_GROUP_WITH_STUDENTS);
        assertEquals(ONE_GROUP_WITH_STUDENTS, module.toModelType());
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(INVALID_ID, VALID_NAME,
                VALID_GROUP_LIST);
        String expectedMessage = Module.CONSTRAINTS_MODULE_CODE;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(null, VALID_NAME,
                VALID_GROUP_LIST);
        assertThrows(IllegalValueException.class, MISSING_MODULE_ID, module::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_ID, INVALID_NAME,
                VALID_GROUP_LIST);
        String expectedMessage = Module.CONSTRAINTS_MODULE_NAME;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_ID, null,
                VALID_GROUP_LIST);
        assertThrows(IllegalValueException.class, MISSING_MODULE_NAME, module::toModelType);
    }

    // @Test
    // public void toModelType_duplicateSessionList_throwsIllegalValueException() {
    //     JsonAdaptedModule module = new JsonAdaptedModule(VALID_ID, VALID_NAME,
    //             DUPLICATE_SESSION_LIST, VALID_GROUP_LIST);
    //     assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_SESSIONS, module::toModelType);
    // }

    @Test
    public void toModelType_duplicateGroupList_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_ID, VALID_NAME,
                DUPLICATE_GROUP_LIST);
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_GROUPS, module::toModelType);
    }
}
