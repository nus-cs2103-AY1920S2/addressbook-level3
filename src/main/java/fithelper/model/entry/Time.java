package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;

/**
 * Represents a Entry's time in FitHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in format: time string hh:mm";

    public final LocalDate date;
    public final LocalTime time;
    public final LocalDateTime dateTime;
    public final String value;

    /**
     * Constructs an {@code Time}.
     *
     * @param timeStr A valid time.
     */
    public Time(String timeStr) {
        requireNonNull(timeStr);
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List groups = parser.parse(timeStr);
        checkArgument(groups.size() > 0, MESSAGE_CONSTRAINTS);
        DateGroup dateGroup = (DateGroup) groups.get(0);
        checkArgument(dateGroup.getDates().size() > 0, MESSAGE_CONSTRAINTS);
        Date localDate = dateGroup.getDates().get(0);
        dateTime = LocalDateTime.ofInstant(localDate.toInstant(), ZoneId.systemDefault());

        time = dateTime.toLocalTime();
        date = dateTime.toLocalDate();
        String truncatedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        value = concat(truncatedTime, date);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getDateStr() {
        return this.date.toString();
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        boolean isValid = true;
        String delims = " ";
        String[] tokens = test.split(delims);
        if (tokens.length < 2) {
            isValid = false;
        } else {
            delims = ":";
            String[] time = tokens[tokens.length - 1].split(delims);
            if (time.length != 2) {
                isValid = false;
            } else {
                com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
                List groups = parser.parse(test);
                if (groups.size() <= 0) {
                    isValid = false;
                } else {
                    DateGroup dateGroup = (DateGroup) groups.get(0);
                    if (dateGroup.getDates().size() <= 0) {
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }

    public String concat(String time, LocalDate date) {
        return date + " " + time;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
