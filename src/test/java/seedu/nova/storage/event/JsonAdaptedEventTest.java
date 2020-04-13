package seedu.nova.storage.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.nova.testutil.TypicalEvents.CONSULTATION;
import static seedu.nova.testutil.TypicalEvents.LESSON;
import static seedu.nova.testutil.TypicalEvents.MEETING;
import static seedu.nova.testutil.TypicalEvents.STUDY_SESSION;

import org.junit.jupiter.api.Test;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.logic.parser.exceptions.ParseException;

class JsonAdaptedEventTest {

    private static final String INVALID_DATE = "2020/01/10";
    private static final String INVALID_START_TIME = "2.00pm";
    private static final String INVALID_END_TIME = "4.00pm";
    private static final String INVALID_EVENT_TYPE = "C0nsult@tion";

    private static final String VALID_DATE = MEETING.getDate().toString();
    private static final String VALID_START_TIME = MEETING.getStartTime().toString();
    private static final String VALID_END_TIME = MEETING.getEndTime().toString();
    private static final String VALID_EVENT_TYPE = "meeting";

    @Test
    public void toModelType_validMeetingDetails() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(MEETING);
        assertEquals(MEETING, event.toModelType());
    }

    @Test
    public void toModelType_validConsultationDetails() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(CONSULTATION);
        assertEquals(CONSULTATION, event.toModelType());
    }

    @Test
    public void toModelType_validStudySessionDetails() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(STUDY_SESSION);
        assertEquals(STUDY_SESSION, event.toModelType());
    }

    @Test
    public void toModelType_validLessonDetails() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(LESSON);
        assertEquals(LESSON, event.toModelType());
    }

    @Test
    public void toModelType_validEventType() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(INVALID_EVENT_TYPE, "Test Description", "Test Venue",
                INVALID_DATE, VALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsParseException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", INVALID_DATE,
                VALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(ParseException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsDateTimeParseException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", VALID_DATE,
                INVALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(ParseException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsDateTimeParseException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", VALID_DATE,
                VALID_START_TIME, INVALID_END_TIME, "Test Note", null);
        assertThrows(ParseException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullEventType_throwsNullPointerException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, "Test Description", "Test Venue", VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(NullPointerException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, null, "Test Venue", VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", null, VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", null,
                VALID_START_TIME, VALID_END_TIME, "Test Note", null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", VALID_DATE,
                null, VALID_END_TIME, "Test Note", null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", VALID_DATE,
                VALID_START_TIME, null, "Test Note", null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_TYPE, "Test Description", "Test Venue", VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, null, null);
        assertThrows(IllegalValueException.class, event::toModelType);
    }
}
