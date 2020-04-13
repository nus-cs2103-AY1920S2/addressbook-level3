package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Reminder in TAble.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Reminder implements Comparable<Reminder> {

    // Identity fields
    private final Description description;
    private final LocalDate date;
    private final LocalTime time;

    // Data fields
    private final boolean done;

    /**
     * Every field must be present and not null.
     */
    public Reminder(Description description, LocalDate date, LocalTime time, boolean done) {
        requireAllNonNull(description, date, time);
        this.description = description;
        this.date = date;
        this.time = time;
        this.done = done;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public boolean getDone() {
        return done;
    }

    /**
     * Returns true if both reminders have the same identity.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getDescription().equals(getDescription())
                && otherReminder.getDate().equals(getDate())
                && otherReminder.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, date, time);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .append(" Done: ")
                .append(getDone() ? "Yes" : "No");
        return builder.toString();
    }

    @Override
    public int compareTo(Reminder other) {
        if (getDone() == other.getDone()) {
            if (getDate().compareTo(other.getDate()) == 0) {
                return getTime().compareTo(other.getTime());
            } else {
                return getDate().compareTo(other.getDate());
            }
        } else {
            if (getDone()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
