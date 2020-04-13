package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.ingredient.exceptions.IncompatibleIngredientException;
import seedu.address.model.recipe.Recipe;

/**
 * Adds an ingredient to a recipe.
 */
public class RecipeAddIngredientCommand extends RecipeAddCommand {

    public static final String MESSAGE_SUCCESS = "New ingredient added for %2$s: %1$s";
    public static final String MESSAGE_INCOMPATIBLE_UNITS = "%1$s has different units in %2$s";

    private final Index index;
    private final Ingredient toAdd;

    /**
     * Creates a RecipeAddIngredientCommand to add the specified {@code Ingredient} to the recipe
     */
    public RecipeAddIngredientCommand(Index index, Ingredient toAdd) {
        requireAllNonNull(index, toAdd);
        this.index = index;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    RecipeAddCommand.MESSAGE_USAGE));
        }

        assert index.getZeroBased() < lastShownList.size();
        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());
        UniqueIngredientList ingredients = new UniqueIngredientList();
        ingredients.setIngredients(recipeToEdit.getIngredients());

        try {
            ingredients.add(toAdd);

            EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
            editRecipeDescriptor.setIngredients(ingredients.asUnmodifiableObservableList());
            Recipe editedRecipe = EditRecipeDescriptor.createEditedRecipe(recipeToEdit, editRecipeDescriptor);

            model.setCookbookRecipe(recipeToEdit, editedRecipe);
            model.updateFilteredCookbookRecipeList(PREDICATE_SHOW_ALL_RECIPES);

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, editedRecipe.getName()));
        } catch (IncompatibleIngredientException e) {
            throw new CommandException(String.format(MESSAGE_INCOMPATIBLE_UNITS, toAdd, recipeToEdit.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeAddIngredientCommand // instanceof handles nulls
                && toAdd.equals(((RecipeAddIngredientCommand) other).toAdd));
    }
}
