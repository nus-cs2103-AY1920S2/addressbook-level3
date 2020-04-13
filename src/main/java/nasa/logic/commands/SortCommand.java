package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import nasa.model.Model;
import nasa.model.module.SortMethod;

/* @@author don-tay */
/**
 * Lists all modules and their activity lists to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all deadlines and events according to the "
            + "the method specified.\nParameters: SORT_METHOD\n"
            + "Example: " + COMMAND_WORD + " " + "priority";;

    public static final String MESSAGE_SUCCESS = "Sorted all deadlines and events successfully.";

    private final SortMethod sortMethod;

    public SortCommand(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortActivityList(sortMethod);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortMethod.equals(((SortCommand) other).sortMethod)); // state check
    }
}
