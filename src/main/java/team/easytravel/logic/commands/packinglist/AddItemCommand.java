package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_QUANTITY;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.trip.TripManager;

/**
 * The type Add item command.
 */
public class AddItemCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "additem";

    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the packing list. "
            + "Parameters: "
            + PREFIX_ITEM_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_ITEM_CATEGORY + "CATEGORY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ITEM_NAME + "T Shirt "
            + PREFIX_QUANTITY + 7 + " "
            + PREFIX_ITEM_CATEGORY + "clothes";

    /**
     * The constant MESSAGE_SUCCESS.
     */
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    /**
     * The constant MESSAGE_DUPLICATE_PERSON.
     */
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the packing list";

    private final PackingListItem toAdd;

    /**
     * Creates an AddItemCommand to add the specified {@code Person}
     *
     * @param item the item
     */
    public AddItemCommand(PackingListItem item) {
        requireNonNull(item);
        toAdd = item;
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

        if (model.hasPackingListItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.addPackingListItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), SWITCH_TAB_PACKING_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddItemCommand // instanceof handles nulls
                && toAdd.equals(((AddItemCommand) other).toAdd));
    }
}
