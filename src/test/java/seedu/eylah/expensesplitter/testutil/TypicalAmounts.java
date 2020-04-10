package seedu.eylah.expensesplitter.testutil;

import seedu.eylah.expensesplitter.model.person.Amount;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalAmounts {

    public static final Amount CHICKENRICE = new AmountBuilder().build();
    public static final Amount PIZZA = new AmountBuilder().withDoubleAmount(25.00).build();


    /**
     * Prevents public from instantiating a Typical Item.
     */
    private TypicalAmounts () {

    }
}
