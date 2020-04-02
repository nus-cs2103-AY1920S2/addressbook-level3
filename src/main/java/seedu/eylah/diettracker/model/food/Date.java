package seedu.eylah.diettracker.model.food;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Food's date created in the diet tracker of EYLAH.
 * Guarantees: immutable;
 */
public class Date {

    public final LocalDateTime value;

    /**
     * Constructs a {@code Date} at current time.
     */
    public Date() {
        this.value = LocalDateTime.now();
    }

    /**
     * Constructs a {@code Date} at dateTime given.
     *
     * @param date string given in the format 2007-12-03T10:15:30
     */
    public Date(String date) {
        if (date == null || date == "") {
            this.value = LocalDateTime.now();
        } else {
            this.value = LocalDateTime.parse(date);
        }
    }

    public Date(LocalDateTime date) {
        this.value = date;
    }

    public String getStorageString() {
        return value.toString();
    }

    public LocalDateTime getValue() {
        return value;
    }

    public boolean isBefore(Date other) {
        return value.isBefore(other.getValue());
    }

    public boolean isAfter(Date other) {
        return value.isAfter(other.getValue());
    }

    public LocalDate getLocalDateValue () {
        return value.toLocalDate();
    }

    @Override
    public String toString() {
        return value.toLocalDate().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;

        return value.equals(otherDate.getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
