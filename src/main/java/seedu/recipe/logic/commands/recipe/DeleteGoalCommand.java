package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.commands.recipe.EditCommand.EditRecipeDescriptor;
import static seedu.recipe.logic.commands.recipe.EditCommand.createEditedRecipe;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Deletes an exists goal from a recipe.
 */
public class DeleteGoalCommand extends Command {
    public static final String COMMAND_WORD = "deletegoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a goal from an existing recipe in the "
            + "recipe book.\n"
            + "Parameters: [INDEX of recipe] [goal name]\n"
            + "Example: " + COMMAND_WORD + " 1 Herbivore";

    public static final String MESSAGE_DELETE_GOAL_SUCCESS = "Successfully deleted goal from %1$s!";
    public static final String MESSAGE_INVALID_GOAL_NAME = "Invalid goal name! "
            + "Ensure that the capitalization of goals are the same.";
    public static final String MESSAGE_INVALID_GOAL = "Attempting to remove a non-existent goal.";

    private final Tab recipesTab = Tab.RECIPES;
    private final Index index;
    private final String goal;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit.
     * @param goal is the name of goal user wishes to remove from recipe.
     */
    public DeleteGoalCommand(Index index, String goal) {
        this.index = index;
        this.goal = goal;
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

        Set<Goal> updatedGoalsList = new HashSet<>(recipeToEdit.getGoals());
        if (!Goal.isValidGoalName(this.goal)) {
            throw new CommandException(MESSAGE_INVALID_GOAL_NAME);
        }
        Goal currGoal = new Goal(this.goal);
        if (updatedGoalsList.contains(currGoal)) {
            updatedGoalsList.remove(currGoal);
        } else {
            throw new CommandException(MESSAGE_INVALID_GOAL);
        }

        editRecipeDescriptor.setGoals(updatedGoalsList);

        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType, recipesTab);

        String finalMessage = String.format(MESSAGE_DELETE_GOAL_SUCCESS, recipeToEdit.getName().toString());
        return new CommandResult(finalMessage, false, false, recipesTab, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGoalCommand // instanceof handles nulls
                && index.equals(((DeleteGoalCommand) other).index)
                && goal.equals(((DeleteGoalCommand) other).goal)); // state check
    }
}
