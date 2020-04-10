package nasa.logic.calendar;

import nasa.model.activity.Schedule;

public class IcsSchedule {

    private Schedule schedule;

    public IcsSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean hasSchedule() {
        return !(schedule.getType() == 0);
    }

    public String getIcsRule() {
        String frequency = "FREQ=";
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
