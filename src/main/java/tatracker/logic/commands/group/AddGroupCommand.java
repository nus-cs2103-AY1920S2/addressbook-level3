package tatracker.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.CommandWords.ADD_MODEL;
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
 * Adds a group to the TA-Tracker.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = GROUP + " " + ADD_MODEL;

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a group into TA-Tracker. "
            + "Parameters: "
            + PREFIX_GROUP + "GROUP CODE "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TYPE + "GROUP TYPE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "G06 "
            + PREFIX_MODULE + "CS2100 "
            + PREFIX_TYPE + "lab";

    public static final String MESSAGE_SUCCESS = "New Group added: %s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the TA-Tracker";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";

    private final Group toAdd;
    private final Module targetModule;

    /**
    * Creates an addGroupCommand
    */

    public AddGroupCommand(Group group, Module module) {
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

        Module actualModule = model.getModule(targetModule.getIdentifier());

        if (actualModule.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        actualModule.addGroup(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
