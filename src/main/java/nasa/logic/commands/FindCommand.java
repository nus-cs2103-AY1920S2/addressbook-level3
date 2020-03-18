package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;

/**
 * Represents the command for finding specific activities.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment lab tutorial";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }
}
