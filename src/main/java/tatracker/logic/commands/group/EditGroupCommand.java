//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
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

    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.GROUP,
            CommandWords.EDIT_MODEL,
            "Edits the group with the given group code",
            List.of(MODULE, GROUP),
            List.of(NEWGROUP, NEWTYPE), // TODO: new type not needed?
            MODULE, GROUP, NEWGROUP, NEWTYPE
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited group: %s [%s]";
    public static final String MESSAGE_EDIT_GROUP_FAILURE = "There is already a group with the group code"
            + " that you are editing with.";

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

        if (newGroupCode.equals(group.getIdentifier()) && newGroupType == null) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        if (newGroupCode.isBlank()) {
            throw new CommandException(Group.CONSTRAINTS_GROUP_CODE);
        }

        if (!newGroupCode.equals(group.getIdentifier()) && actualModule.hasGroup(new Group(newGroupCode))) {
            throw new CommandException(MESSAGE_EDIT_GROUP_FAILURE);
        }
        Group editedGroup = actualModule.getGroup(group.getIdentifier());
        editedGroup.setIdentifier(newGroupCode);

        if (newGroupType != null) {
            editedGroup.setGroupType(newGroupType);
        }

        model.updateFilteredGroupList(actualModule.getIdentifier());

        if (model.getFilteredGroupList() == null || model.getFilteredGroupList().isEmpty()) {
            model.setFilteredStudentList();
        } else {
            model.updateFilteredStudentList(editedGroup.getIdentifier(), actualModule.getIdentifier());
        }

        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, targetModule, editedGroup.getIdentifier()),
                Action.GOTO_STUDENT);
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
