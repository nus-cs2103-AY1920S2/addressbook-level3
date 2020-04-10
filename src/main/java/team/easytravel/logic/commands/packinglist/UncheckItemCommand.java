package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.trip.TripManager;

/**
 * The type Uncheck item command.
 */
public class UncheckItemCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "uncheckitem";

    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the item identified by the index number used in the displayed packing list as unpacked.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    /**
     * The constant MESSAGE_DELETE_ITEM_SUCCESS.
     */
    public static final String MESSAGE_PACKED_ITEM_SUCCESS = "Unpacked Item(s): %1$s";

    /**
     * The constant MESSAGE_DUPLICATE_ITEM.
     */
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the packing list.";

    private final List<Index> indexes;
    private final UncheckItemDescriptor uncheckItemDescriptor;

    /**
     * Instantiates a new Edit item command.
     *
     * @param indexes               of the Item in the filtered Item list to edit
     * @param uncheckItemDescriptor details to edit the Item with
     */
    public UncheckItemCommand(List<Index> indexes, UncheckItemDescriptor uncheckItemDescriptor) {
        requireNonNull(indexes);
        requireNonNull(uncheckItemDescriptor);

        this.indexes = indexes;
        this.uncheckItemDescriptor = new UncheckItemDescriptor(uncheckItemDescriptor);
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
        StringBuilder sb = new StringBuilder().append("Unchecked items \n");
        StringBuilder invalidIndex = new StringBuilder().append("Invalid Indexes are: ");

        for (Index index : indexes) {

            if (index.getZeroBased() >= lastShownList.size()) {
                invalidIndexes.add(index);
                invalidIndex.append(index.toString()).append(" ");
                continue;
            }

            PackingListItem itemToUncheck = lastShownList.get(index.getZeroBased());
            PackingListItem uncheckItem = new PackingListItem(itemToUncheck.getItemName(), itemToUncheck.getQuantity(),
                    itemToUncheck.getItemCategory(), false);

            if (!itemToUncheck.isSame(uncheckItem) && model.hasPackingListItem(uncheckItem)) {
                throw new CommandException(MESSAGE_DUPLICATE_ITEM);
            }

            sb.append(uncheckItem.toString()).append("\n");

            model.setPackingListItem(itemToUncheck, uncheckItem);
            editedItems.add(uncheckItem);
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
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UncheckItemCommand)) {
            return false;
        }

        // state check
        UncheckItemCommand e = (UncheckItemCommand) other;
        return indexes.equals(e.indexes)
                && uncheckItemDescriptor.equals(e.uncheckItemDescriptor);
    }

    /**
     * The type Check item descriptor.
     */
    public static class UncheckItemDescriptor {
        private boolean isPacked = false;

        /**
         * Instantiates a new Uncheck item descriptor.
         */
        public UncheckItemDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         *
         * @param toCopy the to copy
         */
        public UncheckItemDescriptor(UncheckItemDescriptor toCopy) {
            setCheck(toCopy.isPacked);
        }

        /**
         * Sets check.
         *
         * @param isPacked the is packed
         */
        public void setCheck(boolean isPacked) {
            this.isPacked = isPacked;
        }


        /**
         * Gets unpacked.
         *
         * @return the unpacked
         */
        public Optional<Boolean> getUnpacked() {
            return Optional.of(isPacked);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UncheckItemDescriptor)) {
                return false;
            }

            // state check
            UncheckItemDescriptor e = (UncheckItemDescriptor) other;
            return getUnpacked().equals(e.getUnpacked());
        }
    }
}
