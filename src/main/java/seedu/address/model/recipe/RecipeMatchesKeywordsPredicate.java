package seedu.address.model.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Ingredient;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Tests that a {@code Recipe}'s {@code Time}, {@code Ingredient}, or {@code Goal} matches any of the arguments given.
 */
public class RecipeMatchesKeywordsPredicate implements Predicate<Recipe> {
    private final List<Time> time;
    private final Set<Goal> goals;
    private final boolean favourites;
    private final Set<Grain> grains;
    private final Set<Vegetable> vegetables;
    private final Set<Protein> proteins;
    private final Set<Fruit> fruits;
    private final Set<Other> others;

    public RecipeMatchesKeywordsPredicate(List<Time> time, Set<Goal> goals, boolean favourites, Set<Grain> grains,
                                          Set<Vegetable> vegetables, Set<Protein> proteins, Set<Fruit> fruits,
                                          Set<Other> others) {
        this.time = time;
        this.goals = goals;
        this.favourites = favourites;
        this.grains = grains;
        this.vegetables = vegetables;
        this.proteins = proteins;
        this.fruits = fruits;
        this.others = others;
    }

    @Override
    public boolean test(Recipe recipe) {
        boolean toFilter = true;
        if (time.size() == 1) {
            toFilter = toFilter && time.get(0).isLessThan(recipe.getTime());
        } else if (time.size() == 2) {
            toFilter = toFilter && recipe.getTime().isWithinRange(time.get(0), time.get(1));
        }

        if (!goals.isEmpty()) {
            toFilter = toFilter && goals.stream().anyMatch(goal -> recipe.getGoals().contains(goal));
        }

        if (favourites) {
            toFilter = toFilter && recipe.isFavourite();
        }

        if (!grains.isEmpty()) {
            ArrayList<String> grainsInRecipe = new ArrayList<>();
            recipe.getGrains().forEach(grain -> grainsInRecipe.add(grain.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(grains).count() > 0) {
                toFilter = toFilter && getIncludedIngredientNameStream(grains).anyMatch(grainsInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(grains).count() > 0) {
                toFilter = toFilter
                        && getExcludedIngredientNameStream(grains)
                        .anyMatch(grain -> !grainsInRecipe.contains(grain));
            }
        }

        if (!vegetables.isEmpty()) {
            ArrayList<String> vegeInRecipe = new ArrayList<>();
            recipe.getVegetables().forEach(vege -> vegeInRecipe.add(vege.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(vegetables).count() > 0) {
                toFilter = toFilter && getIncludedIngredientNameStream(vegetables).anyMatch(vegeInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(vegetables).count() > 0) {
                toFilter = toFilter
                        && getExcludedIngredientNameStream(vegetables)
                        .anyMatch(grain -> !vegeInRecipe.contains(grain));
            }
        }

        if (!proteins.isEmpty()) {
            ArrayList<String> proteinsInRecipe = new ArrayList<>();
            recipe.getProteins().forEach(protein -> proteinsInRecipe.add(protein.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(proteins).count() > 0) {
                toFilter = toFilter && getIncludedIngredientNameStream(proteins).anyMatch(proteinsInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(proteins).count() > 0) {
                toFilter = toFilter
                        && getExcludedIngredientNameStream(proteins)
                        .anyMatch(grain -> !proteinsInRecipe.contains(grain));
            }
        }

        if (!fruits.isEmpty()) {
            ArrayList<String> fruitsInRecipe = new ArrayList<>();
            recipe.getFruits().forEach(fruit -> fruitsInRecipe.add(fruit.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(fruits).count() > 0) {
                toFilter = toFilter && getIncludedIngredientNameStream(fruits).anyMatch(fruitsInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(fruits).count() > 0) {
                toFilter = toFilter
                        && getExcludedIngredientNameStream(fruits)
                        .anyMatch(grain -> !fruitsInRecipe.contains(grain));
            }
        }

        if (!others.isEmpty()) {
            ArrayList<String> othersInRecipe = new ArrayList<>();
            recipe.getOthers().forEach(other -> othersInRecipe.add(other.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(others).count() > 0) {
                toFilter = toFilter && getIncludedIngredientNameStream(others).anyMatch(othersInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(others).count() > 0) {
                toFilter = toFilter
                        && getExcludedIngredientNameStream(others)
                        .anyMatch(grain -> !othersInRecipe.contains(grain));
            }
        }

        return toFilter;
    }

    /**
     * Gets the user included ingredient names in the ingredients set as a {@code Stream<String>}
     */
    private Stream<String> getIncludedIngredientNameStream(Set<? extends Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> ingredient.getIngredientName().toLowerCase())
                .filter(ingredient -> !ingredient.startsWith("exclude"));
    }

    /**
     * Gets the user excluded ingredient names in the ingredients set as a {@code Stream<String>}
     */
    private Stream<String> getExcludedIngredientNameStream(Set<? extends Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> ingredient.getIngredientName().toLowerCase())
                .filter(ingredient -> ingredient.startsWith("exclude"))
                .map(ingredient -> ingredient.substring(7).trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeMatchesKeywordsPredicate // instanceof handles nulls
                && time.equals(((RecipeMatchesKeywordsPredicate) other).time)
                && goals.equals(((RecipeMatchesKeywordsPredicate) other).goals)
                && favourites == ((RecipeMatchesKeywordsPredicate) other).favourites
                && grains.equals(((RecipeMatchesKeywordsPredicate) other).grains)
                && vegetables.equals(((RecipeMatchesKeywordsPredicate) other).vegetables)
                && proteins.equals(((RecipeMatchesKeywordsPredicate) other).proteins)
                && fruits.equals(((RecipeMatchesKeywordsPredicate) other).fruits)
                && others.equals(((RecipeMatchesKeywordsPredicate) other).others)); // state check
    }

}
