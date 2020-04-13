package nasa.testutil;

import nasa.model.activity.Event;

/**
 * Contains examples of events use for testing.
 */
public class TypicalEvents {
    public static final Event WRONG_EVENT = new EventBuilder().withName("BasketBall MPSH")
            .withFromDate("02-02-2021 06:00")
            .withToDate("01-01-2021 06:00").build();

    public static final Event CORRECT_EVENT = new EventBuilder().withName("Tennis")
            .withFromDate("02-04-2021 06:00")
            .withToDate("13-06-2021 06:00").build();

    public static final Event PAST_EVENT = new EventBuilder().withName("Soccer")
            .withFromDate("02-04-2019 06:00")
            .withToDate("13-06-2019 06:00").build();

    private TypicalEvents() {} // prevents instantiation
}
