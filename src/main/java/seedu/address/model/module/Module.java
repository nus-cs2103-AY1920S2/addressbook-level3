package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.util.Description;

/**
 * Represents a Module in Mod Manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final ModuleCode moduleCode;

    // Data fields
    private Description description;

    public Module(ModuleCode moduleCode, Description description) {
        requireAllNonNull(moduleCode, description);
        this.moduleCode = moduleCode;
        this.description = description;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both modules have the same module code.
     *
     * @param otherModule The other module to compare with.
     * @return true if both modules have the same module code.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode());
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     *
     * @param other The other object to compare with.
     * @return true if both modules are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode()).append("\n");
        if (getDescription().value != null) {
            builder.append("Description: ").append(getDescription());
        }
        return builder.toString();
    }

}
