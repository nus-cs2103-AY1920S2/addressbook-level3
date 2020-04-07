package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Event's duration.
 * Guarantees: immutable, is valid as declared in {@Link #isValidDuration(String)}
 */
public class Duration {
    public static final String MESSAGE_CONSTRAINTS = "Duration cannot be left empty, "
            + "please give an estimate of duration if you are unable to get exact timing"
            + " and round off to the nearest hour. "
            + "For instance, if your appointment is half an hour long, please input "
            + "0.5 as the duration.";
    public static final String VALIDATION_REGEX = "[0-9]{1,2}";

    // Instance field
    public final String duration;

    /**
     * Constructs a {@code Duration}.
     * durationHours + durationMinutes = total duration of the event
     */
    public Duration(String duration) {
        requireAllNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        this.duration = duration;
    }

    /**
     * @param testDuration The input duration to be tested.
     * Returns true if the input duration contains only numbers and at least one digit.
     */
    public static boolean isValidDuration(String testDuration) {
        return testDuration.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return duration + " hours";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.assignment.Workload // instanceof handles nulls
                && duration.equals(((seedu.address.model.event.Duration) other).duration)); // state check
    }

}
