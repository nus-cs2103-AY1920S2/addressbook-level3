package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Assignment's Deadline in the Schoolwork Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)} and {@link #isValidTime(String)}
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS =
            "Deadline cannot be empty and has to be in yyyy-MM-dd HH:mm (24-hour clock) format. "
                + "Make sure deadline is also after "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
    private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    // Instance variables
    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code Deadline}
     *
     * @param date The due date of the assignment.
     * @param time The time by which the assignment needs to be submitted on the due date.
     */
    public Deadline(String deadline) {
        requireAllNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);

        dateTime = LocalDateTime.parse(deadline, inputFormat);
    }

    /**
     * Getter for datetime object.
     * @return datetime of deadline
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @param testDeadline The input deadline to be tested.
     *
     * Returns true if deadline is not an empty string, follows the required format.
     */
    public static boolean isValidDeadline(String testDeadline) {
        try {
            LocalDateTime.parse(testDeadline, inputFormat);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * @param testDeadline The input deadline to be tested.
     *
     * Returns true if the deadline that the user is trying to set for a new assignment is already past the current
     * date and time.
     */
    public static boolean hasDeadlinePassed(String testDeadline) {
        LocalDateTime dateTime = LocalDateTime.parse(testDeadline, inputFormat);

        if (dateTime.isBefore(LocalDateTime.now(ZoneId.of("Singapore")))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return dateTime.format(outputFormat);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && dateTime.compareTo(((Deadline) other).dateTime) == 0); // state check
    }
}
