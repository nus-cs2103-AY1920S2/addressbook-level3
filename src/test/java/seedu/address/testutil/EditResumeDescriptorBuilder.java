package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.edit.EditResumeDescriptor;
import seedu.address.model.item.Resume;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditResumeDescriptor objects.
 */
public class EditResumeDescriptorBuilder {
    private EditResumeDescriptor descriptor;

    public EditResumeDescriptorBuilder() {
        descriptor = new EditResumeDescriptor();
    }

    public EditResumeDescriptorBuilder(EditResumeDescriptor descriptor) {
        this.descriptor = new EditResumeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditResumeDescriptor} with fields containing {@code resume}'s details
     */
    public EditResumeDescriptorBuilder(Resume resume) {
        descriptor = new EditResumeDescriptor();
        descriptor.setName(resume.getName());
        descriptor.setTags(resume.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditResumeDescriptor} that we are building.
     */
    public EditResumeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code EditResumeDescriptor} that we are building.
     */
    public EditResumeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        tagSet = SampleDataUtil.getTagSet(tags);
        descriptor.setTags(tagSet);
        return this;
    }

    public EditResumeDescriptor build() {
        return descriptor;
    }
}
