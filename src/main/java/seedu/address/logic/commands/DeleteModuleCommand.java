package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Deletes a module identified using it's module code.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "module";
    public static final String DELETE_MODEL = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + DELETE_MODEL
            + ": Deletes the student identified by the index number used in the displayed student list.\n"
            + "Parameters: MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + DELETE_MODEL + " CS2013T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with this module code.";

    private final String moduleCode;

    public DeleteModuleCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module module = new Module(moduleCode, "");
        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module moduleToDelete = model.getModule(module);
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && moduleCode.equals(((DeleteModuleCommand) other).moduleCode)); // state check
    }
}

