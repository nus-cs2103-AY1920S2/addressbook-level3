package seedu.address.model.transaction;

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
        DateTime transactionDate = transaction.getDateTime();
        return (transactionDate.isAfter(startDateTime) || transactionDate.equals(startDateTime))
                && (transactionDate.isBefore(endDateTime) || transactionDate.equals(endDateTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeInRangePredicate // instanceof handles nulls
                && startDateTime.equals(((DateTimeInRangePredicate) other).startDateTime)
                && startDateTime.equals(((DateTimeInRangePredicate) other).endDateTime)); // state check
    }
}
