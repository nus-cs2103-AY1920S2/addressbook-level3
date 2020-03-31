package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.model.Model;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.plan.PlannedBook;

public class ClearPlanCommand extends Command {
    public static final String COMMAND_WORD = "clearplan";
    public static final String MESSAGE_SUCCESS = "Planned recipes have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPlannedBook(new PlannedBook());

        model.commitRecipeBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
