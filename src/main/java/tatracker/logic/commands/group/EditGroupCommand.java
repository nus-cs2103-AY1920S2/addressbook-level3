package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NEWGROUP;
import static tatracker.logic.parser.Prefixes.NEWTYPE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;

/**
 * Edits a group identified using it's group code.
 */
public class EditGroupCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.GROUP,
            CommandWords.EDIT_MODEL,
            "Edits the group with the given group code.",
            List.of(MODULE, GROUP),
            List.of(NEWGROUP, NEWTYPE), // TODO: new type not needed?
            MODULE, GROUP, NEWGROUP, NEWTYPE
    );

    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";
    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    public static final String MESSAGE_DUPLICATE_GROUP = "There is already a group with the group code"
        + " you want to change it to.";

    private final Group group;
    private final String targetModule;
    private final String newGroupCode;
    private final GroupType newGroupType;

    public EditGroupCommand(Group group, String module, String newGroupCode, GroupType newGroupType) {
        this.group = group;
        this.targetModule = module;
        this.newGroupCode = newGroupCode;
        this.newGroupType = newGroupType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module actualModule = model.getModule(targetModule);

        if (!actualModule.hasGroup(group)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        if (!newGroupCode.equals(group.getIdentifier())) {
            if (actualModule.hasGroup(group)) {
                throw new CommandException(MESSAGE_DUPLICATE_GROUP);
            }
        }
        Group editedGroup = actualModule.getGroup(group.getIdentifier());
        editedGroup.setIdentifier(newGroupCode);

        if (newGroupType != null) {
            editedGroup.setGroupType(newGroupType);
        }

        model.updateFilteredGroupList(actualModule.getIdentifier());

        if (model.getFilteredGroupList().isEmpty()) {
            model.setFilteredStudentList();
        } else {
            model.updateFilteredStudentList(editedGroup.getIdentifier(), actualModule.getIdentifier());
        }

        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, editedGroup), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof EditGroupCommand)) {
            return false; // instanceof handles nulls
        }

        EditGroupCommand otherCommand = (EditGroupCommand) other;
        return group.equals(otherCommand.group)
                && targetModule.equals(otherCommand.targetModule);
    }
}
