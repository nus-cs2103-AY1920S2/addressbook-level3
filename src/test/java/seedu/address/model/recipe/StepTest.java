package seedu.address.model.recipe;

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

        // blank step
        assertFalse(Step.isValidStep("")); // empty string
        assertFalse(Step.isValidStep(" ")); // spaces only

        // missing parts
        assertFalse(Step.isValidStep("@example.com")); // missing local part
        assertFalse(Step.isValidStep("peterjackexample.com")); // missing '@' symbol
        assertFalse(Step.isValidStep("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Step.isValidStep("peterjack@-")); // invalid domain name
        assertFalse(Step.isValidStep("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Step.isValidStep("peter jack@example.com")); // spaces in local part
        assertFalse(Step.isValidStep("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Step.isValidStep(" peterjack@example.com")); // leading space
        assertFalse(Step.isValidStep("peterjack@example.com ")); // trailing space
        assertFalse(Step.isValidStep("peterjack@@example.com")); // double '@' symbol
        assertFalse(Step.isValidStep("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Step.isValidStep("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Step.isValidStep("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Step.isValidStep("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Step.isValidStep("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Step.isValidStep("peterjack@example.com-")); // domain name ends with a hyphen

        // valid step
        assertTrue(Step.isValidStep("PeterJack_1190@example.com"));
        assertTrue(Step.isValidStep("a@bc")); // minimal
        assertTrue(Step.isValidStep("test@localhost")); // alphabets only
        assertTrue(Step.isValidStep("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Step.isValidStep("123@145")); // numeric local part and domain name
        assertTrue(Step.isValidStep("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Step.isValidStep("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Step.isValidStep("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
