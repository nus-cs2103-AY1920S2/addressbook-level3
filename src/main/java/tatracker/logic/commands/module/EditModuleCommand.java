package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.CommandWords.EDIT_MODEL;
import static tatracker.logic.commands.CommandWords.MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NAME;
import static tatracker.logic.parser.CliSyntax.PREFIX_NEWTYPE;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;

/**
 * Deletes a group identified using it's group code.
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

    private final Module module;
    private final Module targetModule;
    private final String newGroupCode;
    private final GroupType newGroupType;

    public EditModuleCommand(Group group, Module module, String newGroupCode, GroupType newGroupType) {
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

        Module actualModule = model.getModule(targetModule.getIdentifier());

        if (!actualModule.hasGroup(group)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
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

        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, editedGroup));
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
