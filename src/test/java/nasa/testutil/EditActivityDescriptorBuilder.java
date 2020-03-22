package nasa.testutil;

import nasa.logic.commands.EditActivityCommand.EditActivityDescriptor;
import nasa.model.activity.Activity;
import nasa.model.activity.Date;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;

/**
 * A utility class to help with building EditActivityDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(EditActivityDescriptor descriptor) {
        this.descriptor = new EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activity}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new EditActivityDescriptor();
        descriptor.setName(activity.getName());
        descriptor.setNote(activity.getNote());
        descriptor.setPriority(activity.getPriority());
        descriptor.setDate(activity.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    public EditActivityDescriptor build() {
        return descriptor;
    }
}
