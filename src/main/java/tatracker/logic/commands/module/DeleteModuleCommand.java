package tatracker.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Deletes a module identified using it's module code.
 */
public class DeleteModuleCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.MODULE,
            CommandWords.DELETE_MODEL,
            "Deletes the module identified by the module code.",
            List.of(MODULE),
            List.of(),
            MODULE
    );

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with this module code.";

    private final String module;

    public DeleteModuleCommand(String module) {
        this.module = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module moduleToDelete = model.getModule(module);
        model.deleteModule(moduleToDelete);

        model.setDefaultStudentViewList();

        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && module.equals(((DeleteModuleCommand) other).module)); // state check
    }
}
