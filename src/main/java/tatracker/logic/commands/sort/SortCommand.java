package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 * Command to sort all students in all groups of all modules.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.SORT + " " + CommandWords.SORT_ALL;

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in"
            + "all groups of all modules in the TA-Tracker. \n"
            + "Parameters: "
            + PREFIX_TYPE + "SORT TYPE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "alphabetically";

    public static final String MESSAGE_SUCCESS = "The modules have been sorted.";
    public static final String MESSAGE_INVALID_SORT = "The only sort types are alphabetical,"
            + "by rating asc, by rating desc and matric.";
    private static final int FIRST_MODULE_INDEX = 0;
    private static final int FIRST_GROUP_INDEX = 0;

    private final String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch(type) {
        case "alphabetically":
        case "alpha":
        case "alphabetical":
            model.sortModulesAlphabetically();
            break;
        case "matric":
            model.sortModulesByMatricNumber();
            break;
        case "rating asc":
            model.sortModulesByRatingAscending();
            break;
        case "rating desc":
            model.sortModulesByRatingDescending();
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_SORT);
        }

        model.setDefaultStudentViewList();

        return new CommandResult(String.format(MESSAGE_SUCCESS), Action.GOTO_STUDENT);
    }
}
