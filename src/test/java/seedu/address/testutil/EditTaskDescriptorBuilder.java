package seedu.address.testutil;

import seedu.address.logic.commands.task.TaskEditCommand;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {
    private TaskEditCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new TaskEditCommand.EditTaskDescriptor();
    }

    /**
     * Initializes the EditTaskDescriptorBuilder with data from {@code descriptor}
     */
    public EditTaskDescriptorBuilder(TaskEditCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new TaskEditCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Initializes the EditTaskDescriptorBuilder with data from {@code task}
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new TaskEditCommand.EditTaskDescriptor(task);
    }

    /**
     * Sets the {@code description} to {@code desc}
     */
    public EditTaskDescriptorBuilder withDescription(String desc) {
        descriptor.setDescription(new Description(desc));
        return this;
    }

    /**
     * Sets the {@code taskDateTime} to {@code tdt}
     */
    public EditTaskDescriptorBuilder withTaskDateTime(TaskDateTime tdt) {
        descriptor.setTaskDateTime(tdt);
        return this;
    }

    /**
     * Returns the {@code descriptor}
     */
    public TaskEditCommand.EditTaskDescriptor build() {
        return descriptor;

    }
}
