package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACTIVITY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_DURATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TAG;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TITLE;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.trip.TripManager;

/**
 * Adds a Activity to the Activity manager.
 */
public class AddActivityCommand extends Command {

    public static final String COMMAND_WORD = "addactivity";

    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the scheduler";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the activity list"
            + "Parameters: "
            + PREFIX_ACTIVITY_TITLE + "TITLE "
            + PREFIX_ACTIVITY_DURATION + "DURATION "
            + PREFIX_ACTIVITY_LOCATION + "LOCATION "
            + PREFIX_ACTIVITY_TAG + "TAGS...\n "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACTIVITY_TITLE + "Osaka Castle View "
            + PREFIX_ACTIVITY_DURATION + "1 "
            + PREFIX_ACTIVITY_LOCATION + "Osaka "
            + PREFIX_ACTIVITY_TAG + "expensive "
            + PREFIX_ACTIVITY_TAG + "sightseeing";

    public static final String MESSAGE_SUCCESS = "New Activity added: %1$s";
    private final Activity toAdd;

    /**
     * Creates an AddActivityCommand to the specified {@code Activity}
     */
    public AddActivityCommand(Activity toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), SWITCH_TAB_ACTIVITY);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddActivityCommand // instanceof handles nulls
                && toAdd.equals(((AddActivityCommand) other).toAdd));
    }
}
