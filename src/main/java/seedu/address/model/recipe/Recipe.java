package seedu.address.model.recipe;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Recipe in the recipe book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {
    // Identity fields
    private final Name name;

    // Data fields
    private final Ingredients ingredients;
    private final Instructions instructions;


    public Recipe(Name name, Ingredients ingredients, Instructions instructions) {
        requireAllNonNull(name, ingredients, instructions);
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Name getName() {
        return name;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public Instructions getInstructions() {
        return instructions;
    }

    /**
     * Returns true if both recipes have the same name.
     */
    public boolean isSameRecipe(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null
               && otherRecipe.getName().equals(getName());
    }

    /**
     * Returns true if both recipes have the same identity and data fields.
     * This defines a stronger notion of equality between two recipes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recipe)) {
            return false;
        }

        Recipe otherRecipe = (Recipe) other;
        return otherRecipe.getName().equals(getName())
               && otherRecipe.getIngredients().equals(getIngredients())
               && otherRecipe.getInstructions().equals(getInstructions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ingredients, instructions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Ingredients: ")
                .append(getIngredients())
                .append(" Instructions: ")
                .append(getInstructions());
        return builder.toString();
    }
}
