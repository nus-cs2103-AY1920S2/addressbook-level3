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
import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Represents a Recipe in the recipe book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {

    // Identity fields
    private final Name name;
    private final Time time;

    // Data fields
    private final List<Step> steps = new ArrayList<>();
    private final Set<Goal> goals = new HashSet<>();
    private final Set<Grain> grains = new TreeSet<>();
    private final Set<Vegetable> vegetables = new TreeSet<>();
    private final Set<Protein> proteins = new TreeSet<>();
    private final Set<Fruit> fruits = new TreeSet<>();
    private final Set<Other> others = new TreeSet<>();
    private boolean isFavourite;

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, Time time,
                  Set<Grain> grains, Set<Vegetable> vegetables,
                  Set<Protein> proteins, Set<Fruit> fruits, Set<Other> others,
                  List<Step> steps, Set<Goal> goals, boolean isFavourite) {
        requireAllNonNull(name, time, steps, goals);
        this.name = name;
        this.time = time;
        this.steps.addAll(steps);
        this.goals.addAll(goals);
        this.grains.addAll(grains);
        this.vegetables.addAll(vegetables);
        this.proteins.addAll(proteins);
        this.fruits.addAll(fruits);
        this.others.addAll(others);
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
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
     * Returns an immutable ingredient set for the respective ingredient type,
     * which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Grain> getGrains() {
        return Collections.unmodifiableSet(grains);
    }

    /**
     * Returns an immutable ingredient set for the respective ingredient type,
     * which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Vegetable> getVegetables() {
        return Collections.unmodifiableSet(vegetables);
    }

    /**
     * Returns an immutable ingredient set for the respective ingredient type,
     * which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Protein> getProteins() {
        return Collections.unmodifiableSet(proteins);
    }

    /**
     * Returns an immutable ingredient set for the respective ingredient type,
     * which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Fruit> getFruits() {
        return Collections.unmodifiableSet(fruits);
    }

    /**
     * Returns an immutable ingredient set for the respective ingredient type,
     * which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Other> getOthers() {
        return Collections.unmodifiableSet(others);
    }

    /**
     * Returns an immutable goal set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Goal> getGoals() {
        return Collections.unmodifiableSet(goals);
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
        /* debug purposes
        System.out.print("my: " + grains + ", other: " + otherRecipe.getGrains()
                + "my: " + vegetables + ", other: " + otherRecipe.getVegetables()
                + "my: " + proteins + ", other: " + otherRecipe.getProteins()
                + "my: " + others + ", other: " + otherRecipe.getOthers());*/
        return otherRecipe.getName().equals(getName())
                && otherRecipe.getTime().equals(getTime())
                && otherRecipe.getGrains().equals(getGrains())
                && otherRecipe.getVegetables().equals(getVegetables())
                && otherRecipe.getProteins().equals(getProteins())
                && otherRecipe.getFruits().equals(getFruits())
                && otherRecipe.getOthers().equals(getOthers())
                && otherRecipe.getSteps().equals(getSteps())
                && otherRecipe.getGoals().equals(getGoals());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, grains, vegetables, proteins, fruits, others, steps, goals);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Ingredients: ");
        getGrains().forEach(builder::append);
        getVegetables().forEach(builder::append);
        getProteins().forEach(builder::append);
        getFruits().forEach(builder::append);
        getOthers().forEach(builder::append);
        builder.append(" Step: ")
                .append(getSteps())
                .append(" Goals: ");
        getGoals().forEach(builder::append);
        return builder.toString();
    }

}
