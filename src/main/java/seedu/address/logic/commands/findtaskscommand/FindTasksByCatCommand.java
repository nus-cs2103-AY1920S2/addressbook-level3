package seedu.address.logic.commands.findtaskscommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.todolist.Task;

public class FindTasksByCatCommand extends FindTasksCommand {
    public static final String MESSAGE_SUCCESS = "Tasks found: ";

    private final String targetCat;

    public FindTasksByCatCommand(String targetCat) {
        requireNonNull(targetCat);
        this.targetCat = targetCat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Task> tasks = model.findTasksByCat(targetCat);
        //ui part

        return new CommandResult(MESSAGE_SUCCESS + " " + tasks);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTasksByCatCommand // instanceof handles nulls
                && targetCat.equals(((FindTasksByCatCommand) other).targetCat));
    }
}