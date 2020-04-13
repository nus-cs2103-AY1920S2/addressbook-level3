//@@author aakanksha-rai

package tatracker.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_MODULE;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.MODULE_NAME;

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
 * Adds a module to the TA-Tracker.
 */
public class AddModuleCommand extends Command {

    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.MODULE,
            CommandWords.ADD_MODEL,
            "Adds a module into TA-Tracker",
            List.of(MODULE, MODULE_NAME),
            List.of(),
            MODULE, MODULE_NAME
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_ADD_MODULE_SUCCESS = "New module added: %s";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toAdd.getIdentifier().isBlank()) {
            throw new CommandException(Module.CONSTRAINTS_MODULE_CODE);
        }

        if (toAdd.getName().isBlank()) {
            throw new CommandException(Module.CONSTRAINTS_MODULE_NAME);
        }

        if (model.hasModule(toAdd.getIdentifier())) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);

        model.updateFilteredGroupList(toAdd.getIdentifier());

        model.setFilteredStudentList();

        return new CommandResult(String.format(MESSAGE_ADD_MODULE_SUCCESS, toAdd.getIdentifier()), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
