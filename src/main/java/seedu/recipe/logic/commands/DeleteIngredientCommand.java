package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.recipe.logic.commands.EditCommand.createEditedRecipe;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Vegetable;

/**
 * Deletes ingredient(s) from an existing recipe in the recipe book.
 */
public class DeleteIngredientCommand extends Command {

    public static final String COMMAND_WORD = "deleteingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes ingredient(s) from an existing recipe in the "
            + "recipe book.\n"
            + "Parameters: [INDEX of recipe] "
            + "[" + PREFIX_INGREDIENT_GRAIN + "GRAIN]... "
            + "[" + PREFIX_INGREDIENT_VEGE + "VEGETABLE]... "
            + "[" + PREFIX_INGREDIENT_PROTEIN + "PROTEIN]... "
            + "[" + PREFIX_INGREDIENT_FRUIT + "FRUIT]... "
            + "[" + PREFIX_INGREDIENT_OTHER + "OTHER]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INGREDIENT_VEGE + "Tomato "
            + PREFIX_INGREDIENT_VEGE + "Lettuce "
            + PREFIX_INGREDIENT_OTHER + "Honeydew";

    public static final String MESSAGE_ADD_INGREDIENTS_SUCCESS = "Successfully deleted ingredient(s) from %1$s!";

    private final Index index;
    private final EditCommand.EditRecipeDescriptor editRecipeDescriptor;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public DeleteIngredientCommand(Index index, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
        this.index = index;
        this.editRecipeDescriptor = editRecipeDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());

        updateGrainsList(recipeToEdit, editRecipeDescriptor);
        updateVegetablesList(recipeToEdit, editRecipeDescriptor);
        updateProteinsList(recipeToEdit, editRecipeDescriptor);
        updateFruitsList(recipeToEdit, editRecipeDescriptor);
        updateOthersList(recipeToEdit, editRecipeDescriptor);

        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.commitRecipeBook();

        return new CommandResult(String.format(MESSAGE_ADD_INGREDIENTS_SUCCESS, recipeToEdit.getName().toString()));
    }

    /**
     * Removes the specified {@code Grain} ingredient(s) from the current list of grains.
     * If the specified grain ingredient(s) do not exist in the current list, nothing happens.
     */
    public void updateGrainsList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
        if (editRecipeDescriptor.getGrains().isPresent()) {
            Set<Grain> grainsToDelete = new TreeSet<>(editRecipeDescriptor.getGrains().get());
            Set<Grain> currentGrainsList = new TreeSet<>(recipeToEdit.getGrains());
            currentGrainsList.removeIf(grainsToDelete::contains);
            editRecipeDescriptor.setGrains(currentGrainsList);
        } else {
            editRecipeDescriptor.setGrains(recipeToEdit.getGrains());
        }
    }

    /**
     * Removes the specified {@code Vegetable} ingredient(s) from the current list of vegetables.
     * If the specified vegetable ingredient(s) do not exist in the current list, nothing happens.
     */
    public void updateVegetablesList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
        if (editRecipeDescriptor.getVegetables().isPresent()) {
            Set<Vegetable> vegetablesToDelete = new TreeSet<>(editRecipeDescriptor.getVegetables().get());
            Set<Vegetable> currentVegetablesList = new TreeSet<>(recipeToEdit.getVegetables());
            currentVegetablesList.removeIf(vegetablesToDelete::contains);
            editRecipeDescriptor.setVegetables(currentVegetablesList);
        } else {
            editRecipeDescriptor.setVegetables(recipeToEdit.getVegetables());
        }
    }

    /**
     * Removes the specified {@code Protein} ingredient(s) from the current list of proteins.
     * If the specified protein ingredient(s) do not exist in the current list, nothing happens.
     */
    public void updateProteinsList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
        if (editRecipeDescriptor.getProteins().isPresent()) {
            Set<Protein> proteinsToDelete = new TreeSet<>(editRecipeDescriptor.getProteins().get());
            Set<Protein> currentProteinsList = new TreeSet<>(recipeToEdit.getProteins());
            currentProteinsList.removeIf(proteinsToDelete::contains);
            editRecipeDescriptor.setProteins(currentProteinsList);
        } else {
            editRecipeDescriptor.setProteins(recipeToEdit.getProteins());
        }
    }

    /**
     * Removes the specified {@code Fruit} ingredient(s) from the current list of fruits.
     * If the specified fruit ingredient(s) do not exist in the current list, nothing happens.
     */
    public void updateFruitsList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
        if (editRecipeDescriptor.getFruits().isPresent()) {
            Set<Fruit> fruitsToDelete = new TreeSet<>(editRecipeDescriptor.getFruits().get());
            Set<Fruit> currentFruitsList = new TreeSet<>(recipeToEdit.getFruits());
            currentFruitsList.removeIf(fruitsToDelete::contains);
            editRecipeDescriptor.setFruits(currentFruitsList);
        } else {
            editRecipeDescriptor.setFruits(recipeToEdit.getFruits());
        }
    }

    /**
     * Removes the specified {@code Other} ingredient(s) from the current list of other ingredients.
     * If the specified other ingredient(s) do not exist in the current list, nothing happens.
     */
    public void updateOthersList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
        if (editRecipeDescriptor.getOthers().isPresent()) {
            Set<Other> othersToDelete = new TreeSet<>(editRecipeDescriptor.getOthers().get());
            Set<Other> currentOthersList = new TreeSet<>(recipeToEdit.getOthers());
            currentOthersList.removeIf(othersToDelete::contains);
            editRecipeDescriptor.setOthers(currentOthersList);
        } else {
            editRecipeDescriptor.setOthers(recipeToEdit.getOthers());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientCommand // instanceof handles nulls
                && index.equals(((DeleteIngredientCommand) other).index)
                && editRecipeDescriptor.equals(((DeleteIngredientCommand) other).editRecipeDescriptor)); // state check
    }
}
