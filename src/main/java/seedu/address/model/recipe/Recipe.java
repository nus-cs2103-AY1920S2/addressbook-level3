package seedu.address.model.recipe;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.recipe.attribute.Tag;

/**
 * Represents a Recipe in the recipe book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {

    // Identity fields
    private final Name name;
    private final IngredientList ingredients;
    private final InstructionList instructions;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();


    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, IngredientList ingredients, InstructionList instructions, Set<Tag> tags) {
        requireAllNonNull(name, ingredients, instructions);
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public IngredientList getIngredients() {
        return ingredients;
    }

    public InstructionList getInstructions() {
        return instructions;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both recipes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSameRecipe(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null && otherRecipe.getName().equals(getName()) && (otherRecipe.getIngredients().equals(
                getIngredients()) || otherRecipe.getInstructions().equals(getInstructions()));
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
        return otherRecipe.getName().equals(getName()) && otherRecipe.getIngredients().equals(getIngredients())
               && otherRecipe.getInstructions().equals(getInstructions()) && otherRecipe.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ingredients, instructions, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" Ingredients: ").append(getIngredients()).append(
                " Instructions: ").append(getInstructions()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
