package NASA.logic.commands;

import static java.util.Objects.requireNonNull;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;

import NASA.commons.core.index.Index;
import NASA.logic.commands.exceptions.CommandException;
import NASA.model.ModelNasa;
import NASA.model.module.Module;
import NASA.model.module.ModuleCode;
import NASA.model.module.ModuleName;

import java.util.ArrayList;

public class DeleteActivityCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Deletes the activities identified by the index numbers used in the displayed activity list.\n "
            + "Parameters:"
            + PREFIX_MODULE + "MODULE CODE"
            + "INDEX" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233"
            + "1 2 3";

    public static final String MESSAGE_SUCCESS = " are deleted successfully!";

    private final Index index;
    private final ModuleCode moduleCode;

    public DeleteActivityCommand(Index index, ModuleCode moduleCode) {
        requireNonNull(index);
        this.index = index;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(ModelNasa model) throws CommandException {
        requireNonNull(model);
        // TODO wait for them to change the model
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteActivityCommand // instanceof handles nulls
                && index.equals(((DeleteActivityCommand) other).index));
    }
}
