package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.CommandWords.DELETE_MODEL;
import static tatracker.logic.commands.CommandWords.GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Deletes a group identified using it's group code.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = GROUP + " " + DELETE_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the  group identified by the group code.\n"
            + "Parameters: " + PREFIX_MODULE + " MODULE_CODE " + PREFIX_GROUP + " GROUP_CODE"
            + PREFIX_TYPE + " GROUP_TYPE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULE + "CS2013T " + PREFIX_GROUP + "G03 "
            + PREFIX_TYPE + "lab";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";

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
            model.updateGroupList(0);
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
            } else {
                model.updateStudentList(0, 0);
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
