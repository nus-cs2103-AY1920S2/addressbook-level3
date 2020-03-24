package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Order's timeStamp in the order book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeStamp(String)}
 */
public class TimeStamp {

    public static final String MESSAGE_CONSTRAINTS =
            "Timestamp should have a valid date and time and it should have space in between date and time\n"
            + "Note: Time should be in 24hour format and the input date and time cannot before current timestamp";
    public static final DateTimeFormatter FORMAT_CHECKER = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm");
    public final LocalDateTime timeStamp;
    public final String value;

    /**
     * Constructs a {@code Timestamp}.
     *
     * @param timeStamp A valid date and time.
     */
    public TimeStamp(String timeStamp) {
        requireNonNull(timeStamp);
        checkArgument(isValidTimeStamp(timeStamp), MESSAGE_CONSTRAINTS);
        this.timeStamp = LocalDateTime.parse(timeStamp, FORMAT_CHECKER);
        this.value = this.timeStamp.format(FORMAT_CHECKER);
    }

    /**
     * Returns true if a given string is a valid date and time.
     */
    public static boolean isValidTimeStamp(String test) {
        try {
            LocalDateTime userInput = LocalDateTime.parse(test, FORMAT_CHECKER.withResolverStyle(ResolverStyle.STRICT));
            if (userInput.compareTo(LocalDateTime.now()) < 0) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeStamp // instanceof handles nulls
                && value.equals(((TimeStamp) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
