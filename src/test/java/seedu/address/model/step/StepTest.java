package seedu.address.model.step;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StepTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Step(null));
    }

    @Test
    public void constructor_invalidStep_throwsIllegalArgumentException() {
        String invalidStep = "";
        assertThrows(IllegalArgumentException.class, () -> new Step(invalidStep));
    }

    @Test
    public void isValidStep() {
        // null step
        assertThrows(NullPointerException.class, () -> Step.isValidStep(null));

        // invalid step
        assertFalse(Step.isValidStep("")); // empty string
        assertFalse(Step.isValidStep(" ")); // spaces only

        // valid step
        assertTrue(Step.isValidStep("hahahaha")); // alphabets only
        assertTrue(Step.isValidStep("12345")); // numbers only
        assertTrue(Step.isValidStep("throw 2 eggs in the water")); // alphanumeric characters
        assertTrue(Step.isValidStep("Sear the meat")); // with capital letters
        assertTrue(Step.isValidStep("Peel the egg. Then throw the egg in the soup.")); // long names
    }
}

