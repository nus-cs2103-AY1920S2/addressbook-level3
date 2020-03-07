package seedu.address.model.module;

/**
 * Abstract class to specify fields with getter and setters for modules.
 */
public abstract class Module {

    private ModuleCode moduleCode;

    /**
     * Constructs a {@code module}
     * @param moduleCode module code
     */
    public Module(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    //Priority priority;

    /**
     * Retrieve the moduleCode of the module.
     * @return String moduleCode
     */
    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    /**
     * Sets the module moduleCode to a new moduleCode.
     * Used for editing module code.
     * @param moduleCode of the module
     */
    public void setModuleCode(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Returns true if both are the same module.
     * This defines a stronger notion of equality between two activities.
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
        return otherModule.getModuleCode().equals(getModuleCode());
    }
}
