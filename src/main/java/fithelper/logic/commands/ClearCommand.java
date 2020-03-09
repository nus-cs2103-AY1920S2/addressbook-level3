package fithelper.logic.commands;

import static java.util.Objects.requireNonNull;

import fithelper.model.FitHelper;
import fithelper.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Fit Helper log book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFitHelper(new FitHelper());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
