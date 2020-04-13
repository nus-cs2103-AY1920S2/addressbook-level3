package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedConsult.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalConsults.getConsult1;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedConsultTest {

    private static final String VALID_BEGIN_DATE_TIME = getConsult1().getBeginDateTime().toString();
    private static final String VALID_END_DATE_TIME = getConsult1().getEndDateTime().toString();
    private static final String VALID_PLACE = getConsult1().getLocation().toString();
    private static final String VALID_MATRICNUMBER = getConsult1().getMatricNumber().toString();
    private static final String VALID_NAME = "John";


    @Test
    public void toModelType_validConsultDetails_returnsConsult() throws Exception {
        JsonAdaptedConsult consult = new JsonAdaptedConsult(getConsult1());
        assertEquals(getConsult1(), consult.toModelType());
    }

    @Test
    public void toModelType_nullBeginDateTime_throwsIllegalValueException() {
        JsonAdaptedConsult consult =
            new JsonAdaptedConsult(null, VALID_END_DATE_TIME, VALID_PLACE,
                VALID_NAME, VALID_MATRICNUMBER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "BEGIN DATE TIME");
        assertThrows(IllegalValueException.class, expectedMessage, consult::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        JsonAdaptedConsult consult =
            new JsonAdaptedConsult(VALID_BEGIN_DATE_TIME, null, VALID_PLACE,
                VALID_NAME, VALID_MATRICNUMBER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME");
        assertThrows(IllegalValueException.class, expectedMessage, consult::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedConsult consult =
            new JsonAdaptedConsult(VALID_BEGIN_DATE_TIME, VALID_END_DATE_TIME, null,
                VALID_NAME, VALID_MATRICNUMBER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT LOCATION");
        assertThrows(IllegalValueException.class, expectedMessage, consult::toModelType);
    }
}
