package NASA.testutil;

import NASA.model.activity.Activity;

/**
 * Contains examples of activities use for testing.
 */
public class TypicalActivities {

    public static final Activity DEADLINE = new ActivityBuilder().withName("deadline")
            .withDate("19-02-2020 06:00")
            .withNote("Do homework").build();

    public static final Activity WRONG_EVENT = new ActivityBuilder().withName("event")
            .withFromDate("02-02-2020 06:00")
            .withToDate("01-01-2020 06:00").build();

    public static final Activity CORRECT_EVENT = new ActivityBuilder().withName("event")
            .withFromDate("02-04-2020 06:00")
            .withToDate("13-06-2020 06:00").build();

    public static final Activity PAST_EVENT = new ActivityBuilder().withName("event")
            .withFromDate("02-04-2019 06:00")
            .withToDate("13-06-2019 06:00").build();

    private TypicalActivities() {} // prevents instantiation
}
