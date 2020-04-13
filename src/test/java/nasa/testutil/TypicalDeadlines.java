package nasa.testutil;

import nasa.model.activity.Deadline;
import nasa.model.activity.Schedule;

/**
 * Contains examples of deadlines use for testing.
 */
public class TypicalDeadlines {

    public static final String VALID_NAME = "tP";
    public static final String VALID_DATE = "13-02-2020 23:59";
    public static final String VALID_NOTE = "Finish UG and DG";
    public static final String VALID_DUE_DATE = "13-04-2020 23:59";
    public static final String VALID_PRIORITY = "5";
    public static final String VALID_SCHEDULE = new Schedule().toString();
    public static final String VALID_ISDONE = "true";

    public static final Deadline DEADLINE = new DeadlineBuilder().withName("Homework for tut")
            .withDueDate("19-02-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Deadline DEADLINE_LATE = new DeadlineBuilder().withName("Test")
            .withDueDate("12-01-2021 06:00")
            .withNote("pay attention to qns2").build();

    public static final Deadline CS2103T_DEADLINE = new DeadlineBuilder().withName(VALID_NAME)
            .withDateCreated(VALID_DATE)
            .withDueDate(VALID_DUE_DATE)
            .withNote(VALID_NOTE)
            .withPriority(VALID_PRIORITY)
            .withSchedule(VALID_SCHEDULE)
            .withIsDone(VALID_ISDONE)
            .build();



    private TypicalDeadlines() {} // prevents instantiation
}
