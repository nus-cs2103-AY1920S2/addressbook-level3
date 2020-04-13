package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.task.predicates.DescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all tasks in task list whose task description contains any of the
 * argument keywords. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " assignment";

    private final DescriptionContainsKeywordsPredicate predicate;

    public FindCommand(DescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                        && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
