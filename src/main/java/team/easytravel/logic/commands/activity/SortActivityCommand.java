package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_EMPTY_LIST_FORMAT;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACTIVITY;

import java.util.Comparator;
import java.util.List;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.trip.TripManager;

/**
 * Sorts your activity List according to ascending or descending amount.
 */
public class SortActivityCommand extends Command {

    public static final String COMMAND_WORD = "sortactivity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts the activity identified by the command"
            + " asc or des in the displayed activity\n"
            + "asc sorts by ascending order while des sorts by descending order\n"
            + "Parameters : SORTIDENTIFIER (must be asc or des) "
            + "[title] " + "[location] [duration] \n"
            + "Example: " + COMMAND_WORD + " asc title";

    public static final String MESSAGE_SORT_ACTIVITY_SUCCESS = "Sorting of Activity successful :)";

    public static final String SORT_DESCENDING = "des";
    public static final String SORT_ASCENDING = "asc";
    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String DURATION = "duration";

    private final String sortIdentifier;
    private final String sortParameter;

    public SortActivityCommand(String sortIdentifier, String sortParameter) {
        this.sortIdentifier = sortIdentifier;
        this.sortParameter = sortParameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<Activity> lastShownList = model.getFilteredActivityList();

        if (lastShownList.size() < 1) {
            throw new CommandException(String.format(MESSAGE_EMPTY_LIST_FORMAT, "activity"));
        }

        switch (sortParameter) {
        case "location":
            if (sortIdentifier.equals("des")) {
                model.sortActivityList((x, y) -> y.getLocation().toString().compareTo(
                        x.getLocation().toString()
                ));
                return new CommandResult(MESSAGE_SORT_ACTIVITY_SUCCESS, SWITCH_TAB_ACTIVITY);

            } else {
                model.sortActivityList(Comparator.comparing(x -> x.getLocation().toString()));
                return new CommandResult(MESSAGE_SORT_ACTIVITY_SUCCESS, SWITCH_TAB_ACTIVITY);
            }
        case "title":
            if (sortIdentifier.equals("des")) {
                model.sortActivityList((x, y) -> y.getTitle().toString().compareTo(
                        x.getTitle().toString()));
                return new CommandResult(MESSAGE_SORT_ACTIVITY_SUCCESS, SWITCH_TAB_ACTIVITY);

            } else {
                model.sortActivityList(Comparator.comparing(x -> x.getTitle().toString()));
                return new CommandResult(MESSAGE_SORT_ACTIVITY_SUCCESS, SWITCH_TAB_ACTIVITY);
            }

        case "duration":
            if (sortIdentifier.equals("des")) {
                model.sortActivityList((x, y) -> (int) Math.signum(y.getDuration().value)
                        - x.getDuration().value);
                return new CommandResult(MESSAGE_SORT_ACTIVITY_SUCCESS, SWITCH_TAB_ACTIVITY);

            } else {
                model.sortActivityList((Comparator.comparing(x -> x.getDuration().value)));
                return new CommandResult(MESSAGE_SORT_ACTIVITY_SUCCESS, SWITCH_TAB_ACTIVITY);
            }

        default:
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortActivityCommand.MESSAGE_USAGE));
        }

    }
}
