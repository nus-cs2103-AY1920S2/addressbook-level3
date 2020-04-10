package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.TRIP_EDIT;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.attributes.Title;

/**
 * Edits the title of an existing trip
 */
public class RenameCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the title of the current trip."
            + "Parameter: TITLE (must be between 1 to 50 characters)\n"
            + "Example: " + COMMAND_WORD + " Awesome trip";

    public static final String MESSAGE_EDIT_TITLE_SUCCESS = "The trip is now named: %s";

    private final Title newTitle;

    /**
     * @param title details to edit the current budget with
     */
    public RenameCommand(Title title) {
        requireNonNull(title);
        this.newTitle = title;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }


        model.setTitle(newTitle);

        return new CommandResult(String.format(MESSAGE_EDIT_TITLE_SUCCESS, newTitle), TRIP_EDIT);
    }


}
