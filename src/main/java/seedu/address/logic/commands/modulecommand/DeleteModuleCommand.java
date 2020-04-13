package seedu.address.logic.commands.modulecommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES_TAKEN;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Deletes a NUS module identified using given module code from the module book.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "moduleDel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows for deleting modules "
            + "Parameters: MODULE CODE "
            + "Example: " + COMMAND_WORD + " CS2103T ";

    public static final String MESSAGE_SUCCESS = "Module deleted ";

    private final ModuleCode targetModuleCode;

    /**
     * Creates an AddModuleCommand to add the specified {@code NusModule}
     */
    public DeleteModuleCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        targetModuleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModuleCode)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_MODULE);
        }

        model.deleteModule(targetModuleCode);
        model.updateModulesListTaken(PREDICATE_SHOW_ALL_MODULES_TAKEN);
        return new CommandResult(MESSAGE_SUCCESS + targetModuleCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && targetModuleCode.equals(((DeleteModuleCommand) other).targetModuleCode));
    }
}
