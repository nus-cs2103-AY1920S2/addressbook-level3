//@@author yaojiethng
package seedu.jelphabot.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's module code in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode implements Comparable<ModuleCode> {

    public static final String MESSAGE_CONSTRAINTS = "Module Code should be of the format [faculty][code][variant] "
                                                         + "and adhere to the following constraints:\n"
                                                         + "1. The faculty should only contain alphabetical "
                                                         + "characters.\n"
                                                         + "2. This is followed by a reference code, and optionally a"
                                                         + " variant tag.\n"
                                                         + "    - The reference code must be 4 digits long.\n"
                                                         + "    - The variant tag is an optional alphabetical "
                                                         + "character.\n";
    private static final String FACULTY_REGEX = "^[A-Za-z]{2,3}";
    private static final String REFERENCE_CODE = "\\d{4}";
    private static final String VARIANT_TAG = "[A-Za-z]?$";
    public static final String VALIDATION_REGEX = FACULTY_REGEX + REFERENCE_CODE + VARIANT_TAG;

    public final String value;

    /**
     * Constructs an {@code ModuleCode}.
     *
     * @param moduleCode code A valid module code address.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        moduleCode = moduleCode.toUpperCase();
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        value = moduleCode;
    }

    /**
     * Returns if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof ModuleCode // instanceof handles nulls
                           && value.equals(((ModuleCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(ModuleCode o) {
        return this.toString().compareTo(o.toString());
    }
}
