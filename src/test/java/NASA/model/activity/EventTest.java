package NASA.model.activity;

import static NASA.testutil.Assert.assertThrows;
import static NASA.testutil.TypicalActivities.CORRECT_EVENT;
import static NASA.testutil.TypicalActivities.PAST_EVENT;
import static NASA.testutil.TypicalActivities.WRONG_EVENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * An EventTest class to test the functionality of Event.
 */
class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, null, null,null));
    }

    @Test
    void isValidEvent() {

        //invalid date sequence
        assertFalse(Event.isValidEvent(WRONG_EVENT));

        //invalid date expired
        assertFalse(Event.isValidEvent(PAST_EVENT));

        //valid date sequence
        assertTrue(Event.isValidEvent(CORRECT_EVENT));
    }
}