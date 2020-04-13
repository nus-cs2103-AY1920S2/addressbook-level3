package seedu.address.model.reminder;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Reminder}'s {@code Date} matches with the date given.
 */
public class DatePredicate implements Predicate<Reminder> {
    private final LocalDate date;

    public DatePredicate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean test(Reminder reminder) {
        return date.equals(reminder.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DatePredicate // instanceof handles nulls
                && date.equals(((DatePredicate) other).date)); // state check
    }

}
