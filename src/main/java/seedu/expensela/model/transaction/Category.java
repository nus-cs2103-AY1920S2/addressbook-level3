package seedu.expensela.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's category.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS =
            "Valid categories are: FOOD,"
                    + " SHOPPING,"
                    + " TRANSPORT,"
                    + " GROCERIES,"
                    + " HEALTH,"
                    + " RECREATION,"
                    + " INCOME,"
                    + " UTILITIES OR"
                    + " MISC";

    /*
     * The first character of the category must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public final String transactionCategory;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CONSTRAINTS);
        transactionCategory = category.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        for (CategoryEnum c : CategoryEnum.values()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return transactionCategory;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && transactionCategory.equals(((Category) other).transactionCategory)); // state check
    }

    @Override
    public int hashCode() {
        return transactionCategory.hashCode();
    }

}
