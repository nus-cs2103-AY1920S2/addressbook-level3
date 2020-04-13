package seedu.address.logic.commands.inventory;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.CompatibleIngredientList;

/**
 * Represents an inventory command with hidden internal logic and the ability to be executed.
 */
public abstract class InventoryCommand extends Command {

    public static final String COMMAND_CATEGORY = "inventory";

    /**
     * Stores the details to edit the inventory with.
     */
    public static class EditIngredientDescriptor {
        private CompatibleIngredientList ingredients;

        public EditIngredientDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditIngredientDescriptor(EditIngredientDescriptor toCopy) {
            setIngredients(toCopy.ingredients);
        }

        /**
         * Creates and returns a {@code Inventory} with the details of {@code inventoryToEdit}
         * edited with {@code editInventoryDescriptor}.
         */
        public static Inventory createEditedInventory(Inventory inventoryToEdit,
                                                      EditIngredientDescriptor editInventoryDescriptor) {
            assert inventoryToEdit != null;

            CompatibleIngredientList updatedIngredients = editInventoryDescriptor.getIngredients()
                    .orElse(inventoryToEdit.getCompatibleIngredientList());

            return new Inventory((ReadOnlyInventory) updatedIngredients);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(ingredients);
        }

        public void setIngredients(CompatibleIngredientList ingredients) {
            this.ingredients = ingredients;
        }

        public Optional<CompatibleIngredientList> getIngredients() {
            return Optional.ofNullable(ingredients);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof InventoryCommand.EditIngredientDescriptor)) {
                return false;
            }

            // state check
            EditIngredientDescriptor e = (EditIngredientDescriptor) other;

            return getIngredients().equals(e.getIngredients());
        }
    }

}
