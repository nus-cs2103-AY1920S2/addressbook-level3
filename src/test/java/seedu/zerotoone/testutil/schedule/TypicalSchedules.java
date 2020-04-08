package seedu.zerotoone.testutil.schedule;

import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_JULY;
import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_JUNE;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.zerotoone.model.schedule.DateTime;
import seedu.zerotoone.model.schedule.OneTimeSchedule;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.workout.Workout;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */
public class TypicalSchedules {

    private static final List<Workout> workouts = getTypicalWorkouts();

    public static final OneTimeSchedule SCHEDULE_AT_FIRST_JUNE = new OneTimeScheduleBuilder()
            .withWorkout(workouts.get(0))
            .withDateTime(new DateTime(VALID_DATETIME_JUNE))
            .build();
    public static final OneTimeSchedule SCHEDULE_AT_FIRST_JULY = new OneTimeScheduleBuilder()
            .withWorkout(workouts.get(1))
            .withDateTime(new DateTime(VALID_DATETIME_JULY))
            .build();

    private TypicalSchedules() {} // prevents instantiation

    /**
     * Returns an {@code ScheduleList} with all the typical workouts.
     */
    public static ScheduleList getTypicalScheduleList() {
        ScheduleList scheduleList = new ScheduleList();
        for (Schedule schedule : getTypicalSchedules()) {
            scheduleList.addSchedule(schedule);
        }
        return scheduleList;
    }

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(SCHEDULE_AT_FIRST_JUNE, SCHEDULE_AT_FIRST_JULY));
    }
}
