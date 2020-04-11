package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * Wraps all data at the planned-recipe-book level
 * Duplicates are not allowed (same recipe planned on the same day).
 */
public class PlannedBook implements ReadOnlyPlannedBook {

    private final UniquePlannedList uniquePlans;
    private final PlannedRecipeMap recipeMap;
    private String groceryList = "";

    public PlannedBook() {
        uniquePlans = new UniquePlannedList();
        recipeMap = new PlannedRecipeMap();
    }

    /**
     * Creates a PlannedBook using the plans in the {@code toBeCopied}
     */
    public PlannedBook(ReadOnlyPlannedBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /**
     * Replaces the contents of the planned recipe list with {@code plans}.
     * {@code plans} must not contain duplicate plans.
     */
    public void setUniquePlans(ObservableList<Plan> uniquePlans) {
        this.uniquePlans.setPlannedDates(uniquePlans);
    }

    /**
     * Replaces the contents of the recipe map with {@code plannedRecipeMap}.
     */
    public void setRecipeMap(PlannedRecipeMap recipeMap) {
        this.recipeMap.setPlannedRecipeMap(recipeMap);
    }

    /**
     * Resets the existing data of this {@code PlannedBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPlannedBook newData) {
        requireNonNull(newData);

        setUniquePlans(newData.getPlannedList());
        setRecipeMap(newData.getPlannedMap());
    }

    // ===== Plan and Recipe level methods =====

    /**
     * Adds {@code plan} for {@code recipe} into the unique plans list and recipe map.
     * Throws {@code DuplicatePlannedRecipeException} if a similar plan is already present.
     */
    public void addPlan(Recipe recipe, Plan plan) {
        uniquePlans.addPlan(plan);
        recipeMap.addPlan(recipe, plan);
    }

    /**
     * Deletes the {@code plan} for {@code recipe}.
     * Throws {@code PlannedRecipeNotFoundException} if the plan is not present.
     */
    public void deletePlan(Recipe recipe, Plan plan) {
        uniquePlans.deletePlan(plan);
        recipeMap.deletePlan(recipe, plan);
    }

    /**
     * Deletes the plans for {@code recipe}.
     */
    public void deleteRecipe(Recipe recipe) {
        Optional<List<Plan>> optionalPlans = recipeMap.getPlans(recipe);
        if (optionalPlans.isPresent()) {
            List<Plan> plans = optionalPlans.get();
            plans.stream().forEach(plan -> uniquePlans.deletePlan(plan));
            recipeMap.deleteRecipe(recipe);
        }
    }

    /**
     * Sets {@code target} to {@code editedRecipe} in the list and map.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);
        Optional<List<Plan>> optionalPlans = recipeMap.getPlans(target);
        if (optionalPlans.isPresent()) {
            List<Plan> oldPlans = optionalPlans.get();
            List<Plan> newPlans = new ArrayList<>();
            for (Plan plan : oldPlans) {
                Plan newPlan = plan.setRecipe(editedRecipe);
                newPlans.add(newPlan);
                uniquePlans.setPlan(plan, newPlan);
            }
            recipeMap.setRecipe(target, editedRecipe, newPlans);
        }
    }

    /**
     * Returns optional of the list of plans that uses {@code recipe}.
     * Returns optional empty if there are no recipes.
     */
    public Optional<List<Plan>> getPlans(Recipe recipe) {
        return recipeMap.getPlans(recipe);
    }

    /**
     * Returns true if {@code plan} exists in list.
     */
    public boolean containsPlan(Plan plan) {
        return uniquePlans.containsPlan(plan);
    }


    // ===== Grocery list methods =====

    /**
     * Returns all the ingredients used in the planned recipes.
     */
    public String getGroceryList() {
        return groceryList;
    }

    /**
     * Sets the grocery list to {@code groceryList}.
     */
    public void setGroceryList(String groceryList) {
        this.groceryList = groceryList;
    }


    // ===== Util methods =====

    @Override
    public ObservableList<Plan> getPlannedList() {
        return uniquePlans.asUnmodifiableObservableList();
    }

    @Override
    public PlannedRecipeMap getPlannedMap() {
        return recipeMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedBook // instanceof handles nulls
                && uniquePlans.equals(((PlannedBook) other).uniquePlans));
    }

    @Override
    public int hashCode() {
        return uniquePlans.hashCode();
    }

}
