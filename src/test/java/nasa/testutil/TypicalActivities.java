package nasa.testutil;

import nasa.model.activity.Activity;

/**
 * Contains examples of activities use for testing.
 */
public class TypicalActivities {

    public static final Activity DEADLINE = new DeadlineBuilder().withName("Homework for tut")
            .withDueDate("19-02-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Activity CORRECT_EVENT = new EventBuilder().withName("Tennis")
            .withFromDate("02-04-2021 06:00")
            .withToDate("13-06-2021 06:00").build();

    private TypicalActivities() {} // prevents instantiation
}
