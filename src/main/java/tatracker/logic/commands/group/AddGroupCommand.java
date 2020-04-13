//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_GROUP;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.TYPE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Adds a group to the TA-Tracker.
 */
public class AddGroupCommand extends Command {

    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.GROUP,
            CommandWords.ADD_MODEL,
            "Adds a group into TA-Tracker",
            List.of(GROUP, MODULE, TYPE),
            List.of(),
            GROUP, MODULE, TYPE
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_ADD_GROUP_SUCCESS = "New group added: %s [%s]";

    private final Group toAdd;
    private final String targetModule;

    /**
    * Creates an addGroupCommand
    */

    public AddGroupCommand(Group group, String module) {
        requireNonNull(group);
        requireNonNull(module);
        toAdd = group;
        targetModule = module;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module actualModule = model.getModule(targetModule);

        if (toAdd.getIdentifier().isBlank()) {
            throw new CommandException(Group.CONSTRAINTS_GROUP_CODE);
        }
        if (actualModule.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        actualModule.addGroup(toAdd);

        model.updateFilteredGroupList(actualModule.getIdentifier());
        model.updateFilteredStudentList(toAdd.getIdentifier(), actualModule.getIdentifier());

        return new CommandResult(String.format(MESSAGE_ADD_GROUP_SUCCESS, targetModule, toAdd.getIdentifier()),
                Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof AddGroupCommand)) {
            return false; // instanceof handles nulls
        }

        AddGroupCommand otherCommand = (AddGroupCommand) other;
        return toAdd.equals(otherCommand.toAdd)
                && targetModule.equals(otherCommand.targetModule);
    }
}
