package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

//@@author kester-ng
/**
 * Command to delete event command.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "del-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the event identified by the index numbers used in the displayed event list.\n"
        + "Parameters: " + "INDEX (must be a positive integer) " + PREFIX_MODULE + "MODULE CODE\n"
        + "Example: " + COMMAND_WORD + " 1 " + PREFIX_MODULE + "CS3233";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "%d. %s is successfully deleted!";

    public static final String MESSAGE_FAILURE = "Event indicated does not exist!";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module does not exist!";

    private Index index;
    private ModuleCode moduleCode;

    public DeleteEventCommand(Index index, ModuleCode moduleCode) {
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
            Event event = module.getFilteredEventList().get(index.getZeroBased());
            model.removeEvent(moduleCode, event);
            return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, index.getOneBased(),
                    event.getName().toString()));
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    /**
     * Returns true if both DeleteDeadlineCommand has the same index and module code.
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand command = (DeleteEventCommand) other;
        return moduleCode.equals(((DeleteEventCommand) other).moduleCode)
            && index.equals(((DeleteEventCommand) other).index);
    }
}
