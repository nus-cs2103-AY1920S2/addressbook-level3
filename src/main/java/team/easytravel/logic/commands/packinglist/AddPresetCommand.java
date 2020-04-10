package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;

import java.util.Arrays;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.trip.TripManager;

/**
 * The type Add preset command.
 */
public class AddPresetCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "addpreset";

    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a preset to the packing list. "
            + "Parameters: "
            + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " swimming";

    /**
     * The constant MESSAGE_SUCCESS.
     */
    public static final String MESSAGE_SUCCESS = "New preset added: %1$s";

    private final PackingListItem[] toAdd;
    private final String presetCategory;


    /**
     * Instantiates a new Add preset command.
     *
     * @param items    the items
     * @param category the category
     */
    public AddPresetCommand(PackingListItem[] items, String category) {
        requireNonNull(items);
        toAdd = items;
        presetCategory = category;
    }

    /**
     * Execute command result.
     *
     * @param model the model
     * @return the command result
     * @throws CommandException the command exception
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        for (PackingListItem item : toAdd) {
            if (model.hasPackingListItem(item)) {
                continue;
            }
            model.addPackingListItem(item);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, presetCategory), SWITCH_TAB_PACKING_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPresetCommand // instanceof handles nulls
                && Arrays.equals(toAdd, ((AddPresetCommand) other).toAdd));
    }
}
