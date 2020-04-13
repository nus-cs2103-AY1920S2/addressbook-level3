package seedu.jelphabot.testutil;

import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.jelphabot.logic.commands.AddCommand;
import seedu.jelphabot.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(Task task) {
        return AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     * Does not include status as status cannot be set from a command string.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION).append(task.getDescription().fullDescription).append(" ");
        sb.append(PREFIX_DATETIME).append(task.getDateTime().toString()).append(" ");
        sb.append(PREFIX_MODULE_CODE).append(task.getModuleCode().value).append(" ");
        sb.append(PREFIX_PRIORITY).append(task.getPriority().toString()).append(" ");
        task.getTags().forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription()
            .ifPresent(name -> sb.append(PREFIX_DESCRIPTION).append(name.toString()).append(" "));
        descriptor.getDateTime()
            .ifPresent(dateTime -> sb.append(PREFIX_DATETIME).append(dateTime.toString()).append(" "));
        descriptor.getModuleCode()
            .ifPresent(moduleCode -> sb.append(PREFIX_MODULE_CODE).append(moduleCode.toString()).append(" "));
        descriptor.getPriority()
            .ifPresent(priority -> sb.append(PREFIX_PRIORITY).append(priority.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
