package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Deadline;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

/**
 * Command to delete deadline.
 */
public class DeleteDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "del-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the activities identified by the index numbers used in the displayed deadline list.\n"
        + "Parameters: " + "INDEX (must be a positive integer) " + PREFIX_MODULE + "MODULE CODE\n"
        + "Example: " + COMMAND_WORD + " 1 " + PREFIX_MODULE + "CS3233";

    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "%d. %s is successfully deleted!";

    public static final String MESSAGE_FAILURE = "Deadline indicated does not exist!";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module does not exist!";

    private final Index index;
    private final ModuleCode moduleCode;

    public DeleteDeadlineCommand(Index index, ModuleCode moduleCode) {
        this.index = index;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        }

        try {
            Module module = model.getModule(moduleCode);
            Deadline deadline = module.getFilteredDeadlineList().get(index.getZeroBased());
            model.removeDeadline(moduleCode, deadline);
            return new CommandResult(String.format(MESSAGE_DELETE_DEADLINE_SUCCESS,
                    index.getOneBased(), deadline.getName().toString()));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }


    }

    /**
     * Returns true if both DeleteDeadlineCommand has the same index and module code.
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        if (!(other instanceof DeleteDeadlineCommand)) {
            return false;
        }

        DeleteDeadlineCommand command = (DeleteDeadlineCommand) other;
        return moduleCode.equals(((DeleteDeadlineCommand) other).moduleCode)
            && index.equals(((DeleteDeadlineCommand) other).index);
    }
}
