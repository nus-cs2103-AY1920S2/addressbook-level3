package seedu.planner.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Class to represent the Module Code of a {@code Module}.
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should only contain alphanumeric characters, and it should not be blank";

    /*
     * All characters must be alphanumeric, and there must be at least one character.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]+";

    /**
     * 2 to 3 case-insensitive letters, then exactly 4 digits, then at most one case-insensitive letter.
     */
    public static final String VALIDATION_REGEX_STRICT = "[\\p{Alpha}]{2,3}[\\p{Digit}]{4}[\\p{Alpha}]?";

    public final String value;

    public ModuleCode(String code) {
        requireNonNull(code);
        checkArgument(isValidModuleCode(code), MESSAGE_CONSTRAINTS);
        value = code.toUpperCase(); // Allow case insensitivity.
    }

    /**
     * Returns true if a given string has the format of a valid module code.
     * This does not validate if the module code corresponds to a module in NUS.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof ModuleCode)) {
            return false;
        } else {
            return value.equals(((ModuleCode) other).value);
        }
    }
}
