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
public class WeakTask extends Task {
    private Duration maxDuration;
    private Duration total;

    private WeakTask(TaskDetails details, Duration maxDuration, Duration total) {
        super(details);
        this.maxDuration = maxDuration;
        this.total = total;
    }

    public static WeakTask get(String name, Duration mind, Duration maxd, Duration total)
            throws ImpossibleTaskException {
        if (mind.compareTo(total) > 0) {
            throw new ImpossibleTaskException();
        }
        return new WeakTask(new TaskDetails(name, mind, TaskFreq.DAILY), maxd, total);
    }

    public Duration getMaxDuration() {
        if (total.minus(totalEventDuration).compareTo(maxDuration) > 0) {
            return maxDuration;
        } else {
            return total.minus(totalEventDuration);
        }
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

    private DateTimeDuration getBestTimeframe(List<DateTimeDuration> freeSlots) throws ImpossibleTaskException {
        assert !freeSlots.isEmpty() : "no free slot to choose";
        if (getMaxDuration().isZero()) {
            throw new ImpossibleTaskException();
        }
        List<DateTimeDuration> lst = new ArrayList<>(freeSlots);
        Collections.sort(lst);
        Collections.reverse(lst);
        for (DateTimeDuration dtd : lst) {
            if (dtd.getDuration().compareTo(getMaxDuration()) < 0
                    && dtd.getDuration().compareTo(getBaseDuration()) > 0) {
                return dtd;
            } else {
                return new DateTimeDuration(dtd.getStartDateTime(), dtd.getStartDateTime().plus(getMaxDuration()));
            }
        }
        throw new ImpossibleTaskException();
    }

    @Override
    public String toString() {
        return String.format("%s\nPercentage done: %f\nEvents scheduled:\n %s\n",
                super.toString(), (
                        totalEventDuration.getSeconds() + 0.0) / total.getSeconds(),
                listEvents());
    }
}
