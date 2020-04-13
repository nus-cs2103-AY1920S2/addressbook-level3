package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.model.Model;
import seedu.recipe.ui.tab.Tab;

/**
 * Lists all recipes in the recipe book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all recipes";

    private final Tab recipesTab = Tab.RECIPES;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(MESSAGE_SUCCESS, false, false, recipesTab, false);
    }
}
