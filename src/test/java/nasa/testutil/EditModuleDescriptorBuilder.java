package nasa.testutil;

import nasa.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class EditModuleDescriptorBuilder {

    private EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setModuleName(module.getModuleName());
        descriptor.setModuleCode(module.getModuleCode());
    }

    /**
     * Sets the {@code ModuleName} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleName(String moduleName) {
        descriptor.setModuleName(new ModuleName(moduleName));
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleCode(String moduleCode) {
        descriptor.setModuleCode(new ModuleCode(moduleCode));
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
