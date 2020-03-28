package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's name in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, or special characters from "
                    + "this set {&, %, (, ), -, /, ', ,}. The name should also not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[\\sA-Za-z0-9\\()&%/',-]+$+";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid recipe name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = removeExtraWhitespace(name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return (!test.isBlank()) && test.matches(VALIDATION_REGEX);
    }

    /**
     * Removes additional whitespaces between words (ie. words should only have one whitespace between them)
     */
    private String removeExtraWhitespace(String stringToProcess) {
        return stringToProcess.replaceAll("\\s{2,}", " ");
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
