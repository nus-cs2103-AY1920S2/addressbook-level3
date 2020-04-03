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
import java.util.concurrent.atomic.AtomicInteger;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
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

    public static final String MESSAGE_DELETE_INGREDIENTS_SUCCESS = "Successfully deleted ingredient(s) from %1$s!";
    public static final String MESSAGE_DELETING_TOO_MANY_INGREDIENTS = "Attempting to delete all ingredients or "
            + "more ingredients than %1$s has. Ingredients list cannot be empty!";
    public static final String MESSAGE_NO_SUCH_INGREDIENT = "%1$s is either not %2$s ingredient "
            + "or it does not exist in %3$s!";

    private final Index index;
    private final EditRecipeDescriptor editRecipeDescriptor;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public DeleteIngredientCommand(Index index, EditRecipeDescriptor editRecipeDescriptor) {
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

        int numberOfCurrentIngredients = recipeToEdit.getTotalNumberOfIngredients();
        if (getNumberOfIngredientsToDelete(recipeToEdit, editRecipeDescriptor) >= numberOfCurrentIngredients) {
            throw new CommandException(
                    String.format(MESSAGE_DELETING_TOO_MANY_INGREDIENTS, recipeToEdit.getName().toString()));
        }

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

        return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENTS_SUCCESS, recipeToEdit.getName().toString()));
    }

    /**
     * Calculates and returns the number of ingredients that is to be deleted.
     */
    private int getNumberOfIngredientsToDelete(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        AtomicInteger numberOfIngredientsToDelete = new AtomicInteger(0);
        // If the ingredient prefix (eg. ig/) is present, check if suffix is empty ("").
        // If it is empty, all ingredients from that type will be deleted.
        editRecipeDescriptor.getGrains()
                .ifPresent(grainSet -> numberOfIngredientsToDelete.addAndGet(grainSet.size() > 0
                        ? grainSet.size()
                        : recipeToEdit.getGrains().size()));
        editRecipeDescriptor.getVegetables()
                .ifPresent(vegetableSet -> numberOfIngredientsToDelete.addAndGet(vegetableSet.size() > 0
                        ? vegetableSet.size()
                        : recipeToEdit.getVegetables().size()));
        editRecipeDescriptor.getProteins()
                .ifPresent(proteinSet -> numberOfIngredientsToDelete.addAndGet(proteinSet.size() > 0
                        ? proteinSet.size()
                        : recipeToEdit.getProteins().size()));
        editRecipeDescriptor.getFruits()
                .ifPresent(fruitSet -> numberOfIngredientsToDelete.addAndGet(fruitSet.size() > 0
                        ? fruitSet.size()
                        : recipeToEdit.getFruits().size()));
        editRecipeDescriptor.getOthers()
                .ifPresent(otherSet -> numberOfIngredientsToDelete.addAndGet(otherSet.size() > 0
                        ? otherSet.size()
                        : recipeToEdit.getOthers().size()));

        return numberOfIngredientsToDelete.get();
    }

    /**
     * Removes the specified {@code Grain} ingredient(s) from the current list of grains.
     * If no grain ingredient(s) were specified but "ig/" prefix was present, all grains will be removed.
     * If the specified grain ingredient(s) do not exist in the current list, CommandException is thrown.
     */
    private void updateGrainsList(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor)
            throws CommandException {
        requireNonNull(recipeToEdit);
        requireNonNull(editRecipeDescriptor);

        Set<Grain> editedGrainSet;
        Set<Grain> grainsToDelete = editRecipeDescriptor.getGrains().orElse(null);
        if (grainsToDelete != null && !grainsToDelete.isEmpty()) { // Case: The suffix of "ig/" is not empty
            Set<Grain> currentGrainsList = new TreeSet<>(recipeToEdit.getGrains());
            for (Grain grain : grainsToDelete) {
                if (!currentGrainsList.contains(grain)) {
                    throw new CommandException(String.format(MESSAGE_NO_SUCH_INGREDIENT,
                            grain, "a grain", recipeToEdit.getName().toString()));
                }
                currentGrainsList.remove(grain);
            }
            editedGrainSet = currentGrainsList;
        } else if (grainsToDelete != null) { // Case: The suffix of "ig/" is empty
            editedGrainSet = new TreeSet<>();
        } else { // Case: The user did not input the grains prefix, "ig/"
            editedGrainSet = recipeToEdit.getGrains();
        }
        editRecipeDescriptor.setGrains(editedGrainSet);
    }

    /**
     * Removes the specified {@code Vegetable} ingredient(s) from the current list of vegetables.
     * If no vegetable ingredient(s) were specified but "iv/" prefix was present, all ingredients will be removed.
     * If the specified vegetable ingredient(s) do not exist in the current list, CommandException is thrown.
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
                            vegetable, "a vegetable", recipeToEdit.getName().toString()));
                }
                currentVegetablesList.remove(vegetable);
            }
            editedVegetableSet = currentVegetablesList;
        } else if (vegetablesToDelete != null) { // Case: The suffix of "iv/" is empty
            editedVegetableSet = new TreeSet<>();
        } else { // Case: The user did not input the vegetables prefix, "iv/"
            editedVegetableSet = recipeToEdit.getVegetables();
        }
        editRecipeDescriptor.setVegetables(editedVegetableSet);
    }

    /**
     * Removes the specified {@code Protein} ingredient(s) from the current list of proteins.
     * If no protein ingredient(s) were specified but "ip/" prefix was present, all proteins will be removed.
     * If the specified protein ingredient(s) do not exist in the current list, CommandException is thrown.
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
                            protein, "a protein", recipeToEdit.getName().toString()));
                }
                currentProteinsList.remove(protein);
            }
            editedProteinSet = currentProteinsList;
        } else if (proteinsToDelete != null) { // Case: The suffix of "ip/" is empty
            editedProteinSet = new TreeSet<>();
        } else { // Case: The user did not input the proteins prefix, "ip/"
            editedProteinSet = recipeToEdit.getProteins();
        }
        editRecipeDescriptor.setProteins(editedProteinSet);
    }

    /**
     * Removes the specified {@code Fruit} ingredient(s) from the current list of fruits.
     * If no fruit ingredient(s) were specified but "if/" prefix was present, all fruits will be removed.
     * If the specified fruit ingredient(s) do not exist in the current list, CommandException is thrown.
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
                            fruit, "a fruit", recipeToEdit.getName().toString()));
                }
                currentFruitsList.remove(fruit);
            }
            editedFruitSet = currentFruitsList;
        } else if (fruitsToDelete != null) { // Case: The suffix of "if/" is empty
            editedFruitSet = new TreeSet<>();
        } else { // Case: The user did not input the fruits prefix, "if/"
            editedFruitSet = recipeToEdit.getFruits();
        }
        editRecipeDescriptor.setFruits(editedFruitSet);
    }

    /**
     * Removes the specified {@code Other} ingredient(s) from the current list of other ingredients.
     * If no others ingredient(s) were specified but "io/" prefix was present, all others ingredients will be removed.
     * If the specified other ingredient(s) do not exist in the current list, CommandException is thrown.
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
                            other, "an 'other'", recipeToEdit.getName().toString()));
                }
                currentOthersList.remove(other);
            }
            editedOtherSet = currentOthersList;
        } else if (othersToDelete != null) { // Case: The suffix of "io/" is empty
            editedOtherSet = new TreeSet<>();
        } else { // Case: The user did not input the others prefix, "io/"
            editedOtherSet = recipeToEdit.getOthers();
        }
        editRecipeDescriptor.setOthers(editedOtherSet);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientCommand // instanceof handles nulls
                && index.equals(((DeleteIngredientCommand) other).index)
                && editRecipeDescriptor.equals(((DeleteIngredientCommand) other).editRecipeDescriptor)); // state check
    }
}
