package seedu.nova.model.util.time.slotlist;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import seedu.nova.model.util.time.duration.TimedDuration;

/**
 * Container for timed duration
 * @param <T> timed duration
 */
public interface SlotList<T extends TimedDuration> {
    List<T> getSlotList();

    List<T> getSlotList(Duration greaterThan);

    List<T> getSlotContaining(TimedDuration d);

    List<T> getSlotAfter(LocalDateTime ldt);

    List<T> intersectWith(TimedDuration td);

    boolean isSupersetOf(TimedDuration td);

    void includeDuration(TimedDuration ed);

    void excludeDuration(TimedDuration ed);
}
