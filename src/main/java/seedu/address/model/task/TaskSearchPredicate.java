package seedu.address.model.task;

import static seedu.address.logic.parser.task.TaskSearchCommandParser.DATE_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.MONTH_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.YEAR_STRING;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s {@code Description} matches any of the keywords given.
 */
public class TaskSearchPredicate implements Predicate<Task> {

    private final HashMap<String, Integer> keywords;

    public TaskSearchPredicate(HashMap<String, Integer> keywords) {
        assert (keywords.size() > 0) : "There should be at least one parameter provided for searching";
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {

        if (task.getTaskDateTime().isEmpty()) { // not a ScheduledTask
            return false;
        }

        if (keywords.containsKey(DATE_STRING)) {
            if (!task.getTaskDateTime().get().isDateOnThisDay(keywords.get(DATE_STRING))) {
                return false;
            }
        }

        if (keywords.containsKey(MONTH_STRING)) {
            if (!task.getTaskDateTime().get().isDateOnThisMonth(keywords.get(MONTH_STRING))) {
                return false;
            }
        }

        if (keywords.containsKey(YEAR_STRING)) {
            if (!task.getTaskDateTime().get().isDateOnThisYear(keywords.get(YEAR_STRING))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskSearchPredicate // instanceof handles nulls
                && keywords.equals(((TaskSearchPredicate) other).keywords)); // state check
    }

}
