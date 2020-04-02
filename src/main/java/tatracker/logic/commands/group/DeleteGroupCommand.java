package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 * Deletes a group identified using it's group code.
 */
public class DeleteGroupCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.GROUP,
            CommandWords.DELETE_MODEL,
            "Deletes the group identified by the group code.",
            List.of(MODULE, GROUP),
            List.of(),
            MODULE, GROUP
    );

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    private static final int FIRST_GROUP_INDEX = 0;

    private final String group;
    private final String targetModule;

    public DeleteGroupCommand(String groupCode, String moduleCode) {
        this.group = groupCode;
        this.targetModule = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (!model.hasGroup(group, targetModule)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        model.deleteGroup(group, targetModule);

        model.updateFilteredGroupList(targetModule);

        if (model.getFilteredGroupList().isEmpty()) {
            model.setFilteredStudentList();
        } else {
            model.setFilteredStudentList(targetModule, FIRST_GROUP_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, group), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof DeleteGroupCommand)) {
            return false; // instanceof handles nulls
        }

        DeleteGroupCommand otherCommand = (DeleteGroupCommand) other;
        return group.equals(otherCommand.group)
                && targetModule.equals(otherCommand.targetModule);
    }
}
