package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's name in TAble.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphabetical characters and spaces, and it should not be blank\n"
            + "The maximum length of a student's name is 70 characters.";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    private static final int CHAR_LIMIT = 70;

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        name = capitaliseName(name);
        name = removeExcessSpaces(name);
        fullName = name;
    }

    /**
     * Removes any excess spaces between words of the names if there are more than 1 space between each word.
     */
    public String removeExcessSpaces(String name) {
        return name.trim().replaceAll(" +", " ");
    }

    /**
     * Capitalises the first letter of the student's name.
     */
    public String capitaliseName(String name) {
        StringBuffer stringBuffer = new StringBuffer();
        char beforeLetter = ' ';
        for (int i = 0; i < name.length(); i++) {
            if (beforeLetter == ' ' && name.charAt(i) != ' ') {
                stringBuffer.append(Character.toUpperCase(name.charAt(i)));
            } else {
                stringBuffer.append(Character.toLowerCase(name.charAt(i)));
            }
            beforeLetter = name.charAt(i);
        }
        return stringBuffer.toString().trim();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= CHAR_LIMIT;
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
