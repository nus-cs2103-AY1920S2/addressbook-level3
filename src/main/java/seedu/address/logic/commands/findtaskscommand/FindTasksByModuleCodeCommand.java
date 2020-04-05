package seedu.address.logic.commands.findtaskscommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;

public class FindTasksByModuleCodeCommand extends FindTasksCommand {
    public static final String MESSAGE_SUCCESS = "Tasks found: ";

    private final ModuleCode targetModule;

    public FindTasksByModuleCodeCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        this.targetModule = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException("Module specified does not exist!");
        }

        List<ModuleTask> tasks = model.getModuleTaskList(targetModule);
        //ui part

        return new CommandResult(MESSAGE_SUCCESS + " " + tasks);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTasksByModuleCodeCommand // instanceof handles nulls
                && targetModule.equals(((FindTasksByModuleCodeCommand) other).targetModule));
    }
}
