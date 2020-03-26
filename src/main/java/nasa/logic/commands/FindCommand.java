package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_ACTIVITY_LISTED_OVERVIEW;

import javafx.collections.ObservableList;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;
import nasa.model.module.Module;

/**
 * Finds and lists all activities in NASA whose name contains any of the argument keywords.
 * Represents the command for finding specific activities.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment lab tutorial";

    private final ActivityContainsKeyWordsPredicate predicate;

    public FindCommand(ActivityContainsKeyWordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        model.updateFilteredActivityList(predicate);
        return new CommandResult(String.format(MESSAGE_ACTIVITY_LISTED_OVERVIEW,
            getNumberOfFilteredActivities(model.getFilteredModuleList())));
    }

    private int getNumberOfFilteredActivities(ObservableList<Module> moduleList) {
        int numberOfActivities = 0;
        for (Module module : moduleList) {
            numberOfActivities += module.getFilteredActivityList().size();
        }
        return numberOfActivities;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
