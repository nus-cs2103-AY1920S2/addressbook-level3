package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandWords.DELETE_MODEL;
import static seedu.address.logic.commands.CommandWords.GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.module.Module;

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
    private final String moduleCode;

    public DeleteGroupCommand(Group group, String moduleCode) {
        this.group = group;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module module = new Module(moduleCode, null);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        module = model.getModule(module);

        if (!module.hasGroup(group)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        Group deletedGroup = module.getGroup(group.getIdentifier());
        module.deleteGroup(deletedGroup);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && group.equals(((DeleteGroupCommand) other).group)); // state check
    }
}
