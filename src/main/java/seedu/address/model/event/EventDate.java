package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event's date.
 * Guarantees: immutable; is valid as declared in {@Link #isValidDate(String)} and {@Link #isValidTime(String)}
 */
public class EventDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Event date cannot be empty and has to be in yyyy-MM-dd HH:mm (24-hour clock) format. "
            + "Make sure event date is also after "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
    private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    // Instance variables
    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code EventDate}
     */
    public EventDate(String eventDate) {
        requireAllNonNull(eventDate);
        checkArgument(isValidEventDate(eventDate), MESSAGE_CONSTRAINTS);
        dateTime = LocalDateTime.parse(eventDate, inputFormat);
    }

    /**
     * Getter for datetime object.
     * @return datetime of EventDate
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @param testEventDate The input event date to be tested.
     * @return True if event date is not an empty string & follows required format.
     */
    public static boolean isValidEventDate(String testEventDate) {
        try {
            LocalDateTime.parse(testEventDate, inputFormat);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * @param testEventDate the input event date to be tested.
     * Returns true if the event date that the user is trying to set for a new event is already past the current
     * date and time.
     */
    public static boolean hasEventDatePassed(String testEventDate) {
        LocalDateTime dateTime = LocalDateTime.parse(testEventDate, inputFormat);

        if (dateTime.compareTo(LocalDateTime.now(ZoneId.of("Singapore"))) < 0) {
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
                || (other instanceof seedu.address.model.event.EventDate // instanceof handles nulls
                && dateTime.compareTo(((seedu.address.model.event.EventDate) other).dateTime) == 0); // state check
    }


}
