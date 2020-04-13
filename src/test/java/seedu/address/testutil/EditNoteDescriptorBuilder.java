package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.edit.EditNoteDescriptor;
import seedu.address.model.item.Note;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditNoteDescriptor objects.
 */
public class EditNoteDescriptorBuilder {
    private EditNoteDescriptor descriptor;

    public EditNoteDescriptorBuilder() {
        descriptor = new EditNoteDescriptor();
    }

    public EditNoteDescriptorBuilder(EditNoteDescriptor descriptor) {
        this.descriptor = new EditNoteDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditNoteDescriptor} with fields containing {@code note}'s details
     */
    public EditNoteDescriptorBuilder(Note note) {
        descriptor = new EditNoteDescriptor();
        descriptor.setName(note.getName());
        descriptor.setTime(note.getTime());
        descriptor.setTags(note.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        tagSet = SampleDataUtil.getTagSet(tags);
        descriptor.setTags(tagSet);
        return this;
    }

    public EditNoteDescriptor build() {
        return descriptor;
    }
}
