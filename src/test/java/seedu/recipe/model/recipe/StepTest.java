package seedu.recipe.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

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

        // blank step
        assertFalse(Step.isValidStep("")); // empty string
        assertFalse(Step.isValidStep(" ")); // spaces only
        assertFalse(Step.isValidStep("           ")); // spaces only

        // valid step
        assertTrue(Step.isValidStep("contact PeterJack_1190@example.com for recipe"));
        assertTrue(Step.isValidStep("a")); // minimal
        assertTrue(Step.isValidStep("test@localhost")); // alphabets only
        assertTrue(Step.isValidStep("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Step.isValidStep("123@145")); // numeric local part and domain name
        assertTrue(Step.isValidStep("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Step.isValidStep("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Step.isValidStep("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
