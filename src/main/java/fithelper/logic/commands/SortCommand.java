package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HOME;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_BY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_ORDER;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.Messages;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.Model;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Type;

/**
 * Sort the entry list based on some criterion (calorie OR time).
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts food/sports entry list or both lists "
            + "by either calorie or time.\n"
            + "Parameters: ["
            + PREFIX_TYPE + "TYPE (default is both)] "
            + PREFIX_SORT_BY + "SORT_BY (cal/time)"
            + PREFIX_SORT_ORDER + "[ORDER (a/d, default is d)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "sports "
            + PREFIX_SORT_BY + "cal "
            + PREFIX_SORT_ORDER + "d";

    private final Type sortType;
    private final SortBy sortBy;
    private final boolean isAscendingSort;

    public SortCommand(Type type, SortBy order, boolean isAscending) {
        sortType = type;
        sortBy = order;
        isAscendingSort = isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws IllegalValueException {
        requireNonNull(model);
        String feedback = "";
        if (this.sortType == null) {
            model.sortFilteredEntryList(sortBy, isAscendingSort);
            feedback = Messages.MESSAGE_BOTH_ENTRY_LIST_SORTED + " by " + sortBy.getValue();
        } else {
            if ("food".equalsIgnoreCase(sortType.getValue())) {
                model.sortFilteredFoodEntryList(sortBy, isAscendingSort);
                feedback = Messages.MESSAGE_FOOD_ENTRY_LIST_SORTED + " by " + sortBy.getValue();
            } else {
                model.sortFilteredSportsEntryList(sortBy, isAscendingSort);
                feedback = Messages.MESSAGE_SPORTS_ENTRY_LIST_SORTED + " by " + sortBy.getValue();
            }
        }
        feedback = editFeedbackBasedOnSortOrder(feedback);
        return new CommandResult(feedback, HOME, false);
    }

    private String editFeedbackBasedOnSortOrder(String feedback) {
        if (isAscendingSort) {
            feedback += " in ascending order (i.e. earlier entry OR entry with lower calorie value first)";
        } else {
            feedback += " in descending order (i.e. later entry OR entry with higher calorie value first)";
        }
        return feedback;
    }
}
