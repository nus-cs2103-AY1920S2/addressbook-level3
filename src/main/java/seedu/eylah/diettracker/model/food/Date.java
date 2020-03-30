package seedu.eylah.diettracker.model.food;

import java.time.LocalDateTime;

public class Date {

    public final LocalDateTime value;

    /**
     * Constructs a {@code Date} at current time.
     */
    public Date() {
        this.value = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return value.toLocalDate().toString();
    }

    @Override
    public boolean equals(Object other) {
        return value.equals(other);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
