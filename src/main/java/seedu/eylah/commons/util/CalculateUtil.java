package seedu.eylah.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import seedu.eylah.expensesplitter.model.person.Amount;


/**
 * Functions for handling calculations.
 */
public class CalculateUtil {

    private static final int DECIMALS = 2;
    private static final int EXTRA_DECIMALS = 4;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_DOWN;

    /**
     * Calculates the price per person by dividing the cost of item with the number of persons involved.
     *
     * @param itemPrice Price of the item.
     * @param numPersons Number of persons sharing the cost of the item.
     * @return Price per person.
     */
    public static Amount calculatePricePerPerson(BigDecimal itemPrice, BigDecimal numPersons) {
        BigDecimal pricePerPerson = itemPrice.divide(numPersons, DECIMALS, ROUNDING_MODE);

        return new Amount(pricePerPerson);
    }

    /**
     * Adds {@code toBeAdded} cost to the {@code original} amount.
     *
     * @param original Original amount.
     * @param toBeAdded Amount to be added to the original amount.
     * @return New total amount.
     */
    public static Amount addAmount(BigDecimal original, BigDecimal toBeAdded) {
        return new Amount(original.add(toBeAdded));
    }

    /**
     * Subtracts {@code toBeRemoved} cost from the {@code original} amount.
     * @param original Original amount.
     * @param toBeRemoved Amount to be subtracted from the original amount.
     * @return New total amount.
     */
    public static Amount removeAmount(BigDecimal original, BigDecimal toBeRemoved) {
        return new Amount(original.subtract(toBeRemoved));
    }
}
