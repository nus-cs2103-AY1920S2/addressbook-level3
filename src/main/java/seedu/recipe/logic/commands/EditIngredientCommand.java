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
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Ingredient;
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

    public static final String MESSAGE_EDIT_INGREDIENTS_SUCCESS = "Successfully edited ingredient(s) in %1$s!";
    public static final String MESSAGE_NO_SUCH_INGREDIENT = "%1$s is either not %2$s ingredient "
            + "or it does not exist in %3$s!";

    private final Index index;
    private final EditRecipeDescriptor editRecipeDescriptor;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public EditIngredientCommand(Index index, EditRecipeDescriptor editRecipeDescriptor) {
        this.index = index;
        this.editRecipeDescriptor = editRecipeDescriptor;
        this.commandType = CommandType.MAIN;
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
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);

        return new CommandResult(String.format(MESSAGE_EDIT_INGREDIENTS_SUCCESS, recipeToEdit.getName().toString()));
    }

    /**
     * Edits the quantity of the specified {@code Grain} ingredient(s) in the current list of grains.
     * If the specified grain ingredient(s) do not exist in the current list, or if no grain ingredient(s) were
     * specified but "ig/" prefix was present, CommandException is thrown.
     */
    private void updateGrainsList(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        Set<Grain> editedGrainSet;
        Set<Grain> grainsToEdit = editRecipeDescriptor.getGrains().orElse(null);
        if (grainsToEdit != null && !grainsToEdit.isEmpty()) { // Case: The suffix of "ig/" is not empty
            Set<Grain> currentGrainsList = new TreeSet<>(recipeToEdit.getGrains());
            for (Grain grain : grainsToEdit) {
                if (!currentGrainsList.contains(grain)) {
                    throw new CommandException(String.format(MESSAGE_NO_SUCH_INGREDIENT,
                            grain.getIngredientName(), "a grain", recipeToEdit.getName().toString()));
                }
                currentGrainsList.remove(grain);
                currentGrainsList.add(grain);
            }
            editedGrainSet = currentGrainsList;
        } else if (grainsToEdit != null) { // Case: The suffix of "ig/" is empty
            throw new CommandException(Ingredient.MESSAGE_MISSING_FIELD);
        } else { // Case: The user did not input the grains prefix, "ig/"
            editedGrainSet = recipeToEdit.getGrains();
        }
        editRecipeDescriptor.setGrains(editedGrainSet);
    }

    /**
     * Edits the quantity of the specified {@code Vegetable} ingredient(s) in the current list of vegetables.
     * If the specified vegetable ingredient(s) do not exist in the current list, or if no vegetable ingredient(s) were
     * specified but "iv/" prefix was present, CommandException is thrown.
     */
    private void updateVegetablesList(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        Set<Vegetable> editedVegetableSet;
        Set<Vegetable> vegetablesToDelete = editRecipeDescriptor.getVegetables().orElse(null);
        if (vegetablesToDelete != null && !vegetablesToDelete.isEmpty()) { // Case: The suffix of "iv/" is not empty
            Set<Vegetable> currentVegetablesList = new TreeSet<>(recipeToEdit.getVegetables());
            for (Vegetable vegetable : vegetablesToDelete) {
                if (!currentVegetablesList.contains(vegetable)) {
                    throw new CommandException(String.format(MESSAGE_NO_SUCH_INGREDIENT,
                            vegetable.getIngredientName(), "a vegetable", recipeToEdit.getName().toString()));
                }
                currentVegetablesList.remove(vegetable);
                currentVegetablesList.add(vegetable);
            }
            editedVegetableSet = currentVegetablesList;
        } else if (vegetablesToDelete != null) { // Case: The suffix of "iv/" is empty
            throw new CommandException(Ingredient.MESSAGE_MISSING_FIELD);
        } else { // Case: The user did not input the vegetables prefix, "iv/"
            editedVegetableSet = recipeToEdit.getVegetables();
        }
        editRecipeDescriptor.setVegetables(editedVegetableSet);
    }

    /**
     * Edits the quantity of the specified {@code Protein} ingredient(s) in the current list of proteins.
     * If the specified protein ingredient(s) do not exist in the current list, or if no protein ingredient(s) were
     * specified but "ip/" prefix was present, CommandException is thrown.
     */
    private void updateProteinsList(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        Set<Protein> editedProteinSet;
        Set<Protein> proteinsToDelete = editRecipeDescriptor.getProteins().orElse(null);
        if (proteinsToDelete != null && !proteinsToDelete.isEmpty()) { // Case: The suffix of "ip/" is not empty
            Set<Protein> currentProteinsList = new TreeSet<>(recipeToEdit.getProteins());
            for (Protein protein : proteinsToDelete) {
                if (!currentProteinsList.contains(protein)) {
                    throw new CommandException(String.format(MESSAGE_NO_SUCH_INGREDIENT,
                            protein.getIngredientName(), "a protein", recipeToEdit.getName().toString()));
                }
                currentProteinsList.remove(protein);
                currentProteinsList.add(protein);
            }
            editedProteinSet = currentProteinsList;
        } else if (proteinsToDelete != null) { // Case: The suffix of "ip/" is empty
            throw new CommandException(Ingredient.MESSAGE_MISSING_FIELD);
        } else { // Case: The user did not input the proteins prefix, "ip/"
            editedProteinSet = recipeToEdit.getProteins();
        }
        editRecipeDescriptor.setProteins(editedProteinSet);
    }

    /**
     * Edits the quantity of the specified {@code Fruit} ingredient(s) in the current list of fruits.
     * If the specified fruit ingredient(s) do not exist in the current list, or if no fruit ingredient(s) were
     * specified but "if/" prefix was present, CommandException is thrown.
     */
    private void updateFruitsList(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        Set<Fruit> editedFruitSet;
        Set<Fruit> fruitsToDelete = editRecipeDescriptor.getFruits().orElse(null);
        if (fruitsToDelete != null && !fruitsToDelete.isEmpty()) { // Case: The suffix of "if/" is not empty
            Set<Fruit> currentFruitsList = new TreeSet<>(recipeToEdit.getFruits());
            for (Fruit fruit : fruitsToDelete) {
                if (!currentFruitsList.contains(fruit)) {
                    throw new CommandException(String.format(MESSAGE_NO_SUCH_INGREDIENT,
                            fruit.getIngredientName(), "a fruit", recipeToEdit.getName().toString()));
                }
                currentFruitsList.remove(fruit);
                currentFruitsList.add(fruit);
            }
            editedFruitSet = currentFruitsList;
        } else if (fruitsToDelete != null) { // Case: The suffix of "if/" is empty
            throw new CommandException(Ingredient.MESSAGE_MISSING_FIELD);
        } else { // Case: The user did not input the fruits prefix, "if/"
            editedFruitSet = recipeToEdit.getFruits();
        }
        editRecipeDescriptor.setFruits(editedFruitSet);
    }

    /**
     * Edits the quantity of the specified {@code Other} ingredient(s) in the current list of other ingredients.
     * If the specified others ingredient(s) do not exist in the current list, or if no others ingredient(s) were
     * specified but "io/" prefix was present, CommandException is thrown.
     */
    private void updateOthersList(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        Set<Other> editedOtherSet;
        Set<Other> othersToDelete = editRecipeDescriptor.getOthers().orElse(null);
        if (othersToDelete != null && !othersToDelete.isEmpty()) { // Case: The suffix of "io/" is not empty
            Set<Other> currentOthersList = new TreeSet<>(recipeToEdit.getOthers());
            for (Other other : othersToDelete) {
                if (!currentOthersList.contains(other)) {
                    throw new CommandException(String.format(MESSAGE_NO_SUCH_INGREDIENT,
                            other.getIngredientName(), "an 'other'", recipeToEdit.getName().toString()));
                }
                currentOthersList.remove(other);
                currentOthersList.add(other);
            }
            editedOtherSet = currentOthersList;
        } else if (othersToDelete != null) { // Case: The suffix of "io/" is empty
            throw new CommandException(Ingredient.MESSAGE_MISSING_FIELD);
        } else { // Case: The user did not input the others prefix, "io/"
            editedOtherSet = recipeToEdit.getOthers();
        }
        editRecipeDescriptor.setOthers(editedOtherSet);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditIngredientCommand // instanceof handles nulls
                && index.equals(((EditIngredientCommand) other).index)
                && editRecipeDescriptor.equals(((EditIngredientCommand) other).editRecipeDescriptor)); // state check
    }
}
