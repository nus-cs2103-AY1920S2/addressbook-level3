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
            "Sorts all students in all modules and groups inside TA-Tracker.",
            List.of(SORT_TYPE),
            List.of(),
            SORT_TYPE
    );

    public static final String MESSAGE_SUCCESS = "The modules have been sorted.";

    protected final SortType type;

    public SortCommand(SortType type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch(type) {
        case ALPHABETIC:
            model.sortModulesAlphabetically();
            break;
        case MATRIC:
            model.sortModulesByMatricNumber();
            break;
        case RATING_ASC:
            model.sortModulesByRatingAscending();
            break;
        case RATING_DESC:
            model.sortModulesByRatingDescending();
            break;
        default:
            throw new CommandException(SortType.MESSAGE_CONSTRAINTS);
        }

        model.setDefaultStudentViewList();

        return new CommandResult(MESSAGE_SUCCESS, Action.GOTO_STUDENT);
    }
}
