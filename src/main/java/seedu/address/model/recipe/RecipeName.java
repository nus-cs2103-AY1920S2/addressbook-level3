package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's name in the cookbook.
 * Guarantees: immutable; is valid as declared in {@link #isValidRecipeName(String)}
 */
public class RecipeName {

    public static final String MESSAGE_CONSTRAINTS =
            "Recipe names should only contain alphanumeric characters, spaces and punctuations, "
                    + "and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Graph}][\\p{Graph} ]*";

    public final String fullRecipeName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public RecipeName(String name) {
        requireNonNull(name);
        checkArgument(isValidRecipeName(name), MESSAGE_CONSTRAINTS);
        fullRecipeName = name;
    }

    /**
     * Returns true if a given string is a valid recipe name.
     */
    public static boolean isValidRecipeName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullRecipeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeName // instanceof handles nulls
                && fullRecipeName.equals(((RecipeName) other).fullRecipeName)); // state check
    }

    @Override
    public int hashCode() {
        return fullRecipeName.hashCode();
    }

}
