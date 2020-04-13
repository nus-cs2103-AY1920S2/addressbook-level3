package seedu.address.model.util;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;

/**
 * A comparator to compare two items in a Calendar day
 */
public class DailySchedulableComparator implements Comparator<DailySchedulable> {

    @Override
    public int compare(DailySchedulable o1, DailySchedulable o2) {
        Optional<LocalTime> o1Time = o1.getComparableTime();
        Optional<LocalTime> o2Time = o2.getComparableTime();

        if (o1Time.isEmpty()) {
            return -1;
        }

        if (o2Time.isEmpty()) {
            return 1;
        }

        LocalTime firstTime = o1Time.get();
        LocalTime secondTime = o2Time.get();
        LocalTime noTime = LocalTime.parse("00:00");
        //put all day task (ie task with no time) behind those with actual time
        if (firstTime.equals(noTime) && !secondTime.equals(noTime)) {
            return 1;
        }
        if (secondTime.equals(noTime) && !firstTime.equals(noTime)) {
            return -1;
        }

        return o1Time.get().compareTo(o2Time.get());
    }
}
