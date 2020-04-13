package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.ingredient.exceptions.IncompatibleIngredientException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.model.ingredient.exceptions.NonPositiveIngredientQuantityException;
import seedu.address.model.recipe.Recipe;

/**
 * Removes an ingredient from a recipe.
 */
public class RecipeRemoveIngredientCommand extends RecipeRemoveCommand {

    public static final String ALL_KEYWORD = "All";
    public static final String MESSAGE_SUCCESS = "%1$s removed from %2$s";
    public static final String MESSAGE_INGREDIENT_QUANTITY_TOO_HIGH = "The quantity specified is too large";
    public static final String MESSAGE_INCOMPATIBLE_UNITS = "%1$s has different units in %2$s";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "%2$s does not contain %1$s";

    private final Index index;
    private final IngredientName ingredientName;
    private final Optional<IngredientQuantity> ingredientQuantity;

    /**
     * Creates a RecipeRemoveIngredientCommand to remove the specified {@code Ingredient} from the recipe
     */
    public RecipeRemoveIngredientCommand(Index index, IngredientName ingredientName,
                                         Optional<IngredientQuantity> ingredientQuantity) {
        requireAllNonNull(index, ingredientName, ingredientQuantity);

        this.index = index;
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    RecipeRemoveCommand.MESSAGE_USAGE));
        }

        assert (index.getZeroBased() < lastShownList.size());
        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());
        UniqueIngredientList ingredients = new UniqueIngredientList();
        ingredients.setIngredients(recipeToEdit.getIngredients());

        try {
            ingredientQuantity.map(x -> new Ingredient(ingredientName, x))
                    .ifPresentOrElse(ingredients::remove, () ->
                        ingredients.remove(ingredientName));

            EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
            editRecipeDescriptor.setIngredients(ingredients.asUnmodifiableObservableList());
            Recipe editedRecipe = EditRecipeDescriptor.createEditedRecipe(recipeToEdit, editRecipeDescriptor);

            model.setCookbookRecipe(recipeToEdit, editedRecipe);
            model.updateFilteredCookbookRecipeList(PREDICATE_SHOW_ALL_RECIPES);

            String ingredientRemoved = ingredientQuantity.map(x -> new Ingredient(ingredientName, x).toString())
                    .orElseGet(() -> ALL_KEYWORD + " " + ingredientName);

            return new CommandResult(String.format(MESSAGE_SUCCESS, ingredientRemoved,
                    recipeToEdit.getName()));

        } catch (IngredientNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND,
                    ingredientName, recipeToEdit.getName()));
        } catch (IncompatibleIngredientException e) {
            throw new CommandException(String.format(MESSAGE_INCOMPATIBLE_UNITS,
                    ingredientName, recipeToEdit.getName()));
        } catch (NonPositiveIngredientQuantityException e) {
            throw new CommandException(String.format(MESSAGE_INGREDIENT_QUANTITY_TOO_HIGH));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeRemoveIngredientCommand // instanceof handles nulls
                && ingredientName.equals(((RecipeRemoveIngredientCommand) other).ingredientName)
                && ingredientQuantity.equals(((RecipeRemoveIngredientCommand) other).ingredientQuantity));
    }
}
