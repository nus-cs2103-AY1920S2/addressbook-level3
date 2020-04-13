package seedu.nova.model.util.time.slotlist;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.nova.model.util.Copyable;
import seedu.nova.model.util.time.duration.DateTimeDuration;
import seedu.nova.model.util.time.duration.TimedDuration;
import seedu.nova.model.util.time.duration.WeekDayDuration;

/**
 * Container for DateTimeDuration
 */
public class DateTimeSlotList implements SlotList<DateTimeDuration>, Copyable<DateTimeSlotList> {
    private TreeSet<DateTimeDuration> freeSlotSet;
    private TreeMap<LocalDateTime, DateTimeDuration> freeSlotMap;

    public DateTimeSlotList(DateTimeDuration init) {
        this(new TreeSet<>(), new TreeMap<>());
        addDuration(init);
    }

    public DateTimeSlotList(List<DateTimeDuration> init) {
        this(new TreeSet<>(), new TreeMap<>());
        init.forEach(this::addDuration);
    }

    private DateTimeSlotList(TreeSet<DateTimeDuration> freeSlotSet,
                             TreeMap<LocalDateTime, DateTimeDuration> freeSlotMap) {
        this.freeSlotSet = freeSlotSet;
        this.freeSlotMap = freeSlotMap;
    }

    public static DateTimeSlotList ofDay(LocalDate date) {
        return new DateTimeSlotList(DateTimeDuration.parseDayFromDate(date));
    }

    public static DateTimeSlotList ofWeek(LocalDate sameWeekAs) {
        return new DateTimeSlotList(DateTimeDuration.parseWeekFromDate(sameWeekAs));
    }

    @Override
    public List<DateTimeDuration> getSlotList() {
        return new ArrayList<>(this.freeSlotSet);
    }

    @Override
    public List<DateTimeDuration> getSlotList(Duration greaterThan) {
        DateTimeDuration d = DateTimeDuration.parseDuration(greaterThan);
        return new ArrayList<>(this.freeSlotSet.tailSet(d));
    }

    public List<DateTimeDuration> getSlotContaining(TimedDuration d) {
        return this.freeSlotSet.stream().parallel().filter(d::isSubsetOf).collect(Collectors.toList());
    }

    public List<DateTimeDuration> getSlotAfter(LocalDateTime dateTime) {
        return new ArrayList<>(this.freeSlotMap.tailMap(dateTime).values());
    }

    public boolean isSupersetOf(TimedDuration d) {
        return this.freeSlotSet.stream().parallel().anyMatch(d::isSubsetOf);
    }

    public List<DateTimeDuration> getSlotOn(DayOfWeek day) {
        return this.freeSlotSet.stream().parallel().filter(x -> x.getStartDate().getDayOfWeek().equals(day)).collect(
                Collectors.toList());
    }

    /**
     * if td is WeekDayDuration, cast it into DateTimeDuration by setting the startDate to the same as the forst
     * entry of freeSlotMap
     *
     * @param td = timed duration
     * @return DateTimeDuration
     */
    private DateTimeDuration cast(TimedDuration td) {
        if (td instanceof WeekDayDuration) {
            return ((WeekDayDuration) td).toDateTimeDuration(this.freeSlotMap.firstEntry().getValue().getStartDate());
        } else {
            return (DateTimeDuration) td;
        }
    }

    /**
     * delete the duration ed from the list of durations.
     *
     * @param td timed duration to delete
     */
    @Override
    public void excludeDuration(TimedDuration td) {
        DateTimeDuration ed = cast(td);
        Map.Entry<LocalDateTime, DateTimeDuration> lastFreeSlot = this.freeSlotMap.floorEntry(ed.getStartDateTime());
        SortedMap<LocalDateTime, DateTimeDuration> nextFreeSlotMap = this.freeSlotMap.tailMap(ed.getStartDateTime());
        nextFreeSlotMap = nextFreeSlotMap.headMap(ed.getEndDateTime());

        if (lastFreeSlot != null && ed.isOverlapping(lastFreeSlot.getValue())) {
            deleteDuration(lastFreeSlot.getValue());
            List<TimedDuration> comp = lastFreeSlot.getValue().relativeComplementOf(ed);
            comp.forEach(this::addDuration);
        }
        for (Map.Entry<LocalDateTime, DateTimeDuration> e : nextFreeSlotMap.entrySet()) {
            deleteDuration(e.getValue());
            List<TimedDuration> comp = e.getValue().relativeComplementOf(ed);
            comp.forEach(this::addDuration);
        }
    }

    /**
     * exclude all the durations in another
     *
     * @param another date time slot list
     * @return compliment of another
     */
    public DateTimeSlotList relativeComplimentOf(DateTimeSlotList another) {
        DateTimeSlotList ans = getCopy();
        another.freeSlotSet.forEach(ans::excludeDuration);
        return ans;
    }

    /**
     * intersection with another timed duration
     *
     * @param lst another timed duration
     * @return list of intersection duration
     */
    public List<DateTimeDuration> intersectWith(TimedDuration lst) {
        return this.freeSlotSet.stream()
                .parallel()
                .map(x -> (DateTimeDuration) x.intersectWith(lst))
                .filter(x -> !x.isZero()).collect(Collectors.toList());
    }

    /**
     * add ed back to list
     *
     * @param td Timedduration
     */
    @Override
    public void includeDuration(TimedDuration td) {
        DateTimeDuration ed = cast(td);
        Map.Entry<LocalDateTime, DateTimeDuration> lastFreeSlot = this.freeSlotMap.floorEntry(ed.getStartDateTime());
        SortedMap<LocalDateTime, DateTimeDuration> nextFreeSlotMap = this.freeSlotMap.tailMap(ed.getStartDateTime());
        nextFreeSlotMap = nextFreeSlotMap.headMap(ed.getEndDateTime());

        if (lastFreeSlot != null && ed.isConnected(lastFreeSlot.getValue())) {
            deleteDuration(lastFreeSlot.getValue());
            ed = new DateTimeDuration(lastFreeSlot.getValue().getStartDateTime(), ed.getEndDateTime());
        }
        for (Map.Entry<LocalDateTime, DateTimeDuration> e : nextFreeSlotMap.entrySet()) {
            deleteDuration(e.getValue());
            ed = new DateTimeDuration(ed.getStartDateTime(), e.getValue().getEndDateTime());
        }
        addDuration(ed);
    }

    private void addDuration(TimedDuration dd) {
        DateTimeDuration d = (DateTimeDuration) dd;
        this.freeSlotMap.put(d.getStartDateTime(), d);
        this.freeSlotSet.add(d);
    }

    private void deleteDuration(TimedDuration dd) {
        DateTimeDuration d = (DateTimeDuration) dd;
        this.freeSlotSet.remove(d);
    }

    public int size() {
        return this.freeSlotSet.size();
    }

    public boolean contains(DateTimeDuration d) {
        return this.freeSlotSet.contains(d);
    }

    @Override
    public DateTimeSlotList getCopy() {
        return new DateTimeSlotList(new TreeSet<>(this.freeSlotSet), new TreeMap<>(this.freeSlotMap));
    }

    @Override
    public String toString() {
        return freeSlotSet.stream()
                .parallel()
                .map(DateTimeDuration::toString)
                .map(x -> String.format("%s\n", x))
                .reduce("", (x, y) -> x + y);
    }
}
