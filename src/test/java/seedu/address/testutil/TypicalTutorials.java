package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialTAble;

/**
 * A utility class containing a list of {@code Tutorial} objects to be used in tests.
 */
public class TypicalTutorials {

    private static Tutorial tutorial1;
    private static Tutorial tutorial2;

    static {
        try {
            tutorial1 = new TutorialBuilder().withModCode("CS2103").withTutorialName("T07")
                    .withBeginTime("15:00").withEndTime("16:00")
                    .withDay("3").withLocation("SR1").build();
            tutorial2 = new TutorialBuilder().withModCode("CS2100").withTutorialName("T01")
                    .withBeginTime("10:00").withEndTime("12:00")
                    .withDay("5").withLocation("B113").build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an {@code TutorialTAble} with all the typical tutorials.
     */
    public static TutorialTAble getTypicalTutorialTAble() {
        TutorialTAble tt = new TutorialTAble();
        for (Tutorial tut : getTypicalTutorials()) {
            tt.addTutorial(tut);
        }
        return tt;
    }

    public static Tutorial getTutorial1() {
        return tutorial1;
    }

    public static Tutorial getTutorial2() {
        return tutorial2;
    }

    public static List<Tutorial> getTypicalTutorials() {
        return new ArrayList<>(Arrays.asList(tutorial1, tutorial2));
    }
}
