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
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;
import tatracker.logic.parser.Prefixes;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;

/**
 * Deletes a group identified using it's group code.
 */
public class EditGroupCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.GROUP,
            CommandWords.EDIT_MODEL,
            "Edits the group identified by the group code.",
            List.of(MODULE, GROUP),
            List.of(NEWGROUP, NEWTYPE), // TODO: new type not needed?
            MODULE, GROUP, NEWGROUP, NEWTYPE
    );

    public static final String COMMAND_WORD = CommandWords.GROUP + " " + CommandWords.EDIT_MODEL;

    public static final List<Prefix> PARAMETERS = List.of(MODULE, GROUP);
    public static final List<Prefix> OPTIONALS = List.of(NEWGROUP, NEWTYPE); // TODO: new type not needed?

    public static final String INFO = "Edits the group identified by the group code.";
    public static final String USAGE = PrefixDictionary.getPrefixesWithInfo(PARAMETERS, OPTIONALS);
    public static final String EXAMPLE = PrefixDictionary.getPrefixesWithExamples(MODULE, GROUP, NEWGROUP, NEWTYPE);

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the  group identified by the group code.\n"
            + "Parameters: " + MODULE + " MODULE_CODE " + Prefixes.GROUP + "GROUP_CODE"
            + NEWTYPE + "NEW GROUP TYPE  " + NEWGROUP + "NEW GROUP NAME "
            + "Example: " + COMMAND_WORD + " " + MODULE + "CS2013T " + Prefixes.GROUP + "G03 "
            + NEWTYPE + "lab " + NEWGROUP + "G05";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Edited Group: %1$s";
    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    public static final int FIRST_GROUP_INDEX = 0;
    public static final int FIRST_MODULE_INDEX = 0;

    private final Group group;
    private final Module targetModule;
    private final String newGroupCode;
    private final GroupType newGroupType;

    public EditGroupCommand(Group group, Module module, String newGroupCode, GroupType newGroupType) {
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

        if (!(other instanceof DeleteGroupCommand)) {
            return false; // instanceof handles nulls
        }

        EditGroupCommand otherCommand = (EditGroupCommand) other;
        return group.equals(otherCommand.group)
                && targetModule.equals(otherCommand.targetModule);
    }
}
