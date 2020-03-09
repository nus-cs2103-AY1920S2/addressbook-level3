package NASA.logic.commands;

import NASA.commons.core.Messages;
import NASA.logic.commands.exceptions.CommandException;
import NASA.model.ModelNasa;

import static java.util.Objects.requireNonNull;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment lab tutorial";

    @Override
    public CommandResult execute(ModelNasa model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }
}
