package seedu.address.testutil.transaction;

import seedu.address.model.transaction.DateTime;

/**
 * A utility class containing a list of {@code DateTime} objects to be used in tests.
 */
public class TypicalDateTimes {
    public static final DateTime MARCH_FIRST_2020_10AM = new DateTimeBuilder("2020-03-01 10:00").build();
    public static final DateTime MARCH_FIRST_2020_5PM = new DateTimeBuilder("2020-03-01 17:00").build();
    public static final DateTime MARCH_SECOND_2020_5PM = new DateTimeBuilder("2020-03-02 17:00").build();
    public static final DateTime MARCH_THIRD_2020_10AM = new DateTimeBuilder("2020-03-03 10:00").build();
}
