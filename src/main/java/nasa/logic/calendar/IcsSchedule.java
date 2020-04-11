package nasa.logic.calendar;

import nasa.model.activity.Schedule;

/**
 * Class to get ics rule for scheduling of events/deadlines.
 */
public class IcsSchedule {

    private Schedule schedule;

    /**
     * Constructor.
     * @param schedule schedule of the activity
     */
    public IcsSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * If the activity has a schedule.
     * @return true if have, else false
     */
    public boolean hasSchedule() {
        return !(schedule.getType() == 0);
    }

    /**
     * Get the Ics rule for auto-scheduling
     * @return String ics format for scheduling
     */
    public String getIcsRule() {
        String frequency = "RRULE:FREQ=";
        int type = schedule.getType();
        if (type == 1) {
            frequency += "WEEKLY";
        } else if (type == 2) {
            frequency += "WEEKLY;INTERVAL=2";
        } else {
            frequency += "MONTHLY";
        }
        return frequency;
    }
}
