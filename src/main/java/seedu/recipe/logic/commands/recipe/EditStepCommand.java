package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.commands.recipe.EditCommand.createEditedRecipe;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.ArrayList;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.commands.recipe.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.ui.tab.Tab;

/**
 * Edits a step in an existing recipe in the recipe book.
 */
public class EditStepCommand extends Command {

    public static final String COMMAND_WORD = "editstep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the specified step in an existing recipe.\n"
            + "Parameters: [recipe index] [step index] "
            + "[" + PREFIX_STEP + "new step]\n"
            + "Example: " + COMMAND_WORD + " 3 4 "
            + PREFIX_STEP + "Edited new step (replaces step 4 of recipe 3 with 'Edited new step')";

    public static final String MESSAGE_EDIT_STEPS_SUCCESS = "Successfully edited step %1$d in %2$s!";
    public static final String MESSAGE_INVALID_STEP_INDEX = "Attempting to edit a non-existent step";

    private final Tab recipesTab = Tab.RECIPES;
    private final Index index;
    private final int stepNumber;
    private final Step editedStep;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param stepNumber is the index of the step that the user wishes to edit
     * @param editedStep is the new step that the user wishes to replace the old step with
     */
    public EditStepCommand(Index index, int stepNumber, Step editedStep) {
        this.index = index;
        this.stepNumber = stepNumber;
        this.editedStep = editedStep;
        this.commandType = CommandType.MAIN;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());

        List<Step> updatedStepsList = new ArrayList<>(recipeToEdit.getSteps());
        if (!canEditStep(updatedStepsList, stepNumber)) {
            throw new CommandException(MESSAGE_INVALID_STEP_INDEX);
        }
        updatedStepsList.set(stepNumber, editedStep);
        editRecipeDescriptor.setSteps(updatedStepsList);

        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType, recipesTab);

        String finalMessage = String.format(MESSAGE_EDIT_STEPS_SUCCESS, stepNumber + 1,
                recipeToEdit.getName().toString());
        return new CommandResult(finalMessage, false, false, recipesTab, false);
    }

    /**
     * Checks if the step number that the user wishes to edit exists within the steps list.
     */
    private boolean canEditStep(List<Step> updatedStepsList, int stepIndex) {
        return stepIndex <= updatedStepsList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditStepCommand // instanceof handles nulls
                && index.equals(((EditStepCommand) other).index)
                && stepNumber == ((EditStepCommand) other).stepNumber
                && editedStep.equals(((EditStepCommand) other).editedStep)); // state check
    }
}
