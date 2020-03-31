package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.TYPE;

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
import tatracker.model.module.Module;

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

    public static final String COMMAND_WORD = CommandWords.GROUP + " " + CommandWords.DELETE_MODEL;

    public static final List<Prefix> PARAMETERS = List.of(MODULE, GROUP);

    public static final String INFO = "Deletes the group identified by the group code.";
    public static final String USAGE = PrefixDictionary.getPrefixesWithInfo(PARAMETERS);
    public static final String EXAMPLE = PrefixDictionary.getPrefixesWithExamples(MODULE, GROUP);

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the  group identified by the group code.\n"
            + "Parameters: " + MODULE + " MODULE_CODE " + Prefixes.GROUP + " GROUP_CODE"
            + TYPE + " GROUP_TYPE\n"
            + "Example: " + COMMAND_WORD + " " + MODULE + "CS2013T " + Prefixes.GROUP + "G03 "
            + TYPE + "lab";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    public static final int FIRST_GROUP_INDEX = 0;
    public static final int FIRST_MODULE_INDEX = 0;

    private final Group group;
    private final Module targetModule;

    public DeleteGroupCommand(Group group, Module module) {
        this.group = group;
        this.targetModule = module;
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

        Group deletedGroup = actualModule.getGroup(group.getIdentifier());
        actualModule.deleteGroup(deletedGroup);

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

        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup));
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
