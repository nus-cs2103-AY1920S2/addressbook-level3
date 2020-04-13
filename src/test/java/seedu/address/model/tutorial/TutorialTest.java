package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTutorials.getTutorial1;
import static seedu.address.testutil.TypicalTutorials.getTutorial2;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.testutil.TutorialBuilder;

public class TutorialTest {

    @Test
    public void equals() {
        Tutorial tutorial1 = getTutorial1();
        Tutorial tutorial2 = getTutorial2();

        // same values -> returns true
        Tutorial tutorialCopy = new TutorialBuilder(tutorial1).build();
        assertTrue(tutorial1.equals(tutorialCopy));

        // same object -> returns true
        assertTrue(tutorial1.equals(tutorial1));

        // null -> returns false
        assertFalse(tutorial1.equals(null));

        // different type -> returns false
        assertFalse(tutorial1.equals(5));

        // different tutorial -> returns false
        assertFalse(tutorial1.equals(tutorial2));

        // different modCode -> returns false
        Tutorial editedTutorial1 = new TutorialBuilder(tutorial1).withModCode("CS2100").build();
        assertFalse(tutorial1.equals(editedTutorial1));

        // different tutorialName -> returns false
        Tutorial editedTutorial2 = new TutorialBuilder(tutorial2).withTutorialName("T00").build();
        assertFalse(tutorial2.equals(editedTutorial2));

        // only same modCode and tutorialName -> returns true
        editedTutorial2 = new TutorialBuilder(tutorial2).withModCode("CS2103").withTutorialName("T07").build();
        assertTrue(tutorial1.equals(editedTutorial2));
    }
}
