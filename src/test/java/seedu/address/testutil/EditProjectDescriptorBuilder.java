package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.edit.EditProjectDescriptor;
import seedu.address.model.item.Project;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditProjectDescriptor objects.
 */
public class EditProjectDescriptorBuilder {
    private EditProjectDescriptor descriptor;

    public EditProjectDescriptorBuilder() {
        descriptor = new EditProjectDescriptor();
    }

    public EditProjectDescriptorBuilder(EditProjectDescriptor descriptor) {
        this.descriptor = new EditProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProjectDescriptor} with fields containing {@code project}'s details
     */
    public EditProjectDescriptorBuilder(Project project) {
        descriptor = new EditProjectDescriptor();
        descriptor.setName(project.getName());
        descriptor.setTime(project.getTime());
        descriptor.setWebsite(project.getWebsite());;
        descriptor.setDescription(project.getDescription());
        descriptor.setTags(project.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withWebsite(String website) {
        descriptor.setWebsite(new Website(website));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        tagSet = SampleDataUtil.getTagSet(tags);
        descriptor.setTags(tagSet);
        return this;
    }

    public EditProjectDescriptor build() {
        return descriptor;
    }
}
