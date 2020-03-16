package seedu.address.model.nusmodule;

import static java.util.Objects.requireNonNull;

/**
 * Represents a NUS module's module code.
 */
public class ModuleCode {
    public final String code;

    public ModuleCode(String code) {
        requireNonNull(code);
        this.code = code;
    }
}
