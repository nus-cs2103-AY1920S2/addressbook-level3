//@@author eedenong
package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskIsIncompletePredicate;

/**
 * Displays to the user a list of tasks that are currently of the INCOMPLETE status.
 */
public class ShowIncompleteCommand extends Command {

    public static final String COMMAND_WORD = "show-incomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all incomplete tasks in the task list";

    public static final String MESSAGE_SUCCESS = "Displaying all incomplete tasks";

    public static final Predicate<Task> INCOMPLETE_PREDICATE = new TaskIsIncompletePredicate();

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(INCOMPLETE_PREDICATE);
        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);
        if (model.getFilteredTaskList().size() == 0) {
            sb.append("\nYou do not have any tasks that are incomplete!");
        }
        return new CommandResult(sb.toString()).isShowDateTaskList();
    }
}
