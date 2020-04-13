package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.edit.EditInternshipDescriptor;
import seedu.address.model.item.Internship;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditInternshipDescriptor objects.
 */
public class EditInternshipDescriptorBuilder {

    private EditInternshipDescriptor descriptor;

    public EditInternshipDescriptorBuilder() {
        descriptor = new EditInternshipDescriptor();
    }

    public EditInternshipDescriptorBuilder(EditInternshipDescriptor descriptor) {
        this.descriptor = new EditInternshipDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditInternshipDescriptor} with fields containing {@code internship}'s details
     */
    public EditInternshipDescriptorBuilder(Internship internship) {
        descriptor = new EditInternshipDescriptor();
        descriptor.setName(internship.getName());
        descriptor.setRole(internship.getRole());;
        descriptor.setFrom(internship.getFrom());
        descriptor.setTo(internship.getTo());
        descriptor.setDescription(internship.getDescription());
        descriptor.setTags(internship.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withFrom(String from) {
        descriptor.setFrom(new Time(from));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withTo(String to) {
        descriptor.setTo(new Time(to));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        tagSet = SampleDataUtil.getTagSet(tags);
        descriptor.setTags(tagSet);
        return this;
    }

    public EditInternshipDescriptor build() {
        return descriptor;
    }
}
