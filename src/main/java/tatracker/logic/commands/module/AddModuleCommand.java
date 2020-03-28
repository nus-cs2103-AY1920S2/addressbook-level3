package tatracker.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NAME;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Adds a module to the TA-Tracker.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.MODULE + " " + CommandWords.ADD_MODEL;
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds a module to the TA-Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "MODULE NAME "
            + PREFIX_MODULE + "MODULE CODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Introduction to AI "
            + PREFIX_MODULE + "CS3243 ";

    public static final String MESSAGE_SUCCESS = "New Module added: %s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the TA-Tracker";
    public static final int FIRST_GROUP_INDEX = 0;

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        model.updateFilteredGroupList(toAdd.getIdentifier());
        if (model.getFilteredGroupList().isEmpty()) {
            model.setFilteredStudentList();
        } else {
            model.setFilteredStudentList(toAdd.getIdentifier(), FIRST_GROUP_INDEX);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
