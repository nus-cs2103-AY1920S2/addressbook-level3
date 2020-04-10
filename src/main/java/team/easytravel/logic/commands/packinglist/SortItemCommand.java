package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_EMPTY_LIST_FORMAT;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_CRITERIA;

import java.util.Comparator;
import java.util.List;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.trip.TripManager;

/**
 * The type Sort item command.
 */
public class SortItemCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "sortitem";

    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts the items identified by the command"
            + " asc or des in the displayed packing list\n"
            + "asc sorts by ascending order while des sorts by descending order\n"
            + "Parameters : SORTIDENTIFIER (must be asc or des) "
            + "[" + PREFIX_ITEM_CRITERIA + "]" + "[ALPHABET]" + "[QUANTITY]" + "[CATEGORY]\n"
            + "Example: " + COMMAND_WORD + " asc criteria/category";

    /**
     * The constant MESSAGE_SORT_ITEM_SUCCESS.
     */
    public static final String MESSAGE_SORT_ITEM_SUCCESS = "Sorting of Item successful :)";

    /**
     * The constant SORT_DESCENDING.
     */
    public static final String SORT_DESCENDING = "des";

    /**
     * The constant SORT_ASCENDING.
     */
    public static final String SORT_ASCENDING = "asc";
    /**
     * The constant CATEGORY.
     */
    public static final String CATEGORY = "category";
    /**
     * The constant QUANTITY.
     */
    public static final String QUANTITY = "quantity";
    /**
     * The constant ITEM.
     */
    public static final String ALPHABET = "alphabet";

    private final String sortIdentifier;
    private final String sortParameter;

    /**
     * Instantiates a new Sort item command.
     *
     * @param sortIdentifier the sort identifier
     * @param sortParameter  the sort parameter
     */
    public SortItemCommand(String sortIdentifier, String sortParameter) {
        this.sortIdentifier = sortIdentifier;
        this.sortParameter = sortParameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<PackingListItem> lastShownList = model.getFilteredPackingList();

        if (lastShownList.size() < 1) {
            throw new CommandException(String.format(MESSAGE_EMPTY_LIST_FORMAT, "packing"));
        }

        switch (sortParameter) {
        case "alphabet":
            if (sortIdentifier.equals("des")) {
                model.sortPackingList((x, y) -> y.getItemName().toString().compareTo(
                        x.getItemName().toString()));
                return new CommandResult(MESSAGE_SORT_ITEM_SUCCESS, SWITCH_TAB_PACKING_LIST);
            } else {
                model.sortPackingList(Comparator.comparing(x -> x.getItemName().toString()));
                return new CommandResult(MESSAGE_SORT_ITEM_SUCCESS, SWITCH_TAB_PACKING_LIST);
            }

        case "category":
            if (sortIdentifier.equals("des")) {
                model.sortPackingList((x, y) -> y.getItemCategory().toString().compareTo(
                        x.getItemCategory().toString()
                ));
                return new CommandResult(MESSAGE_SORT_ITEM_SUCCESS, SWITCH_TAB_PACKING_LIST);

            } else {
                model.sortPackingList(Comparator.comparing(x -> x.getItemCategory().toString()));
                return new CommandResult(MESSAGE_SORT_ITEM_SUCCESS, SWITCH_TAB_PACKING_LIST);
            }

        case "quantity":
            if (sortIdentifier.equals("des")) {
                model.sortPackingList((x, y) -> (int) Math.signum(y.getQuantity().value - x.getQuantity().value));
                return new CommandResult(MESSAGE_SORT_ITEM_SUCCESS, SWITCH_TAB_PACKING_LIST);

            } else {
                model.sortPackingList((Comparator.comparing(x -> x.getQuantity().value)));
                return new CommandResult(MESSAGE_SORT_ITEM_SUCCESS, SWITCH_TAB_PACKING_LIST);
            }

        default:
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortItemCommand.MESSAGE_USAGE));
        }

    }
}
