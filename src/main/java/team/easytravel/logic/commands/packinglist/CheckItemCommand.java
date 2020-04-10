package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS;

import java.util.ArrayList;
import java.util.List;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.trip.TripManager;

/**
 * Marks a packing list item as checked.
 */
public class CheckItemCommand extends Command {
    public static final String COMMAND_WORD = "checkitem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the item identified by the index number used in the displayed packing list as packed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PACKED_ITEM_SUCCESS = "Packed Item(s): %1$s";

    private final List<Index> indexes;

    /**
     * Instantiates a new Check item command.
     *
     * @param indexes of the Item in the filtered Item list to check
     */
    public CheckItemCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexes = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<PackingListItem> lastShownList = model.getFilteredPackingList();
        List<PackingListItem> editedItems = new ArrayList<>();
        List<Index> invalidIndexes = new ArrayList<>();
        StringBuilder sb = new StringBuilder().append("Checked items \n");
        StringBuilder invalidIndex = new StringBuilder().append("Invalid Indexes are: ");

        for (Index i : indexes) {
            if (i.getZeroBased() >= lastShownList.size()) {
                invalidIndexes.add(i);
                invalidIndex.append(i.toString()).append(" ");
                continue;
            }
            PackingListItem itemToCheck = lastShownList.get(i.getZeroBased());
            PackingListItem checkedItem = new PackingListItem(itemToCheck.getItemName(), itemToCheck.getQuantity(),
                    itemToCheck.getItemCategory(), true);

            sb.append(checkedItem.toString()).append("\n");

            model.setPackingListItem(itemToCheck, checkedItem);
            editedItems.add(checkedItem);
        }

        model.updateFilteredPackingList(PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS);

        if (editedItems.isEmpty() && !invalidIndexes.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT,
                    "packing list item"));
        } else if (invalidIndexes.isEmpty() && !editedItems.isEmpty()) {
            // Event where there are no invalid indexes and items were edited
            return new CommandResult(String.format(MESSAGE_PACKED_ITEM_SUCCESS, sb), SWITCH_TAB_PACKING_LIST);
        }
        return new CommandResult(String.format(MESSAGE_PACKED_ITEM_SUCCESS, sb.append("\n").append(invalidIndex)),
                SWITCH_TAB_PACKING_LIST);

    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckItemCommand // instanceof handles nulls
                && indexes.equals(((CheckItemCommand) other).indexes)); // state check
    }
}
