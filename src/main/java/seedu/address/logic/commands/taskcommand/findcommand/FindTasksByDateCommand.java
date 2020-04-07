package seedu.address.logic.commands.taskcommand.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.calender.Task;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class FindTasksByDateCommand extends FindTasksCommand {
    public static final String MESSAGE_SUCCESS = "Tasks found: ";

    private final String targetDate;

    public FindTasksByDateCommand(String targetDate) {
        requireNonNull(targetDate);
        this.targetDate = targetDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Task> tasks = model.findTasksByDate(targetDate);
        //ui part

        return new CommandResult(MESSAGE_SUCCESS + " " + tasks);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTasksByDateCommand // instanceof handles nulls
                && targetDate.equals(((FindTasksByDateCommand) other).targetDate));
    }
}

