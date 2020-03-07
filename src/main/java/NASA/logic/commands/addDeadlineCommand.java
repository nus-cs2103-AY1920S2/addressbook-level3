package NASA.logic.commands;

import static java.util.Objects.requireNonNull;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static NASA.logic.parser.CliSyntax.PREFIX_DATE;
import static NASA.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static NASA.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static NASA.logic.parser.CliSyntax.PREFIX_NOTE;

import NASA.logic.commands.exceptions.CommandException;
import NASA.model.Model;
import NASA.model.module.Module;
import NASA.model.activity.Deadline;

public class AddDeadlineCommand extends Command {

    public final String COMMAND_WORD = "deadline";

    public final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deadline to the module's activity list. "
            + "Parameters:"
            + PREFIX_MODULE + "MODULE CODE"
            + PREFIX_DATE + "DATE"
            + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME"
            + PREFIX_PRIORITY + "PRIORITY"
            + PREFIX_NOTE + "NOTE" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233"
            + PREFIX_DATE + "2020-03-20"
            + PREFIX_ACTIVITY_NAME + "SEA Group Programming Assignment"
            + PREFIX_PRIORITY + "1"
            + PREFIX_NOTE + "Focus on computational geometry and DP.";

    public static final String MESSAGE_SUCCESS = "New deadline activity added!";
    public static final String MESSAGE_DUPLICATED_ACTIVITY = "This activity already exist in the module's activity list!";

    private final Deadline toAdd;

    public AddDeadlineCommand(Deadline deadline) {
        requireNonNull(deadline);
        toAdd = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && toAdd.equals(((AddDeadlineCommand) other).toAdd));
    }
}
