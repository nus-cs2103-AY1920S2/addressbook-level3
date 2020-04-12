package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.RecipeBook;
import seedu.recipe.ui.tab.Tab;

/**
 * Clears the recipe book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Recipe book and planned recipes have been cleared!";
    private final CommandType commandType = CommandType.MAIN;
    private final Tab recipesTab = Tab.RECIPES;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRecipeBook(new RecipeBook());
        model.setPlannedBook(new PlannedBook());
        model.commitBook(commandType, recipesTab);
        return new CommandResult(MESSAGE_SUCCESS, false, false, recipesTab, false);
    }
}
