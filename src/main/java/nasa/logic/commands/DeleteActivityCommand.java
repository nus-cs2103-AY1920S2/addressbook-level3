package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.ModuleCode;

/**
 * Deletes an activity from a module list.
 */
public class DeleteActivityCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Deletes the activities identified by the index numbers used in the displayed activity list.\n "
            + "Parameters:"
            + PREFIX_MODULE + "MODULE CODE"
            + "INDEX" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233"
            + "1 2 3";

    public static final String MESSAGE_SUCCESS = " are deleted successfully!";

    private final Index index;
    private final ModuleCode moduleCode;

    /**
     * Creates a DeleteActivityCommand to delete the activity at
     * {@code index} from the specified {@code moduleCode} list.
     * @param index index of the activity to be deleted
     * @param moduleCode module of the activity that it is associated with
     */
    public DeleteActivityCommand(Index index, ModuleCode moduleCode) {
        requireNonNull(index);
        this.index = index;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
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
