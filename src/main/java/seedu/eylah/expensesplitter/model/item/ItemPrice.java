package seedu.eylah.expensesplitter.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

/**
 * Represents an Item's price in Expense Splitter of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(BigDecimal)}
 */
public class ItemPrice {

    /*
    Initially I coded ItemPrice using Double to represent the dollar amount. However when coding out the tests and
    doing further research online, using Double will lead to rounding errors and many credible sources have recommended
    against using Double to represent currency. Thus I have decided to replace Double with BigDecimal.
    I obtained this Regex expression from this link:
    https://stackoverflow.com/questions/16242449/regex-currency-validation
    As per discussed in the meeting, we are using flags to split it so the Scanner input takes in a String.
    To make String to BigDecimal is new BigDecimal("123.12")
    Big Decimal can also make use of Rounding to solve cases of $1.6667777, which should be rounded to $1.67

    Another issue I found out is new BigDecimal("19.90") (takes in a string is correct), but if it
    new BigDecimal(19.90) (takes in a double) then is wrong. it shows 19.89999999... Perhaps this is due to how
    the class is defined. it says it should take in a string only.
     */

    public static final String MESSAGE_CONSTRAINTS =
            "Price should be greater than $0";
    public static final String VALIDATION_REGEX = "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?$";
    //Price can either be 9 or 9.0 or 9.00 It can only accept a max of 2 decimal place.


    public final BigDecimal itemPrice;

    /**
     * Constructs a {@code ItemPrice}.
     *
     * @param price A valid price
     */
    public ItemPrice(BigDecimal price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        this.itemPrice = price;
    }

    /**
     * Returns true if a given price is a valid price.
     *
     * @param test if an ItemPrice is > 0 because ItemPrice cannot be <= 0 and can only contain digit.
     */
    public static boolean isValidPrice(BigDecimal test) {

        // These SOUT commands are for me to test whether BigDecimal is working as intended or not.
        //System.out.println(test.compareTo(BigDecimal.ZERO) > 0);
        //System.out.println(test.toString().matches(VALIDATION_REGEX));
        //System.out.println(test.toString());

        return test.compareTo(BigDecimal.ZERO) > 0 && test.toString().matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return itemPrice.toString();
    }


    /**
     * Checks if two Items have the same ItemPrice.
     *
     * @param other ItemPrice to be checked against.
     * @return True if two of the Items have the same ItemPrice, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemPrice // instanceof handles nulls
                && itemPrice == ((ItemPrice) other).itemPrice); // state check
    }

    @Override
    public int hashCode() {
        return itemPrice.hashCode();
    }
}
