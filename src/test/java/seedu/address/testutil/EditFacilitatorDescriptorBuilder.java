package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.module.ModuleCode;

/**
 * A utility class to help with building EditFacilitatorDescriptor objects.
 */
public class EditFacilitatorDescriptorBuilder {

    private FacilEditCommand.EditFacilitatorDescriptor descriptor;

    public EditFacilitatorDescriptorBuilder() {
        descriptor = new FacilEditCommand.EditFacilitatorDescriptor();
    }

    public EditFacilitatorDescriptorBuilder(FacilEditCommand.EditFacilitatorDescriptor descriptor) {
        this.descriptor = new FacilEditCommand.EditFacilitatorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFacilitatorDescriptor} with fields containing {@code facilitator}'s details
     */
    public EditFacilitatorDescriptorBuilder(Facilitator facilitator) {
        descriptor = new FacilEditCommand.EditFacilitatorDescriptor();
        descriptor.setName(facilitator.getName());
        descriptor.setPhone(facilitator.getPhone());
        descriptor.setEmail(facilitator.getEmail());
        descriptor.setOffice(facilitator.getOffice());
        descriptor.setModuleCodes(facilitator.getModuleCodes());
    }

    /**
     * Sets the {@code Name} of the {@code EditFacilitatorDescriptor} that we are building.
     */
    public EditFacilitatorDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditFacilitatorDescriptor} that we are building.
     */
    public EditFacilitatorDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditFacilitatorDescriptor} that we are building.
     */
    public EditFacilitatorDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Office} of the {@code EditFacilitatorDescriptor} that we are building.
     */
    public EditFacilitatorDescriptorBuilder withOffice(String office) {
        descriptor.setOffice(new Office(office));
        return this;
    }

    /**
     * Parses the {@code moduleCodes} into a {@code Set<ModuleCode>} and set it to the {@code EditFacilitatorDescriptor}
     * that we are building.
     */
    public EditFacilitatorDescriptorBuilder withModuleCodes(String... moduleCodes) {
        Set<ModuleCode> moduleCodeSet = Stream.of(moduleCodes).map(ModuleCode::new).collect(Collectors.toSet());
        descriptor.setModuleCodes(moduleCodeSet);
        return this;
    }

    public FacilEditCommand.EditFacilitatorDescriptor build() {
        return descriptor;
    }
}
