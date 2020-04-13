package seedu.recipe.model.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Ingredient;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Vegetable;

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
        return timeTest(recipe) && goalsTest(recipe) && favouritesTest(recipe) && grainsTest(recipe)
                && vegetablesTest(recipe) && proteinsTest(recipe) && fruitsTest(recipe) && othersTest(recipe);
    }

    /**
     * Gets the user included ingredient names in the ingredients set as a {@code Stream<String>}
     */
    private Stream<String> getIncludedIngredientNameStream(Set<? extends Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> ingredient.getIngredientName().toLowerCase())
                .filter(ingredient -> !ingredient.trim().split(" ")[0].equals("exclude"));
    }

    /**
     * Gets the user excluded ingredient names in the ingredients set as a {@code Stream<String>}
     */
    private Stream<String> getExcludedIngredientNameStream(Set<? extends Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> ingredient.getIngredientName().toLowerCase())
                .filter(ingredient -> ingredient.trim().split(" ")[0].equals("exclude"))
                .map(ingredient -> ingredient.substring(7).trim());
    }

    /**
     * If time predicate exists, tests if recipe's {@code Time} fulfills the time predicate and returns the outcome.
     * Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean timeTest(Recipe recipe) {
        if (time.size() == 1) {
            return time.get(0).isLessThan(recipe.getTime());
        } else if (time.size() == 2) {
            return recipe.getTime().isWithinRange(time.get(0), time.get(1));
        } else {
            return true;
        }
    }

    /**
     * If goals predicate exists, tests if recipe's {@code Goal} fulfills the goals predicate and returns the outcome.
     * Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean goalsTest(Recipe recipe) {
        if (!goals.isEmpty()) {
            return goals.stream().anyMatch(goal -> recipe.getGoals().contains(goal));
        } else {
            return true;
        }
    }

    /**
     * If favourites predicate exists, tests if recipe is a favourite and returns the outcome.
     * Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean favouritesTest(Recipe recipe) {
        if (favourites) {
            return recipe.isFavourite();
        } else {
            return true;
        }
    }

    /**
     * If grains predicate exists, tests if recipe's {@code Grain} ingredients fulfill the grains predicate
     * and returns the outcome. Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean grainsTest(Recipe recipe) {
        if (!grains.isEmpty()) {
            boolean hasMatchCriteria = true;
            ArrayList<String> grainsInRecipe = new ArrayList<>();
            recipe.getGrains().forEach(grain -> grainsInRecipe.add(grain.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(grains).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getIncludedIngredientNameStream(grains)
                        .anyMatch(grainsInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(grains).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getExcludedIngredientNameStream(grains)
                        .anyMatch(grain -> !grainsInRecipe.contains(grain));
            }
            return hasMatchCriteria;
        } else {
            return true;
        }
    }

    /**
     * If vegetables predicate exists, tests if recipe's {@code Vegetable} ingredients fulfill the vegetables predicate
     * and returns the outcome. Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean vegetablesTest(Recipe recipe) {
        if (!vegetables.isEmpty()) {
            boolean hasMatchCriteria = true;
            ArrayList<String> vegeInRecipe = new ArrayList<>();
            recipe.getVegetables().forEach(vege -> vegeInRecipe.add(vege.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(vegetables).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getIncludedIngredientNameStream(vegetables)
                        .anyMatch(vegeInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(vegetables).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getExcludedIngredientNameStream(vegetables)
                        .anyMatch(vegetable -> !vegeInRecipe.contains(vegetable));
            }
            return hasMatchCriteria;
        } else {
            return true;
        }
    }

    /**
     * If proteins predicate exists, tests if recipe's {@code Protein} ingredients fulfill the proteins predicate
     * and returns the outcome. Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean proteinsTest(Recipe recipe) {
        if (!proteins.isEmpty()) {
            boolean hasMatchCriteria = true;
            ArrayList<String> proteinsInRecipe = new ArrayList<>();
            recipe.getProteins().forEach(protein -> proteinsInRecipe.add(protein.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(proteins).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getIncludedIngredientNameStream(proteins)
                        .anyMatch(proteinsInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(proteins).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getExcludedIngredientNameStream(proteins)
                        .anyMatch(protein -> !proteinsInRecipe.contains(protein));
            }
            return hasMatchCriteria;
        } else {
            return true;
        }
    }

    /**
     * If fruits predicate exists, tests if recipe's {@code Fruit} ingredients fulfill the fruits predicate
     * and returns the outcome. Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean fruitsTest(Recipe recipe) {
        if (!fruits.isEmpty()) {
            boolean hasMatchCriteria = true;
            ArrayList<String> fruitsInRecipe = new ArrayList<>();
            recipe.getFruits().forEach(fruit -> fruitsInRecipe.add(fruit.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(fruits).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getIncludedIngredientNameStream(fruits)
                        .anyMatch(fruitsInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(fruits).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getExcludedIngredientNameStream(fruits)
                        .anyMatch(fruit -> !fruitsInRecipe.contains(fruit));
            }
            return hasMatchCriteria;
        } else {
            return true;
        }
    }

    /**
     * If others predicate exists, tests if recipe's {@code Other} ingredients fulfill the others predicate
     * and returns the outcome. Otherwise, returns true to allow the recipe to pass through by default.
     */
    private boolean othersTest(Recipe recipe) {
        if (!others.isEmpty()) {
            boolean hasMatchCriteria = true;
            ArrayList<String> othersInRecipe = new ArrayList<>();
            recipe.getOthers().forEach(other -> othersInRecipe.add(other.getIngredientName().toLowerCase()));

            if (getIncludedIngredientNameStream(others).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getIncludedIngredientNameStream(others)
                        .anyMatch(othersInRecipe::contains);
            }
            if (getExcludedIngredientNameStream(others).count() > 0) {
                hasMatchCriteria = hasMatchCriteria
                        && getExcludedIngredientNameStream(others)
                        .anyMatch(other -> !othersInRecipe.contains(other));
            }
            return hasMatchCriteria;
        } else {
            return true;
        }
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
