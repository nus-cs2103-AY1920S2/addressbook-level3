package seedu.address.model.ingredient;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.ingredient.exceptions.IncompatibleIngredientException;

/**
 * Represents an ingredient.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient {

    private final IngredientName ingredientName;
    private final IngredientQuantity ingredientQuantity;

    /**
     * Every field must be present and not null.
     */
    public Ingredient(IngredientName ingredientName, IngredientQuantity ingredientQuantity) {
        requireAllNonNull(ingredientName, ingredientQuantity);
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
    }

    public IngredientName getName() {
        return ingredientName;
    }

    public IngredientQuantity getQuantity() {
        return ingredientQuantity;
    }

    /**
     * Adds {@code toAdd} to the ingredient.
     */
    public Ingredient add(Ingredient toAdd) {
        try {
            checkArgument(isCompatibleWith(toAdd));
            return new Ingredient(getName(), getQuantity().add(toAdd.getQuantity()));
        } catch (IllegalArgumentException e) {
            throw new IncompatibleIngredientException();
        }
    }

    /**
     * Subtracts {@code toSubtract} from the ingredient.
     */
    public Ingredient subtract(Ingredient toSubtract) {
        try {
            checkArgument(isCompatibleWith(toSubtract));
            return new Ingredient(getName(), getQuantity().subtract(toSubtract.getQuantity()));
        } catch (IllegalArgumentException e) {
            throw new IncompatibleIngredientException();
        }
    }

    /**
     * Returns the proportion of {@code toCompare} with respect to the ingredient.
     * @return a double value between 0 and 1 (inclusive)
     */
    public double asProportionOf(Ingredient toCompare) {
        try {
            checkArgument(isCompatibleWith(toCompare));
            return getQuantity().asProportionOf(toCompare.getQuantity());
        } catch (IllegalArgumentException e) {
            throw new IncompatibleIngredientException();
        }
    }

    /**
     * Returns true if both ingredients are compatible with each other.
     * This defines whether two ingredients can be added or subtracted from each other.
     */
    public boolean isCompatibleWith(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && otherIngredient.getName().equals(getName())
                && otherIngredient.getQuantity().hasSameUnitAs(getQuantity());
    }

    /**
     * Returns true if both ingredients have the same name.
     * This defines a weaker notion of equality between two ingredients.
     */
    public boolean isSameIngredient(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && otherIngredient.getName().equals(getName());
    }

    public String getNoWhitespaceName() {
        return this.getName().ingredientName.replace(" ", "");
    }

    /**
     * Returns true if both ingredients have the same name and quantity.
     * This defines a stronger notion of equality between two ingredients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Ingredient)) {
            return false;
        }

        Ingredient otherIngredient = (Ingredient) other;
        return otherIngredient.getName().equals(getName())
            && otherIngredient.getQuantity().equals(getQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ingredientName, ingredientQuantity);
    }

    @Override
    public String toString() {
        return String.format("%s %s", ingredientQuantity, ingredientName);
    }
}
