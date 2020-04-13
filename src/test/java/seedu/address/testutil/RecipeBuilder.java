package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_RECIPE_NAME = "CARBONARA";
    public static final String DEFAULT_RECIPE_DESCRIPTION = "The best dish sold in NUS.";

    private RecipeName recipeName;
    private RecipeDescription recipeDescription;
    private UniqueIngredientList ingredients;
    private UniqueStepList steps;
    private Set<Tag> tags;

    public RecipeBuilder() {
        recipeName = new RecipeName(DEFAULT_RECIPE_NAME);
        recipeDescription = new RecipeDescription(DEFAULT_RECIPE_DESCRIPTION);
        ingredients = new UniqueIngredientList();
        steps = new UniqueStepList();
        tags = new HashSet<>();
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        this.recipeName = recipeToCopy.getName();
        this.recipeDescription = recipeToCopy.getDescription();
        this.ingredients = new UniqueIngredientList();
        this.ingredients.setIngredients(recipeToCopy.getIngredients());
        this.steps = recipeToCopy.getSteps();
        this.tags = new HashSet<>(recipeToCopy.getTags());
    }

    /**
     * Sets the {@code recipeName} of the {@code Recipe} that we are building.
     * @param recipeName
     */
    public RecipeBuilder withRecipeName(String recipeName) {
        this.recipeName = new RecipeName(recipeName);
        return this;
    }

    /**
     * Sets the {@code recipeDescription} of the {@code Recipe} that we are building.
     * @param recipeDescription
     */
    public RecipeBuilder withRecipeDescription(String recipeDescription) {
        this.recipeDescription = new RecipeDescription(recipeDescription);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Adds the {@code ingredient} to the ingredient list of the recipe.
     * @param ingredient
     */
    public RecipeBuilder withIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    /**
     * Adds the {@code steps} to the steps list of the recipe.
     * @param step
     */
    public RecipeBuilder withStep(Step step) {
        this.steps.add(step);
        return this;
    }

    public Recipe build() {
        return new Recipe(recipeName, recipeDescription, ingredients, steps, tags);
    }

}
