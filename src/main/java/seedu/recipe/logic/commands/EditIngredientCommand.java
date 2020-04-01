package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.recipe.logic.commands.EditCommand.createEditedRecipe;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
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
 * Edits ingredient(s) quantity from an existing recipe in the recipe book.
 */
public class EditIngredientCommand extends Command {

    public static final String COMMAND_WORD = "editingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits ingredient(s) quantity from an existing recipe "
            + "in the recipe book.\n"
            + "Parameters: [INDEX of recipe] "
            + "[" + PREFIX_INGREDIENT_GRAIN + "GRAIN]... "
            + "[" + PREFIX_INGREDIENT_VEGE + "VEGETABLE]... "
            + "[" + PREFIX_INGREDIENT_PROTEIN + "PROTEIN]... "
            + "[" + PREFIX_INGREDIENT_FRUIT + "FRUIT]... "
            + "[" + PREFIX_INGREDIENT_OTHER + "OTHER]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INGREDIENT_VEGE + "100g, Tomato "
            + PREFIX_INGREDIENT_VEGE + "100g, Lettuce "
            + PREFIX_INGREDIENT_OTHER + "50g, Honeydew";

    public static final String MESSAGE_ADD_INGREDIENTS_SUCCESS = "Successfully edited ingredient(s) in %1$s!";
    public static final String MESSAGE_NO_SUCH_INGREDIENT = "%1$s is either not %2$s ingredient "
            + "or it does not exist in %3$s!";

    private final Index index;
    private final EditCommand.EditRecipeDescriptor editRecipeDescriptor;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public EditIngredientCommand(Index index, EditCommand.EditRecipeDescriptor editRecipeDescriptor) {
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
        model.setRecipeInPlans(recipeToEdit, editedRecipe);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitRecipeBook();

        return new CommandResult(String.format(MESSAGE_ADD_INGREDIENTS_SUCCESS, recipeToEdit.getName().toString()));
    }

    /**
     * Edits the quantity of the specified {@code Grain} ingredient(s) in the current list of grains.
     * If the specified grain ingredient(s) do not exist in the current list, CommandException is thrown.
     */
    public void updateGrainsList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        if (editRecipeDescriptor.getGrains().isPresent()) {
            Set<Grain> grainsToEdit = new TreeSet<>(editRecipeDescriptor.getGrains().get());
            Set<Grain> currentGrainsList = new TreeSet<>(recipeToEdit.getGrains());
            for (Grain grain : grainsToEdit) {
                if (currentGrainsList.contains(grain)) {
                    currentGrainsList.remove(grain);
                    currentGrainsList.add(grain);
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_NO_SUCH_INGREDIENT,
                                    grain, "a grain", recipeToEdit.getName().toString()));
                }
            }
            editRecipeDescriptor.setGrains(currentGrainsList);
        } else {
            editRecipeDescriptor.setGrains(recipeToEdit.getGrains());
        }
    }

    /**
     * Edits the quantity of the specified {@code Vegetable} ingredient(s) in the current list of vegetables.
     * If the specified vegetable ingredient(s) do not exist in the current list, CommandException is thrown.
     */
    public void updateVegetablesList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        if (editRecipeDescriptor.getVegetables().isPresent()) {
            Set<Vegetable> vegetablesToEdit = new TreeSet<>(editRecipeDescriptor.getVegetables().get());
            Set<Vegetable> currentVegetablesList = new TreeSet<>(recipeToEdit.getVegetables());
            for (Vegetable vegetable : vegetablesToEdit) {
                if (currentVegetablesList.contains(vegetable)) {
                    currentVegetablesList.remove(vegetable);
                    currentVegetablesList.add(vegetable);
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_NO_SUCH_INGREDIENT,
                                    vegetable, "a vegetable", recipeToEdit.getName().toString()));
                }
            }
            editRecipeDescriptor.setVegetables(currentVegetablesList);
        } else {
            editRecipeDescriptor.setVegetables(recipeToEdit.getVegetables());
        }
    }

    /**
     * Edits the quantity of the specified {@code Protein} ingredient(s) in the current list of proteins.
     * If the specified protein ingredient(s) do not exist in the current list, CommandException is thrown.
     */
    public void updateProteinsList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        if (editRecipeDescriptor.getProteins().isPresent()) {
            Set<Protein> proteinsToEdit = new TreeSet<>(editRecipeDescriptor.getProteins().get());
            Set<Protein> currentProteinsList = new TreeSet<>(recipeToEdit.getProteins());
            for (Protein protein : proteinsToEdit) {
                if (currentProteinsList.contains(protein)) {
                    currentProteinsList.remove(protein);
                    currentProteinsList.add(protein);
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_NO_SUCH_INGREDIENT,
                                    protein, "a protein", recipeToEdit.getName().toString()));
                }
            }
            editRecipeDescriptor.setProteins(currentProteinsList);
        } else {
            editRecipeDescriptor.setProteins(recipeToEdit.getProteins());
        }
    }

    /**
     * Edits the quantity of the specified {@code Fruit} ingredient(s) in the current list of fruits.
     * If the specified fruit ingredient(s) do not exist in the current list, CommandException is thrown.
     */
    public void updateFruitsList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        if (editRecipeDescriptor.getFruits().isPresent()) {
            Set<Fruit> fruitsToEdit = new TreeSet<>(editRecipeDescriptor.getFruits().get());
            Set<Fruit> currentFruitsList = new TreeSet<>(recipeToEdit.getFruits());
            for (Fruit fruit : fruitsToEdit) {
                if (currentFruitsList.contains(fruit)) {
                    currentFruitsList.remove(fruit);
                    currentFruitsList.add(fruit);
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_NO_SUCH_INGREDIENT,
                                    fruit, "a fruit", recipeToEdit.getName().toString()));
                }
            }
            editRecipeDescriptor.setFruits(currentFruitsList);
        } else {
            editRecipeDescriptor.setFruits(recipeToEdit.getFruits());
        }
    }

    /**
     * Edits the quantity of the specified {@code Other} ingredient(s) in the current list of other ingredients.
     * If the specified other ingredient(s) do not exist in the current list, CommandException is thrown.
     */
    public void updateOthersList(Recipe recipeToEdit, EditCommand.EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        if (editRecipeDescriptor.getOthers().isPresent()) {
            Set<Other> othersToEdit = new TreeSet<>(editRecipeDescriptor.getOthers().get());
            Set<Other> currentOthersList = new TreeSet<>(recipeToEdit.getOthers());
            for (Other other : othersToEdit) {
                if (currentOthersList.contains(other)) {
                    currentOthersList.remove(other);
                    currentOthersList.add(other);
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_NO_SUCH_INGREDIENT,
                                    other, "an 'other'", recipeToEdit.getName().toString()));
                }
            }
            editRecipeDescriptor.setOthers(currentOthersList);
        } else {
            editRecipeDescriptor.setOthers(recipeToEdit.getOthers());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditIngredientCommand // instanceof handles nulls
                && index.equals(((EditIngredientCommand) other).index)
                && editRecipeDescriptor.equals(((EditIngredientCommand) other).editRecipeDescriptor)); // state check
    }
}
