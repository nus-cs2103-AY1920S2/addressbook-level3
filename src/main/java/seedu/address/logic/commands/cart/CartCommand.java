package seedu.address.logic.commands.cart;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.Cart;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ingredient.CompatibleIngredientList;

/**
 * Represents an Cart command with hidden internal logic and the ability to be executed.
 */
public abstract class CartCommand extends Command {

    public static final String COMMAND_CATEGORY = "cart";

    /**
     * Stores the details to edit the cart with.
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
         * Creates and returns a {@code Cart} with the details of {@code CartToEdit}
         * edited with {@code editCartDescriptor}.
         */
        public static Cart createEditedCart(Cart cartToEdit, EditIngredientDescriptor editCartDescriptor) {
            assert cartToEdit != null;

            CompatibleIngredientList updatedIngredients = editCartDescriptor.getIngredients()
                    .orElse(cartToEdit.getCompatibleIngredientList());

            return new Cart((ReadOnlyCart) updatedIngredients);
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
            if (!(other instanceof CartCommand.EditIngredientDescriptor)) {
                return false;
            }

            // state check
            EditIngredientDescriptor e = (EditIngredientDescriptor) other;

            return getIngredients().equals(e.getIngredients());
        }
    }

}
