package seedu.address.logic.commands.taskcommand.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calender.CatContainsKeywordsPredicate;

/**
 * Find tasks in calendar by specific category.
 */
public class FindTasksByCatCommand extends FindTasksCommand {
    public static final String MESSAGE_SUCCESS = " Tasks found";

    private final CatContainsKeywordsPredicate predicate;

    public FindTasksByCatCommand(CatContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateDeadlineTaskList(predicate);

        return new CommandResult(model.getDeadlineTaskList().size() + MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTasksByCatCommand);
    }
}
