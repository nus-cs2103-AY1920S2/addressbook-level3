package cookbuddy.model.recipe;

import static cookbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import cookbuddy.model.recipe.attribute.Calorie;
import cookbuddy.model.recipe.attribute.Difficulty;
import cookbuddy.model.recipe.attribute.Done;
import cookbuddy.model.recipe.attribute.Fav;
import cookbuddy.model.recipe.attribute.IngredientList;
import cookbuddy.model.recipe.attribute.InstructionList;
import cookbuddy.model.recipe.attribute.Name;
import cookbuddy.model.recipe.attribute.Rating;
import cookbuddy.model.recipe.attribute.Serving;
import cookbuddy.model.recipe.attribute.Tag;

/**
 * Represents a Recipe in the recipe book. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
public class Recipe {

    // Identity fields
    private final Name name;
    private final IngredientList ingredients;
    private final InstructionList instructions;
    private final Calorie calorie;
    private final Serving serving;
    private final Rating rating;
    private final Difficulty difficulty;
    private final Fav favStatus = new Fav(false);
    private final Done doneStatus = new Done(false);
    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, IngredientList ingredients, InstructionList instructions, Calorie calorie,
                Serving serving, Rating rating, Difficulty difficulty, Set<Tag> tags) {
        requireAllNonNull(name, ingredients, instructions);
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.calorie = calorie;
        this.serving = serving;
        this.rating = rating;
        this.difficulty = difficulty;
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

    public Calorie getCalorie() {
        return calorie;
    }

    public Rating getRating() {
        return rating;
    }

    public Serving getServing() {
        return serving;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Fav getFavStatus() {
        return favStatus;
    }

    public Done getDoneStatus() {
        return doneStatus;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both recipes of the same name have the same ingredients and instructions. This defines a weaker
     * notion of equality between two recipes.
     */
    public boolean isSameRecipe(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null && otherRecipe.getName().equals(getName())
            && otherRecipe.getIngredients().equals(getIngredients())
            && otherRecipe.getInstructions().equals(getInstructions());
    }

    public void favRecipe() {
        favStatus.fav();
    }

    public void unFavRecipe() {
        favStatus.unFav();
    }

    public void attemptRecipe() {
        doneStatus.attempt();
    }

    public void unAttemptRecipe() {
        doneStatus.unAttempt();
    }

    /**
     * Returns true if both recipes have the same identity and data fields. This
     * defines a stronger notion of equality between two recipes.
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
            && otherRecipe.getInstructions().equals(getInstructions()) && otherRecipe.getCalorie()
            .equals(getCalorie()) && otherRecipe.getRating().equals(getRating())
                && otherRecipe.getDifficulty().equals(getDifficulty())
                && otherRecipe.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ingredients, instructions, calorie, serving, rating, difficulty, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" Ingredients: ").append(getIngredients()).append(
            " Instructions: ").append(getInstructions()).append(" Calories: ").append(getCalorie()).append(
                    " Serving size: ").append(getServing()).append(" Rating: ").append(getRating()).append(
                " Difficulty ").append(getDifficulty()).append(" Tags" + ": ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
