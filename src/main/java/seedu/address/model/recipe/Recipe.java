package seedu.address.model.recipe;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.ingredient.Ingredient;

/**
 * Represents a Recipe in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {

    // Identity fields
    private final Name name;
    private final Time time;

    // Data fields
    private final List<Step> steps = new ArrayList<>();
    private final Set<Goal> goals = new HashSet<>();
    private final Set<Ingredient> ingredients = new TreeSet<>();
    private boolean isFavourite;

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, Time time, Set<Ingredient> ingredients,
                  List<Step> steps, Set<Goal> goals, boolean isFavourite) {
        requireAllNonNull(name, time, steps, goals);
        this.name = name;
        this.time = time;
        this.steps.addAll(steps);
        this.goals.addAll(goals);
        this.ingredients.addAll(ingredients);
        this.isFavourite = isFavourite;
    }

    public boolean getFavouriteStatus() {
        return isFavourite;
    }

    public void markAsFavourite() {
        isFavourite = true;
    }

    public void unmarkAsFavourite() {
        isFavourite = false;
    }

    public Name getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public List<Step> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    /**
     * Returns an immutable goal set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Goal> getGoals() {
        return Collections.unmodifiableSet(goals);
    }

    /**
     * Returns an immutable ingredient set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Ingredient> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    /**
     * Returns true if both recipes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSameRecipe(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null
                && otherRecipe.getName().equals(getName())
                && (otherRecipe.getTime().equals(getTime()) || otherRecipe.getSteps().equals(getSteps()));
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
                && otherRecipe.getTime().equals(getTime())
                && otherRecipe.getIngredients().equals(getIngredients())
                && otherRecipe.getSteps().equals(getSteps())
                && otherRecipe.getGoals().equals(getGoals());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, steps, goals);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Ingredients: ");
        getIngredients().forEach(builder::append);
        builder.append(" Step: ")
                .append(getSteps())
                .append(" Goals: ");
        getGoals().forEach(builder::append);
        return builder.toString();
    }

}
