package nasa.logic.commands;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.ModuleCode;

/**
 * Repeat an activity for a week, twice a week or monthly.
 */
public class RepeatEventCommand extends Command {

    public static final String COMMAND_WORD = "repeat-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": repeat a event activity.\n"
            + "Example to cancel a repetition: " + COMMAND_WORD + " 1 m/CS3233 r/0\n"
            + "Example for weekly repetition: " + COMMAND_WORD + " 2 m/CS3233 r/1\n"
            + "Example for twice weekly repetition: " + COMMAND_WORD + " 1 m/CS3233 r/2\n"
            + "Example for monthly repetition: " + COMMAND_WORD + " 1 m/CS3233 r/3";

    public static final String MESSAGE_SUCCESS = "Successfully added a repeat!";

    public static final String MESSAGE_FAILURE = "Unsuccessfully added. Please check for correctness in module code or"
            + "activity name.";

    public static final String MESSAGE_NO_MODULE = "No such module, try again with a correct module.";

    private ModuleCode module;
    private Index index;
    private Index schedule;

    public RepeatEventCommand(ModuleCode module, Index index, Index schedule) {
        requireAllNonNull(module, index, schedule);

        this.module = module;
        this.index = index;
        this.schedule = schedule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_NO_MODULE);
        }

        if (model.setEventSchedule(module, index, schedule)) {
            return new CommandResult(String.format("%s for %s %s %s", MESSAGE_SUCCESS, module, index.getZeroBased(),
                    schedule.getZeroBased()));
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RepeatEventCommand
                && module.equals(((RepeatEventCommand) other).module)
                && index.equals(((RepeatEventCommand) other).index)
                && schedule.equals(((RepeatEventCommand) other).schedule));
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", module, index.getZeroBased(), schedule.getZeroBased());
    }
}
