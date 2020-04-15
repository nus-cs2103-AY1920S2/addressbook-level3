package seedu.address.model.transaction;

import java.util.function.Predicate;

/**
 * Tests that a {@code Transactions}'s {@code DateTime} matches the DateTime object given.
 */
public class DateTimeEqualsPredicate implements Predicate<Transaction> {
    private final DateTime targetDateTime;

    public DateTimeEqualsPredicate(DateTime targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getDateTime().equals(targetDateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeEqualsPredicate // instanceof handles nulls
                && targetDateTime.equals(((DateTimeEqualsPredicate) other).targetDateTime)); // state check
    }
}
