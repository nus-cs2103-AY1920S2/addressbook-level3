package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS;

import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;
import team.easytravel.model.trip.TripManager;

/**
 * The type Edit item command.
 */
public class EditItemCommand extends Command {

    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "edititem";

    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item identified "
            + "by the index number used in the displayed packing list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ITEM_NAME + "NAME] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_ITEM_CATEGORY + "CATEGORY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "4 "
            + PREFIX_ITEM_CATEGORY + "essentials";

    /**
     * The constant MESSAGE_EDIT_ITEM_SUCCESS.
     */
    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Item: %1$s";
    /**
     * The constant MESSAGE_NOT_EDITED.
     */
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    /**
     * The constant MESSAGE_DUPLICATE_ITEM.
     */
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the packing list.";

    private final Index index;
    private final EditItemDescriptor editItemDescriptor;

    /**
     * Instantiates a new Edit item command.
     *
     * @param index              of the Item in the filtered Item list to edit
     * @param editItemDescriptor details to edit the Item with
     */
    public EditItemCommand(Index index, EditItemDescriptor editItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editItemDescriptor);

        this.index = index;
        this.editItemDescriptor = new EditItemDescriptor(editItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<PackingListItem> lastShownList = model.getFilteredPackingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT,
                    "packing list item"));
        }

        PackingListItem itemToEdit = lastShownList.get(index.getZeroBased());
        PackingListItem editedItem = createEditedItem(itemToEdit, editItemDescriptor);

        if (!itemToEdit.isSame(editedItem) && model.hasPackingListItem(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.setPackingListItem(itemToEdit, editedItem);
        model.updateFilteredPackingList(PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS);
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem), SWITCH_TAB_PACKING_LIST);
    }

    /**
     * Creates a new Packing List item with the new attributes.
     *
     * @param itemToEdit         of the Item in the filtered Item list to edit
     * @param editItemDescriptor details to edit the Item with
     */
    private static PackingListItem createEditedItem(PackingListItem itemToEdit,
                                                    EditItemDescriptor editItemDescriptor) {
        assert itemToEdit != null;

        ItemName updatedName = editItemDescriptor.getItemName().orElse(itemToEdit.getItemName());
        Quantity updatedQuantity = editItemDescriptor.getQuantity().orElse(itemToEdit.getQuantity());
        ItemCategory updatedCategory = editItemDescriptor.getItemCategory().orElse(itemToEdit.getItemCategory());
        boolean isCheck = itemToEdit.getIsChecked();

        return new PackingListItem(updatedName, updatedQuantity, updatedCategory, isCheck);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditItemCommand)) {
            return false;
        }

        // state check
        EditItemCommand e = (EditItemCommand) other;
        return index.equals(e.index)
                && editItemDescriptor.equals(e.editItemDescriptor);
    }

    /**
     * The type Edit item descriptor.
     */
    public static class EditItemDescriptor {
        private ItemName itemName;
        private Quantity quantity;
        private ItemCategory itemCategory;

        /**
         * Instantiates a new Edit item descriptor.
         */
        public EditItemDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         *
         * @param toCopy the to copy
         */
        public EditItemDescriptor(EditItemDescriptor toCopy) {
            setItemName(toCopy.itemName);
            setQuantity(toCopy.quantity);
            setItemCategory(toCopy.itemCategory);
        }

        /**
         * Returns true if at least one field is edited.
         *
         * @return the boolean
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(itemName, quantity, itemCategory);
        }

        /**
         * Sets item name.
         *
         * @param itemName the item name
         */
        public void setItemName(ItemName itemName) {
            this.itemName = itemName;
        }

        /**
         * Gets item name.
         *
         * @return the item name
         */
        public Optional<ItemName> getItemName() {
            return Optional.ofNullable(itemName);
        }

        /**
         * Sets quantity.
         *
         * @param quantity the quantity
         */
        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        /**
         * Gets quantity.
         *
         * @return the quantity
         */
        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        /**
         * Sets item category.
         *
         * @param itemCategory the item category
         */
        public void setItemCategory(ItemCategory itemCategory) {
            this.itemCategory = itemCategory;
        }

        /**
         * Gets item category.
         *
         * @return the item category
         */
        public Optional<ItemCategory> getItemCategory() {
            return Optional.ofNullable(itemCategory);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditItemDescriptor)) {
                return false;
            }

            // state check
            EditItemDescriptor e = (EditItemDescriptor) other;

            return getItemName().equals(e.getItemName())
                    && getQuantity().equals(e.getQuantity())
                    && getItemCategory().equals(e.getItemCategory());
        }
    }
}
