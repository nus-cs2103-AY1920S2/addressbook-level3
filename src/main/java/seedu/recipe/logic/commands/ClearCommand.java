package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recipe.model.Model;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.plan.PlannedBook;

/**
 * Clears the recipe book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Recipe book and planned recipes have been cleared!";
    private final CommandType commandType = CommandType.MAIN;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRecipeBook(new RecipeBook());
        model.setPlannedBook(new PlannedBook());
        model.commitBook(commandType);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
