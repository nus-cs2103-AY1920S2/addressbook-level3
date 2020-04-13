package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTutorials.getTutorial1;
import static seedu.address.testutil.TypicalTutorials.getTutorial2;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.testutil.TutorialBuilder;

public class TutorialTimeClashTest {

    @Test
    public void timeClash() {
        Tutorial tutorial1 = getTutorial1();
        Tutorial tutorial2 = getTutorial2();

        // same tutorial -> returns true;
        assertTrue(tutorial1.timeClash(tutorial1));

        // null -> returns false;
        assertFalse(tutorial1.timeClash(null));

        // different type -> returns false;
        assertFalse(tutorial1.timeClash(5));

        // different day -> returns false;
        tutorial1 = new TutorialBuilder(tutorial1).withBeginTime("12:00").withEndTime("14:00").withDay("1").build();
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("12:00").withEndTime("14:00").withDay("2").build();
        assertFalse(tutorial1.timeClash(tutorial2));

        // same begin time and end time -> returns true
        tutorial1 = new TutorialBuilder(tutorial1).withBeginTime("12:00").withEndTime("14:00").withDay("1").build();
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("12:00").withEndTime("14:00").withDay("1").build();
        assertTrue(tutorial1.timeClash(tutorial2));

        // same begin time different end time -> returns true
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("12:00").withEndTime("13:00").withDay("1").build();
        assertTrue(tutorial1.timeClash(tutorial2));

        // different begin time same end time -> returns true
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("13:00").withEndTime("14:00").withDay("1").build();
        assertTrue(tutorial1.timeClash(tutorial2));

        // tutorial 2 starts before tutorial 1 ends -> returns true
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("13:00").withEndTime("15:00").withDay("1").build();
        assertTrue(tutorial1.timeClash(tutorial2));

        // tutorial 1 starts and ends within tutorial 2 period -> returns true
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("10:00").withEndTime("15:00").withDay("1").build();
        assertTrue(tutorial1.timeClash(tutorial2));

        // tutorial 1 before tutorial 2 -> returns false
        tutorial1 = new TutorialBuilder(tutorial1).withBeginTime("10:00").withEndTime("12:00").withDay("1").build();
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("13:00").withEndTime("14:00").withDay("1").build();
        assertFalse(tutorial1.timeClash(tutorial2));

        // tutorial 1 ends at the same time tutorial 2 starts -> returns false
        tutorial2 = new TutorialBuilder(tutorial2).withBeginTime("12:00").withEndTime("14:00").withDay("1").build();
        assertFalse(tutorial1.timeClash(tutorial2));
    }
}
