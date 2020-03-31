package seedu.zerotoone.model.util;

import seedu.zerotoone.model.schedule.DateTime;
import seedu.zerotoone.model.schedule.OneTimeSchedule;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.workout.Workout;

/**
 * Contains utility methods for populating {@code ScheduleList} with sample data.
 */
public class SampleScheduleDataUtil {
    public static Schedule[] getSampleSchedules() {
        Workout[] workouts = SampleWorkoutDataUtil.getSampleWorkouts();
        Schedule[] schedules = new Schedule[3];

        // Schedule 1
        DateTime scheduleOneDateTime = new DateTime("2020-02-29 13:00");
        schedules[0] = new OneTimeSchedule(workouts[0], scheduleOneDateTime);

        // Schedule 2
        DateTime scheduleTwoDateTime = new DateTime("2020-04-01 23:00");
        schedules[1] = new OneTimeSchedule(workouts[1], scheduleTwoDateTime);

        // Schedule 3
        DateTime scheduleThreeDateTime = new DateTime("2021-09-29 12:00");
        schedules[2] = new OneTimeSchedule(workouts[2], scheduleThreeDateTime);
        return schedules;
    }

    public static ScheduleList getSampleScheduleList() {
        ScheduleList sampleScheduleList = new ScheduleList();
        for (Schedule schedule : getSampleSchedules()) {
            sampleScheduleList.addSchedule(schedule);
        }
        return sampleScheduleList;
    }

}
