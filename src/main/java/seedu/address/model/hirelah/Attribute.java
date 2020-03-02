package seedu.address.model.hirelah;

import static seedu.address.commons.util.AppUtil.checkArgument;

/*
 * Attribute
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>Attribute class represents the parameters that are
 * taken into account to assess the interviewee.</p>
 * @author AY1920S2-W15-2
 */

public class Attribute {
    public static final String MESSAGE_CONSTRAINTS =
            "A name of attribute should only contain alphabet characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "^[a-zA-Z\\s]*$";

    private String name;

    /**
     * Constructs an Attribute instance.
     * @param name The name of the attribute.
     */

    public Attribute(String name) {
        checkArgument(isValidAttributeName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */

    public static boolean isValidAttributeName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attribute // instanceof handles nulls
                && name.equals(((Attribute) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
