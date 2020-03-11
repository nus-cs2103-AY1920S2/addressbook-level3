package seedu.address.model.profile.course.module;

/**
 * Represents a Module's code in the address book.
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidCode(String)} // to be implemented
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should have 1-3 capital letters at the front, 0-5 capital letters at the back "
            + "and exactly 4 digits in between";

    public static final String VALIDATION_REGEXES = "[A-Z]{2,4}[\\d]{4}[A-Z]{0,5}$";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.moduleCode = moduleCode;
    }

    public static boolean isValidCode(String moduleCode) {
        return moduleCode.matches(VALIDATION_REGEXES);
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }

    // methods to be implemented
    // hashCode()
}
