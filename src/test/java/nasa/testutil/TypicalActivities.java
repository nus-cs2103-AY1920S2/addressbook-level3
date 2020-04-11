package nasa.testutil;

import nasa.model.activity.Deadline;
import nasa.model.activity.Event;

/**
 * Contains examples of activities use for testing.
 */
public class TypicalActivities {

    public static final Deadline DEADLINE = new DeadlineBuilder().withName("Homework for tut")
            .withDueDate("19-02-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Deadline DEADLINE_LATE = new DeadlineBuilder().withName("Test")
            .withDueDate("12-01-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Event WRONG_EVENT = new EventBuilder().withName("BasketBall MPSH")
            .withFromDate("02-02-2021 06:00")
            .withToDate("01-01-2021 06:00").build();

    public static final Event CORRECT_EVENT = new EventBuilder().withName("Tennis")
            .withFromDate("02-04-2021 06:00")
            .withToDate("13-06-2021 06:00").build();

    public static final Event PAST_EVENT = new EventBuilder().withName("Soccer")
            .withFromDate("02-04-2019 06:00")
            .withToDate("13-06-2019 06:00").build();

    private TypicalActivities() {} // prevents instantiation
}
