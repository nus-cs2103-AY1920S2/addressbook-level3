package seedu.recipe.testutil;

import seedu.recipe.model.Date;

/**
 * A utility class containing a list of {@code Date} objects to be used in tests.
 */
public class TypicalDates {
    public static final Date DATE_IN_PAST = new Date("2000-05-05");
    public static final Date DATE_IN_FUTURE = new Date("3000-05-05");
    public static final Date DATE_TODAY = Date.today();
}
