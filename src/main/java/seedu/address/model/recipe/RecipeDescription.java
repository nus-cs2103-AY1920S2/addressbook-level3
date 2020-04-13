package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's description in the cookbook.
 * Guarantees: immutable; is valid as declared in {@link #isValidRecipeDescription(String)}
 */
public class RecipeDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Recipe descriptions should only contain alphanumeric characters, spaces and punctuations, "
                    + "and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Graph}][\\p{Graph} ]*";

    public final String fullRecipeDescription;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid Description.
     */
    public RecipeDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidRecipeDescription(description), MESSAGE_CONSTRAINTS);
        fullRecipeDescription = description;
    }

    /**
     * Returns true if a given string is a valid recipe Description.
     */
    public static boolean isValidRecipeDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullRecipeDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeDescription // instanceof handles nulls
                && fullRecipeDescription.equals(((RecipeDescription) other).fullRecipeDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullRecipeDescription.hashCode();
    }

}
