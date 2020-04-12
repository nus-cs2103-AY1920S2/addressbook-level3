//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;

import java.util.List;

import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 * Command to sort all students in all groups of all modules.
 */
public class SortCommand extends SortGroupCommand {

    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SORT,
            CommandWords.SORT_ALL,
            "Sorts all students in all modules and groups inside TA-Tracker",
            List.of(SORT_TYPE),
            List.of(),
            SORT_TYPE
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_SORT_ALL_SUCCESS = "All students in each module have been sorted";

    public SortCommand(SortType type) {
        super(type);
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

        return new CommandResult(MESSAGE_SORT_ALL_SUCCESS, Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof SortCommand)) {
            return false; // instanceof handles nulls
        }

        SortCommand otherCommand = (SortCommand) other;
        return type.equals(otherCommand.type);
    }

}
