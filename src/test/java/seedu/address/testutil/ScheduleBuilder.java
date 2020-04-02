package seedu.address.testutil;

import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.EndTime;
//import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.StartTime;

/**
 * A utility class to help build Schedules.
 */
public class ScheduleBuilder {

    public static final String DEFAULT_DAY = ("MON");
    public static final String DEFAULT_START_TIME = "1100";
    public static final String DEFAULT_END_TIME = "1200";

    private Day day;
    private StartTime startTime;
    private EndTime endTime;

    public ScheduleBuilder() {
        day = new Day(DEFAULT_DAY);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
    }
//
//    public Schedule build() {
//        return new Schedule(day, startTime, endTime);
//    }
//
//    public ScheduleBuilder withDay(String day) {
//        this.day = new Day(day);
//        return this;
//    }
//
//
//    public ScheduleBuilder withStartTime(String st) {
//        this.startTime = new StartTime(st);
//        return this;
//    }
//
//    /**
//     * Adds endtime to the schedulebuilder chain.
//     * @param et
//     * @return
//     */
//    public ScheduleBuilder withEndTime(String et) {
//        this.endTime = new EndTime(et);
//        return this;
//    }

}
