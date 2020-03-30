package tatracker.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;
import tatracker.logic.parser.Prefixes;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Deletes a module identified using it's module code.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.MODULE + " " + CommandWords.DELETE_MODEL;

    public static final List<Prefix> PARAMETERS = List.of(MODULE);

    public static final String INFO = "Deletes the module identified by the module code.";
    public static final String USAGE = PrefixDictionary.getPrefixesWithInfo(PARAMETERS);
    public static final String EXAMPLE = PrefixDictionary.getPrefixesWithExamples(MODULE);

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code.\n"
            + "Parameters: " + Prefixes.MODULE + " MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " CS2013T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with this module code.";

    public static final int FIRST_GROUP_INDEX = 0;
    public static final int FIRST_MODULE_INDEX = 0;

    private final Module module;

    public DeleteModuleCommand(Module module) {
        this.module = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module moduleToDelete = model.getModule(module.getIdentifier());
        model.deleteModule(moduleToDelete);
        if (model.getFilteredModuleList().isEmpty()) {
            model.setFilteredGroupList();
            model.setFilteredStudentList();
        } else {
            model.updateGroupList(FIRST_MODULE_INDEX);
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
            } else {
                model.updateStudentList(FIRST_GROUP_INDEX, FIRST_MODULE_INDEX);
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && module.equals(((DeleteModuleCommand) other).module)); // state check
    }
}
