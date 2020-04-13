package seedu.nova.model.plan;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.Day;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.WeakEvent;
import seedu.nova.model.util.time.duration.DateTimeDuration;

/**
 * Task which generates definite events
 */
public class StrongTask extends Task {

    private StrongTask(TaskDetails details) {
        super(details);
    }

    public static StrongTask get(String name, Duration duration, TaskFreq freq) {
        return new StrongTask(new TaskDetails(name, duration, freq));
    }

    @Override
    public Event generateEventOnDay(LocalDate date, Schedule sc) throws ImpossibleTaskException {
        if (hasEventOn(date) && sc.hasEvent(getEventOn(date))) {
            return null;
        } else {
            Day d = sc.getDay(date);
            List<DateTimeDuration> dtdLst;
            if (d == null) {
                dtdLst = new Day(date).getFreeSlotList().getSlotList(getBaseDuration());
            } else {
                dtdLst = d.getFreeSlotList().getSlotList(getBaseDuration());
            }
            if (dtdLst.isEmpty()) {
                throw new ImpossibleTaskException();
            } else {
                DateTimeDuration dtd = getBestTimeframe(dtdLst);
                Event newEvent = new WeakEvent(getName(), dtd, this);
                addEvent(newEvent);
                sc.addEvent(newEvent);
                return newEvent;
            }
        }
    }

    private DateTimeDuration getBestTimeframe(List<DateTimeDuration> freeSlots) {
        assert !freeSlots.isEmpty() : "no free slot to choose";
        List<DateTimeDuration> lst = new ArrayList<>(freeSlots);
        Collections.shuffle(lst);
        DateTimeDuration dtd = lst.get(0);
        return new DateTimeDuration(dtd.getStartDateTime(), dtd.getStartDateTime().plus(getBaseDuration()));
    }

    @Override
    public String toString() {
        return String.format("%s\nNo. of weeks done: %d\nEvents scheduled:\n %s\n", super.toString(), size(),
                listEvents());
    }
}
