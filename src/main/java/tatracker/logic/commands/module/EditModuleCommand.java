package tatracker.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.CommandWords.EDIT_MODEL;
import static tatracker.logic.commands.CommandWords.MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NAME;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Edits a module identified using it's module code.
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = MODULE + " " + EDIT_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the module identified by the module code.\n"
            + "Parameters: " + PREFIX_MODULE + " MODULE_CODE " + PREFIX_NAME + "NEW NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULE + "CS2013T "
            + PREFIX_NAME + "Software Engineering ";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    private static final int FIRST_GROUP_INDEX = 0;

    private final Module targetModule;
    private final String newName;

    public EditModuleCommand(Module module, String newName) {
        this.targetModule = module;
        this.newName = newName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module actualModule = model.getModule(targetModule.getIdentifier());
        actualModule.setName(newName);

        model.showAllModules();
        model.updateFilteredGroupList(actualModule.getIdentifier());

        if (model.getFilteredGroupList().isEmpty()) {
            model.setFilteredStudentList();
        } else {
            model.setFilteredStudentList(actualModule.getIdentifier(), FIRST_GROUP_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, actualModule));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof EditModuleCommand)) {
            return false; // instanceof handles nulls
        }

        EditModuleCommand otherCommand = (EditModuleCommand) other;
        return targetModule.equals(otherCommand.targetModule);
    }
}
