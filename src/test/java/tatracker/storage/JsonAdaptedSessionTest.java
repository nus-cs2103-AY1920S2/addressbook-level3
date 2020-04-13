package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.storage.JsonAdaptedSession.MESSAGE_INVALID_END_DATE_TIME;
import static tatracker.storage.JsonAdaptedSession.MESSAGE_INVALID_START_DATE_TIME;
import static tatracker.storage.JsonAdaptedSession.MESSAGE_INVALID_TIMING;
import static tatracker.storage.JsonAdaptedSession.MISSING_DESCRIPTION;
import static tatracker.storage.JsonAdaptedSession.MISSING_END_DATE_TIME;
import static tatracker.storage.JsonAdaptedSession.MISSING_MODULE_ID;
import static tatracker.storage.JsonAdaptedSession.MISSING_SESSION_TYPE;
import static tatracker.storage.JsonAdaptedSession.MISSING_START_DATE_TIME;
import static tatracker.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;

public class JsonAdaptedSessionTest {
    private static final String INVALID_START_DATETIME = "04/04/2020 @ 2pm";
    private static final String INVALID_END_DATETIME = "04/04/2020 @ 4pm";
    private static final String INVALID_TYPE = "lunch";
    private static final String INVALID_MODULE = "    ";
    private static final int INVALID_RECURRING = -1;

    private static final String VALID_START_DATETIME = "2020-04-04T14:00";
    private static final String VALID_END_DATETIME = "2020-04-04T16:00";
    private static final String VALID_TYPE = "tutorial";
    private static final String VALID_NOTES = "With Jane";
    private static final String VALID_MODULE = "CS1231";
    private static final boolean VALID_IS_DONE = false;
    private static final int VALID_RECURRING = 1;

    private static final Session AFTERNOON_TUTORIAL = new Session(
            LocalDateTime.of(2020, 4, 4, 12, 0),
            LocalDateTime.of(2020, 4, 4, 14, 0),
            SessionType.TUTORIAL,
            0,
            "CS2040",
            "With Alice"
    );

    private static final Session AFTERNOON_LAB;

    static {
        AFTERNOON_LAB = new Session(
                LocalDateTime.of(2020, 4, 4, 14, 0),
                LocalDateTime.of(2020, 4, 4, 16, 0),
                SessionType.LAB,
                1,
                "CS2030",
                "With Bob"
        );
        AFTERNOON_LAB.done();
    }

    @Test
    public void toModelType_validSessionDetails_returnsSession() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(AFTERNOON_TUTORIAL);
        assertEquals(AFTERNOON_TUTORIAL, session.toModelType());
    }

    @Test
    public void toModelType_validDoneSession_success() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(AFTERNOON_LAB);
        assertEquals(AFTERNOON_LAB, session.toModelType());
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(INVALID_START_DATETIME, VALID_END_DATETIME,
                VALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MESSAGE_INVALID_START_DATE_TIME, session::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(null, VALID_END_DATETIME,
                VALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MISSING_START_DATE_TIME, session::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, INVALID_END_DATETIME,
                VALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MESSAGE_INVALID_END_DATE_TIME, session::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, null,
                VALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MISSING_END_DATE_TIME, session::toModelType);
    }

    @Test
    public void toModelType_invalidTiming_throwsIllegalValueException() {
        // Times are swapped
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_END_DATETIME, VALID_START_DATETIME,
                VALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MESSAGE_INVALID_TIMING, session::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, VALID_END_DATETIME,
                INVALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, SessionType.MESSAGE_CONSTRAINTS, session::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, VALID_END_DATETIME,
                null, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MISSING_SESSION_TYPE, session::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, VALID_END_DATETIME,
                VALID_TYPE, null, VALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MISSING_DESCRIPTION, session::toModelType);
    }

    @Test
    public void toModelType_invalidModule_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, VALID_END_DATETIME,
                VALID_TYPE, VALID_NOTES, INVALID_MODULE, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, Module.CONSTRAINTS_MODULE_CODE, session::toModelType);
    }

    @Test
    public void toModelType_nullModule_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, VALID_END_DATETIME,
                VALID_TYPE, VALID_NOTES, null, VALID_IS_DONE, VALID_RECURRING);
        assertThrows(IllegalValueException.class, MISSING_MODULE_ID, session::toModelType);
    }

    @Test
    public void toModelType_invalidRecurring_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_START_DATETIME, VALID_END_DATETIME,
                VALID_TYPE, VALID_NOTES, VALID_MODULE, VALID_IS_DONE, INVALID_RECURRING);
        assertThrows(IllegalValueException.class, Session.CONSTRAINTS_RECURRING_WEEKS, session::toModelType);
    }
}
