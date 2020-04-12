package seedu.address.model.nusmodule;

import static java.util.Objects.requireNonNull;

import seedu.address.searcher.Search;

/**
 * Represents a NUS module's module code.
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS = "Please enter valid module code";

    public final String code;

    public ModuleCode(String code) {
        requireNonNull(code);
        this.code = code;
    }

    /**
     * Returns if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        try {
            Search.findModule(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && code.equals(((ModuleCode) other).code)); // state check
    }

}
