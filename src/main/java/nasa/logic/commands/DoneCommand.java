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
 * Sets an activity to done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sets index of deadline in module to being done or completed.\n"
        + "Parameters: " + "INDEX " + PREFIX_MODULE + "MODULE CODE\n"
        + "Example " + COMMAND_WORD + " 2 " + PREFIX_MODULE + "CS2030";

    public static final String MESSAGE_SUCCESS = "Deadline set to done!";

    public static final String MESSAGE_ACTIVITY_NOT_FOUND =
        "Activity not found in module";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module not found!";

    public static final String MESSAGE_ACTIVITY_ALREADY_DONE = "Deadline already set to done!";

    private Index index;
    private ModuleCode moduleCode;

    public DoneCommand(Index index, ModuleCode moduleCode) {
        requireNonNull(index);
        requireNonNull(moduleCode);
        this.index = index;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        } else {
            // get module and check if the activity index exist
            Module module = model.getModule(moduleCode);
            if (index.getZeroBased() > module.getDeadlineList().getActivityList().size()) {
                throw new CommandException(MESSAGE_ACTIVITY_NOT_FOUND);
            }

            // check if activity already done
            Deadline deadline = module.getFilteredDeadlineList().get(index.getZeroBased());
            model.updateHistory("done" + model.currentUiLocation());
            model.updateSchedule();
            if (deadline.isDone()) {
                throw new CommandException(MESSAGE_ACTIVITY_ALREADY_DONE);
            } else {
                Deadline deadlineCopy = new Deadline(deadline.getName(), deadline.getDateCreated(),
                    deadline.getNote(), deadline.getPriority(), deadline.getDueDate(), true);
                deadlineCopy.setSchedule(deadline.getSchedule());
                model.setDeadline(moduleCode, deadline, deadlineCopy);
                return new CommandResult(String.format(MESSAGE_SUCCESS, deadline));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DoneCommand // instanceof handles nulls
            && index.equals(((DoneCommand) other).index)
            && moduleCode.equals(moduleCode)); // state check
    }
}
