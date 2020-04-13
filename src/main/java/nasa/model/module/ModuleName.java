package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents the official name of the module.
 */
public class ModuleName {

    public static final String MESSAGE_CONSTRAINTS =
            "Module name should only contain alphanumeric characters and spaces, and it should not blank.";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String moduleName;

    public ModuleName(String moduleName) {
        requireNonNull(moduleName);
        checkArgument(isValidModuleName(moduleName), MESSAGE_CONSTRAINTS);
        this.moduleName =
                Arrays.stream(moduleName
                        .trim().replaceAll(" +", " ")
                        .split(" "))
                        .map(word -> Character.toTitleCase(
                                word.charAt(0)) + word.substring(1)
                        .toLowerCase()).collect(Collectors.joining(" "));
    }

    public static boolean isValidModuleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getModuleName() {
        return moduleName;
    }

    @Override
    public String toString() {
        return moduleName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleName // instanceof handles nulls
                && moduleName.equals(((ModuleName) other).moduleName)); // state check
    }

    @Override
    public int hashCode() {
        return moduleName.hashCode();
    }
}
