package cookbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import cookbuddy.model.Model;
import cookbuddy.model.RecipeBook;

/**
 * Clears the recipe book.
 */
public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String MESSAGE_SUCCESS = "The recipe book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRecipeBook(new RecipeBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
