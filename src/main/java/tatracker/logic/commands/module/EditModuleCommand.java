//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.MODULE_NEW_NAME;

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
 * Edits a module identified using it's module code.
 */
public class EditModuleCommand extends Command {

    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.MODULE,
            CommandWords.EDIT_MODEL,
            "Edits the module with the given module code",
            List.of(MODULE, MODULE_NEW_NAME),
            List.of(),
            MODULE, MODULE_NEW_NAME
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited module: %s";
    private static final int FIRST_GROUP_INDEX = 0;

    private final String targetModule;
    private final String newName;

    public EditModuleCommand(String module, String newName) {
        this.targetModule = module;
        this.newName = newName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (newName.isBlank()) {
            throw new CommandException(Module.CONSTRAINTS_MODULE_NAME);
        }
        Module actualModule = model.getModule(targetModule);
        actualModule.setName(newName);

        model.showAllModules();
        model.updateFilteredGroupList(actualModule.getIdentifier());

        if (model.getFilteredGroupList() == null || model.getFilteredGroupList().isEmpty()) {
            model.setFilteredStudentList();
        } else {
            model.setFilteredStudentList(actualModule.getIdentifier(), FIRST_GROUP_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, actualModule.getIdentifier()),
                Action.GOTO_STUDENT);
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
        return targetModule.equals(otherCommand.targetModule) && newName.equals(otherCommand.newName);
    }
}
