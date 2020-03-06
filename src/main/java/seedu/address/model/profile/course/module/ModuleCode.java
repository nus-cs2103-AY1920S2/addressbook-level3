package seedu.address.model.profile.course.module;

/**
 * Represents a Module's code in the address book.
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidCode(String)} // to be implemented
public class ModuleCode {

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
    // isValidCode()
    // hashCode()
}
