package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.tutorial.TutorialName;

public class TutorialNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorialName(null));
    }

    @Test
    public void constructor_invalidTutorialName_throwsIllegalArgumentException() {
        String invalidDTutorialName = "";
        assertThrows(IllegalArgumentException.class, () -> new TutorialName(invalidDTutorialName));
    }

    @Test
    public void isValidDescription() {
        // null tutorialName
        assertThrows(NullPointerException.class, () -> TutorialName.isValidTutorialName(null));

        // invalid description
        assertFalse(TutorialName.isValidTutorialName("")); // empty string
        assertFalse(TutorialName.isValidTutorialName("   ")); // spaces only
        assertFalse(TutorialName.isValidTutorialName("Super long tutorial name")); // exceeds 8 characters
        assertFalse(TutorialName.isValidTutorialName("T02!")); // with special characters

        // valid description
        assertTrue(TutorialName.isValidTutorialName("tutorial")); // alphabets only
        assertTrue(TutorialName.isValidTutorialName("T01")); // with capital letters and numbers
    }
}
