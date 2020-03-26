package nasa.logic.commands;

import nasa.commons.core.index.Index;
import nasa.model.Model;
import nasa.model.activity.Name;
import nasa.model.module.ModuleCode;

/**
 * Repeat an activity for a week, twice a week or monthly.
 */
public class RepeatCommand extends Command {

    public static final String COMMAND_WORD = "repeat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": repeat a module activity.\n"
            + "Example for weekly repetition: " + COMMAND_WORD + " m/CS3233 a/SEA Group Programming Assignment r/1\n"
            + "Example for twice weekly repetition: " + COMMAND_WORD + " m/CS3233 a/Lab r/2\n"
            + "Example for monthly repetition: " + COMMAND_WORD + " m/CS3233 a/Lab r/3";

    public static final String SHOWING_HELP_MESSAGE = "set repeated.";

    private ModuleCode module;
    private Name activity;
    private Index schedule;

    public RepeatCommand(ModuleCode module, Name activity, Index schedule) {
        this.module = module;
        this.activity = activity;
        this.schedule = schedule;
    }

    @Override
    public CommandResult execute(Model model) {
        model.setSchedule(module, activity, schedule);
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RepeatCommand
                && module.equals(((RepeatCommand) other).module)
                && activity.equals(((RepeatCommand) other).activity)
                && schedule.equals(((RepeatCommand) other).schedule));
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", module, activity, schedule.getZeroBased());
    }
}
