package seedu.jelphabot.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.jelphabot.logic.commands.EditCommand;
import seedu.jelphabot.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Task;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditCommand.EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setDescription(task.getDescription());
        descriptor.setDateTime(task.getDateTime());
        descriptor.setModuleCode(task.getModuleCode());
        descriptor.setPriority(task.getPriority());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String name) {
        descriptor.setDescription(new Description(name));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withModuleCode(String moduleCode) {
        descriptor.setModuleCode(new ModuleCode(moduleCode));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(Priority.toPriority(priority));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
