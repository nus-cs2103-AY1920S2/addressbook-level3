package seedu.address.model.transaction;

import javax.xml.crypto.Data;
import java.util.function.Predicate;

/**
 * Tests that a {@code Transactions}'s {@code DateTime} matches the DateTime object given.
 */
public class DateTimeInRangePredicate implements Predicate<Transaction> {
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    public DateTimeInRangePredicate(DateTime startDateTime, DateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getDateTime().isAfter(
                new DateTime(startDateTime.value.toLocalDate().atStartOfDay())
        )
                && transaction.getDateTime().isBefore(
                        new DateTime(endDateTime.value.toLocalDate().plusDays(1).atStartOfDay())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeInRangePredicate // instanceof handles nulls
                && startDateTime.equals(((DateTimeInRangePredicate) other).startDateTime)
                && startDateTime.equals(((DateTimeInRangePredicate) other).endDateTime)); // state check
    }
}
