package nasa.logic.commands;
<<<<<<< HEAD:src/main/java/nasa/logic/commands/FindCommand.java
=======

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
>>>>>>> cf98652144e57734b46653f44f8db289a6e30a49:src/main/java/NASA/logic/commands/FindCommand.java

import static java.util.Objects.requireNonNull;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;

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
