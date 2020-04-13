package nasa.storage;

import nasa.model.activity.Schedule;


public class JsonAdaptedDeadlineTest {
    private static final String INVALID_NAME = "";
    private static final String INVALID_DUE_DATE = "02-30-2020 23:59";
    private static final String INVALID_PRIORITY = "0";
    private static final String INVALID_SCHEDULE = "-1";
    private static final String INVALID_ISDONE = "NOPE";

    private static final String VALID_NAME = "tP";
    private static final String VALID_DATE = "13-02-2020 23:59";
    private static final String VALID_NOTE = "Finish UG and DG";
    private static final String VALID_DUE_DATE = "13-04-2020 23:59";
    private static final String VALID_PRIORITY = "5";
    private static final String VALID_SCHEDULE = new Schedule().toString();
    private static final String VALID_ISDONE = "true";

    /*
    @Test
    public void toModelType_validDeadlineDetails_returnsDeadline() throws Exception {
        JsonAdaptedDeadline module = new JsonAdaptedDeadline(VALID_NAME, VALID_DATE, VALID_NOTE, VALID_PRIORITY,
                VALID_DUE_DATE, VALID_SCHEDULE, VALID_ISDONE);
        assertEquals(CS2103T_DEADLINE, module.toModelType());
    }


    @Test
    public void toModelType_invalidCode_throwsIllegalValueException() {
        JsonAdaptedDeadline module =
                new JsonAdaptedDeadline(INVALID_CODE, VALID_NAME, VALID_DEADLINES, VALID_EVENTS);
        String expectedMessage = DeadlineCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullCode_throwsIllegalValueException() {
        JsonAdaptedDeadline module = new JsonAdaptedDeadline(null, VALID_NAME, VALID_DEADLINES, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeadlineCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDeadline module =
                new JsonAdaptedDeadline(VALID_CODE, INVALID_NAME, VALID_DEADLINES, VALID_EVENTS);
        String expectedMessage = DeadlineName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDeadline module = new JsonAdaptedDeadline(VALID_CODE, null, VALID_DEADLINES, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeadlineName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

     */

}
