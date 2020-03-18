package nasa.model.activity;

import static nasa.testutil.TypicalActivities.CORRECT_EVENT;
import static nasa.testutil.TypicalActivities.PAST_EVENT;
import static nasa.testutil.TypicalActivities.WRONG_EVENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * An EventTest class to test the functionality of Event.
 */
class EventTest {

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
