package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.task.TaskEditCommand;
import seedu.address.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {
    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getTaskAddCommand(Task task) {
        return Command.COMMAND_WORD_ADD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE).append(" ").append(task.getModuleCode().value).append(" ");
        sb.append(PREFIX_DESCRIPTION).append(" ").append(task.getDescription().value).append(" ");
        String[] taskDateTimeStringElements = task.getTaskDateTime().get().toString().split(" ");
        sb.append(PREFIX_ON).append(" ").append(taskDateTimeStringElements[0]).append(" ");
        sb.append(PREFIX_AT).append(" ").append(taskDateTimeStringElements[1]).append(" ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(TaskEditCommand.EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE).append(" ").append(descriptor.getModuleCode().value).append(" ");
        sb.append(PREFIX_DESCRIPTION).append(" ").append(descriptor.getDescription().get().toString()).append(" ");
        String[] taskDateTimeStringElements = descriptor.getTaskDateTime().get().toString().split(" ");
        sb.append(PREFIX_ON).append(" ").append(taskDateTimeStringElements[0]).append(" ");
        sb.append(PREFIX_AT).append(" ").append(taskDateTimeStringElements[1]).append(" ");
        return sb.toString();
    }
}
