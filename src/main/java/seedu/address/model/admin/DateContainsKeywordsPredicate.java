package seedu.address.model.admin;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Date}'s {@code Date} matches any of the keywords given.
 */
public class DateContainsKeywordsPredicate implements Predicate<Date> {
    private final LocalDate thisDate;

    public DateContainsKeywordsPredicate(LocalDate date) {
        thisDate = date;
    }

    @Override
    public boolean test(Date date) {
        return thisDate.equals(date.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateContainsKeywordsPredicate // instanceof handles nulls
                && thisDate.equals(((DateContainsKeywordsPredicate) other).thisDate)); // state check
    }
}
