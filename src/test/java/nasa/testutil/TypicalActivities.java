package nasa.testutil;

import nasa.model.activity.Activity;

/**
 * Contains examples of activities use for testing.
 */
public class TypicalActivities {

    public static final Activity DEADLINE = new DeadlineBuilder().withName("Homework for tut")
            .withDate("19-02-2021 06:00")
            .withDueDate("19-02-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Activity DEADLINE_LATE = new DeadlineBuilder().withName("Test")
            .withDate("11-01-2021 06:00")
            .withDueDate("12-01-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Activity WRONG_EVENT = new EventBuilder().withName("BasketBall MPSH")
            .withFromDate("02-02-2021 06:00")
            .withToDate("01-01-2021 06:00").build();

    public static final Activity CORRECT_EVENT = new EventBuilder().withName("Tennis")
            .withFromDate("02-04-2021 06:00")
            .withToDate("13-06-2021 06:00").build();

    public static final Activity PAST_EVENT = new EventBuilder().withName("Soccer")
            .withFromDate("02-04-2019 06:00")
            .withToDate("13-06-2019 06:00").build();

    private TypicalActivities() {} // prevents instantiation
}
