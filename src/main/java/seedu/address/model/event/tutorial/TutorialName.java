package seedu.address.model.event.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial's name in TAble.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorialName(String)}
 */

public class TutorialName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the tutorial name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String tutorialName;

    /**
     * Constructs a {@code TutorialName}.
     *
     * @param tutorialName A valid tutorial name.
     */
    public TutorialName(String tutorialName) {
        requireNonNull(tutorialName);
        checkArgument(isValidTutorialName(tutorialName), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialName;
    }

    /**
     * Returns true if a given string is a valid tutorial name.
     */
    public static boolean isValidTutorialName(String test) {
        if (test.length() > 8) {
            return false;
        }
        return test.matches(VALIDATION_REGEX);
    }

    public String getTutorialName() {
        return tutorialName;
    }

    @Override
    public String toString() {
        return tutorialName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TutorialName // instanceof handles nulls
                && tutorialName.equals(((TutorialName) other).tutorialName)); // state check
    }

    @Override
    public int hashCode() {
        return tutorialName.hashCode();
    }
}
