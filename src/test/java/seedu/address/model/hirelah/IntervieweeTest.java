package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

class IntervieweeTest {

    @Test
    void constructor_invalidName_exceptionThrown() {
        try {
            Interviewee i = new Interviewee("12345", 4); // test numeric name
            fail();
        } catch (IllegalValueException e) {
            assertEquals(Interviewee.MESSAGE_CONSTRAINTS, e.getMessage());
        }
        try {
            Interviewee i = new Interviewee("", 4); // test empty string name
            fail();
        } catch (IllegalValueException e) {
            assertEquals(Interviewee.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    void giveAlias_moreThanOnce_exceptionThrown() throws IllegalValueException {
        Interviewee i = new Interviewee("Bob", 4);
        assertDoesNotThrow(() -> i.giveAlias("Bo"));
        try {
            i.giveAlias("B");
            fail();
        } catch (IllegalActionException e) {
            assertEquals("Interviewee already has an alias", e.getMessage());
        }
    }

    @Test
    void giveAlias_validIdentifier_hasAlias() throws IllegalValueException, IllegalActionException {
        Interviewee i = new Interviewee("Bob", 4);
        i.giveAlias("Bo");
        assertTrue(i.getAlias().isPresent());
    }
}
