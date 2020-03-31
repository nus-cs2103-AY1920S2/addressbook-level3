package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 * Command to sort all students in all groups of all modules.
 */
public class SortCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SORT,
            CommandWords.SORT_ALL,
            "Sorts students in all module groups in the TA-Tracker.",
            List.of(SORT_TYPE),
            List.of(),
            SORT_TYPE
    );

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

        return new CommandResult(MESSAGE_SUCCESS, Action.GOTO_STUDENT);
    }
}
