package com.flashspeed.model.deck;

import static java.util.Objects.requireNonNull;

import com.flashspeed.commons.util.AppUtil;

/**
 * Represents a Deck's name in FlashSpeed.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should contain at least one (non-whitespace) character, i.e. should not be blank";

    public final String name;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        name = name.strip();
        AppUtil.checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return !test.strip().isEmpty();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && name.equals(((Name) other).name)); // state check
    }

    /**
     * Checks if the lower case form of the name is equal to another object
     *
     * @param other the object being compared with.
     * @return True if the lowercase form of the name is equal to (@code other).
     */
    public boolean equalsLowerCase(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && name.toLowerCase().equals(((Name) other).name.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
