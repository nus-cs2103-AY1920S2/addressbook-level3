package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HOME;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_BY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_ORDER;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.model.entry.Type.FOOD;
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

    public static final String BY = " by ";
    public static final String ASCENDING_ORDER = " in ascending order\n";
    public static final String DESCENDING_ORDER = " in descending order\n";
    public static final String MESSAGE_COMMIT = "Sort the entry list and reminder list.";

    private final Type sortType;
    private final SortBy sortBy;
    private final boolean isAscendingSort;

    public SortCommand(Type type, SortBy order, boolean isAscending) {
        requireNonNull(order);
        sortType = type;
        sortBy = order;
        isAscendingSort = isAscending;
    }

    public String getSortBy() {
        return sortBy.getValue();
    }

    @Override
    public CommandResult execute(Model model) throws IllegalValueException {
        requireNonNull(model);
        String feedback = "";
        if (this.sortType == null) {
            model.sortFilteredEntryList(sortBy, isAscendingSort);
            feedback = Messages.MESSAGE_BOTH_ENTRY_LIST_SORTED + BY + sortBy.getValue();
        } else {
            if (FOOD.equalsIgnoreCase(sortType.getValue())) {
                model.sortFilteredFoodEntryList(sortBy, isAscendingSort);
                feedback = Messages.MESSAGE_FOOD_ENTRY_LIST_SORTED + BY + sortBy.getValue();
            } else {
                model.sortFilteredSportsEntryList(sortBy, isAscendingSort);
                feedback = Messages.MESSAGE_SPORTS_ENTRY_LIST_SORTED + BY + sortBy.getValue();
            }
        }
        feedback = editFeedbackBasedOnSortOrder(feedback);
        model.commit(MESSAGE_COMMIT);
        return new CommandResult(feedback, HOME, false);
    }

    /**
     * Appends information to feedback to user based on sorting order.
     *
     * @param feedback feedback to user
     * @return edited feedback
     */
    private String editFeedbackBasedOnSortOrder(String feedback) {
        String copy = feedback;
        if (isAscendingSort) {
            copy += ASCENDING_ORDER;
        } else {
            copy += DESCENDING_ORDER;
        }
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SortCommand) {
            SortCommand other = (SortCommand) obj;
            boolean isSameCheckType;
            if (this.sortType == null) {
                isSameCheckType = (other.sortType == null);
            } else {
                isSameCheckType = this.sortType.equals(other.sortType);
            }
            return isSameCheckType && other.sortBy.equals(this.sortBy)
                    && other.isAscendingSort == this.isAscendingSort;
        } else {
            return false;
        }
    }
}
