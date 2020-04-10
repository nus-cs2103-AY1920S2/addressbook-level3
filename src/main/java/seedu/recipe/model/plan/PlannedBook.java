package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * Wraps all data at the planned-recipe-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
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
     * Creates a PlannedBook using the planned recipes in the {@code toBeCopied}
     */
    public PlannedBook(ReadOnlyPlannedBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /**
     * Replaces the contents of the planned recipe list with {@code plans}.
     * {@code plans} must not contain duplicate recipes on the same day.
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

    // ===== PlannedDate and Recipe level methods =====

/*    *//**
     * Checks whether the planned book contains {@code plannedDate}.
     *//*
    public boolean containsPlan(PlannedDate plannedDate) {
        return plannedDates.(plannedDate);
    }*/


    /**
     * Adds {@code newPlan} into the planned recipes and recipe map.
     * The planned recipe must not exist in the planned book.
     */
    public void addPlan(Recipe recipe, Plan plan) {
        uniquePlans.addPlan(plan);
        recipeMap.addPlan(recipe, plan);
    }

    /**
     * Deletes {@code recipe} in the mapping.
     * And deletes the entire {@code plan} if the {@code recipe} is the last plan on that day.
     * Otherwise, deletes {@code recipe} from the planned recipes in {@code plan}.
     */
    public void deletePlan(Recipe recipe, Plan plan) {
        uniquePlans.deletePlan(plan);
        recipeMap.deletePlan(recipe, plan);
    }

    /**
     * Deletes {@code recipe} from all planned dates in the plan list and map.
     */
    public void deleteRecipe(Recipe recipe) {
        List<Plan> plans = new ArrayList<>(recipeMap.getPlans(recipe));
        plans.stream().forEach(plan -> uniquePlans.deletePlan(plan));
        recipeMap.deleteRecipe(recipe);
    }

    /**
     * Sets {@code target} to {@code editedRecipe} in the list and map.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        if (recipeMap.contains(target)) {
            List<Plan> oldPlans = recipeMap.getPlans(target);
            List<Plan> newPlans = new ArrayList<>();
            for (Plan plan : oldPlans) {
                Plan newPlan = plan.setRecipe(editedRecipe);
                newPlans.add(newPlan);
                uniquePlans.deletePlan(plan);
                uniquePlans.addPlan(newPlan);
            }
            recipeMap.setRecipe(target, editedRecipe, newPlans);
        }
    }

    /**
     * Returns the list of plans that uses {@code recipe}.
     * Returns an empty list of there are no recipes.
     */
    public List<Plan> getPlans(Recipe recipe) {
        return recipeMap.getPlans(recipe);
    }

/*    *//**
     * Marks the {@code recipeCooked} in {@code plannedDate} as completed.
     * Removes {@code recipeCooked} from the mapping as well.
     *//*
    public void completePlan(Recipe recipeCooked, PlannedDate plannedDate) {
        plannedDates.complete(recipeCooked, plannedDate);
        recipeMap.deleteOnePlannedRecipe(recipeCooked, plannedDate);
    }*/ // todo deletePlan once cooked command is complete

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
