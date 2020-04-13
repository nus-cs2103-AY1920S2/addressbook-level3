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
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.ui.tab.Tab;

/**
 * Adds step(s) to an existing recipe in the recipe book.
 */
public class AddStepCommand extends Command {

    public static final String COMMAND_WORD = "addstep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds more steps to an existing recipe.\n"
            + "Parameters: [recipe index] "
            + "[" + PREFIX_STEP + "step] <" + PREFIX_STEP + "next step>...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STEP + "New step "
            + PREFIX_STEP + "Another new step";

    public static final String MESSAGE_ADD_STEPS_SUCCESS = "Successfully added step(s) to %1$s!";

    private final Tab recipesTab = Tab.RECIPES;
    private final Index index;
    private final List<Step> newSteps;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param newSteps is the list of steps that the user wishes to add to the recipe
     */
    public AddStepCommand(Index index, List<Step> newSteps) {
        this.index = index;
        this.newSteps = newSteps;
        this.commandType = CommandType.MAIN;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        EditCommand.EditRecipeDescriptor editRecipeDescriptor = new EditCommand.EditRecipeDescriptor();
        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());

        List<Step> updatedStepsList = new ArrayList<>(recipeToEdit.getSteps());
        updatedStepsList.addAll(newSteps);
        editRecipeDescriptor.setSteps(updatedStepsList);

        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType, recipesTab);

        String finalMessage = String.format(MESSAGE_ADD_STEPS_SUCCESS, recipeToEdit.getName().toString());
        return new CommandResult(finalMessage, false, false, recipesTab, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddStepCommand // instanceof handles nulls
                && index.equals(((AddStepCommand) other).index)
                && newSteps.equals(((AddStepCommand) other).newSteps)); // state check
    }
}
