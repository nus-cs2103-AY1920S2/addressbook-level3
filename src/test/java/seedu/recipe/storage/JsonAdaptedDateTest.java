package seedu.recipe.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;

public class JsonAdaptedDateTest {

    private static final String INVALID_DATE = "20200101";
    private static final String VALID_DATE = "2020-01-01";

    @Test
    public void toModelType_validDate_returnsPlan() throws Exception {
        JsonAdaptedDate date = new JsonAdaptedDate(DATE_TODAY);
        assertEquals(DATE_TODAY, date.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedDate invalidDate = new JsonAdaptedDate(INVALID_DATE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, invalidDate::toModelType);
    }

    @Test
    void toModelType_validDate_getDate() {
        JsonAdaptedDate validDate = new JsonAdaptedDate(VALID_DATE);
        String expectedReturnValue = VALID_DATE;
        assertEquals(expectedReturnValue, validDate.getDate());
    }
}
