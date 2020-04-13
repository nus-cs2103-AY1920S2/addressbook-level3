package seedu.nova.model.util.time.slotlist;

import java.time.Duration;
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
 * container for WeekDayDuration
 */
public class WeekDaySlotList implements SlotList<WeekDayDuration>, Copyable<WeekDaySlotList> {
    private TreeSet<WeekDayDuration> freeSlotSet;
    private TreeMap<Integer, WeekDayDuration> freeSlotMap;

    public WeekDaySlotList() {
        this(new TreeSet<>(), new TreeMap<>());
        addDuration(new WeekDayDuration());
    }

    private WeekDaySlotList(TreeSet<WeekDayDuration> freeSlotSet, TreeMap<Integer, WeekDayDuration> freeSlotMap) {
        this.freeSlotSet = freeSlotSet;
        this.freeSlotMap = freeSlotMap;
    }

    @Override
    public List<WeekDayDuration> getSlotList() {
        return new ArrayList<>(this.freeSlotSet);
    }

    @Override
    public List<WeekDayDuration> getSlotList(Duration greaterThan) {
        WeekDayDuration d = WeekDayDuration.parseDuration(greaterThan);
        return new ArrayList<>(this.freeSlotSet.tailSet(d));
    }

    @Override
    public List<WeekDayDuration> getSlotContaining(TimedDuration d) {
        return this.freeSlotSet.stream().parallel().filter(x -> x.isSubsetOf(d)).collect(Collectors.toList());
    }

    @Override
    public List<WeekDayDuration> getSlotAfter(LocalDateTime ldt) {
        return null;
    }

    /**
     * cast into week day duration
     *
     * @param td
     * @return
     */
    private WeekDayDuration cast(TimedDuration td) {
        if (td instanceof DateTimeDuration) {
            return ((DateTimeDuration) td).toWeekDayDuration();
        } else {
            return (WeekDayDuration) td;
        }
    }

    @Override
    public void includeDuration(TimedDuration td) {
        WeekDayDuration ed = cast(td);
        WeekDayDuration lastSlot = this.freeSlotMap.floorEntry(ed.getEndValue()).getValue();
        SortedMap<Integer, WeekDayDuration> tailMap = this.freeSlotMap.tailMap(ed.getStartValue());
        tailMap = tailMap.headMap(ed.getEndValue());

        if (ed.isConnected(lastSlot)) {
            deleteDuration(lastSlot);
            ed = new WeekDayDuration(lastSlot.getStartDay(), lastSlot.getStartTime(), ed.getEndDay(),
                    ed.getEndTime());
        }
        for (Map.Entry<Integer, WeekDayDuration> e : tailMap.entrySet()) {
            deleteDuration(e.getValue());
            ed = new WeekDayDuration(ed.getStartDay(), ed.getStartTime(), e.getValue().getEndDay(),
                    e.getValue().getEndTime());
        }
        addDuration(ed);
    }

    @Override
    public void excludeDuration(TimedDuration td) {
        WeekDayDuration ed = cast(td);
        WeekDayDuration lastSlot = this.freeSlotMap.floorEntry(ed.getEndValue()).getValue();
        SortedMap<Integer, WeekDayDuration> tailMap = this.freeSlotMap.tailMap(ed.getStartValue());
        tailMap = tailMap.headMap(ed.getEndValue());

        if (ed.isOverlapping(lastSlot)) {
            deleteDuration(lastSlot);
            List<TimedDuration> comp = lastSlot.relativeComplementOf(ed);
            comp.forEach(this::addDuration);
        }
        for (Map.Entry<Integer, WeekDayDuration> e : tailMap.entrySet()) {
            deleteDuration(lastSlot);
            List<TimedDuration> comp = lastSlot.relativeComplementOf(ed);
            comp.forEach(this::addDuration);
        }
    }

    @Override
    public boolean isSupersetOf(TimedDuration td) {
        return this.freeSlotSet.stream().parallel().anyMatch(x -> x.isSubsetOf(td));
    }

    /**
     * Intersection with
     *
     * @param lst timed duration
     * @return list of intersection durations
     */
    public List<WeekDayDuration> intersectWith(TimedDuration lst) {
        return this.freeSlotSet.stream()
                .parallel()
                .map(x -> (WeekDayDuration) x.intersectWith(lst))
                .filter(x -> !x.isZero()).collect(Collectors.toList());
    }

    private void addDuration(TimedDuration dd) {
        WeekDayDuration d = (WeekDayDuration) dd;
        this.freeSlotMap.put(d.getStartValue(), d);
        this.freeSlotSet.add(d);
    }

    private void deleteDuration(TimedDuration dd) {
        WeekDayDuration d = (WeekDayDuration) dd;
        this.freeSlotMap.remove(d.getStartValue());
        this.freeSlotSet.remove(d);
    }

    @Override
    public WeekDaySlotList getCopy() {
        return new WeekDaySlotList(new TreeSet<>(this.freeSlotSet), new TreeMap<>(this.freeSlotMap));
    }
}
