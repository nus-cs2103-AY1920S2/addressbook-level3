package seedu.address.model.transaction;

import java.util.function.Predicate;

import seedu.address.model.util.Money;

/**
 * Tests that a {@code Transactions}'s {@code Money} matches the Money object given.
 */
public class MoneyEqualsPredicate implements Predicate<Transaction> {
    private final Money targetMoney;

    public MoneyEqualsPredicate(Money targetMoney) {
        this.targetMoney = targetMoney;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getMoney().equals(targetMoney);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoneyEqualsPredicate // instanceof handles nulls
                && targetMoney.equals(((MoneyEqualsPredicate) other).targetMoney)); // state check
    }
}
