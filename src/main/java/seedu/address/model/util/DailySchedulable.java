package seedu.address.model.util;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Interface for objects that can be scheduled and shown on calendar.
 * Objects of this type should return information to compare within the same date,
 * i.e. beginning time and ending time.
 */
public interface DailySchedulable {
    Optional<LocalTime> getComparableTime();
}
