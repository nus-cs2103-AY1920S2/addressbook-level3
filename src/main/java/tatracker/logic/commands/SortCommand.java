package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in" +
            "all groups of all modules in the TA-Tracker. "
            + "Parameters: "
            + PREFIX_TYPE + "SORT TYPE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "alphabetically";

    public static final String MESSAGE_SUCCESS = "The modules have been sorted.";

    private final String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if(type.equalsIgnoreCase("alphabetically") ||
                type.equalsIgnoreCase("alpha")) {
            model.sortModulesAlphabetically();
        } else {
            model.sortModulesByRating();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
