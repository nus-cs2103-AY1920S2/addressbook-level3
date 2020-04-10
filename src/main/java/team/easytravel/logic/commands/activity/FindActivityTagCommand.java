package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACTIVITY;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.activity.ActivityTagContainsPredicate;
import team.easytravel.model.trip.TripManager;

/**
 * Finds all the activity tags
 */
public class FindActivityTagCommand extends Command {
    public static final String COMMAND_WORD = "findactivitytag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activity with "
            + "the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " hot";

    private final ActivityTagContainsPredicate predicate;

    public FindActivityTagCommand(ActivityTagContainsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.updateFilteredActivityList(predicate);
        return new CommandResult(
                String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredActivityList().size(), "activities"
                        + "found.\n Use command listactivity to show all activities"), SWITCH_TAB_ACTIVITY);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindActivityTagCommand // instanceof handles nulls
                && predicate.equals(((FindActivityTagCommand) other).predicate)); // state check
    }
}
