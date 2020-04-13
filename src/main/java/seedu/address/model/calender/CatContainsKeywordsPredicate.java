package seedu.address.model.calender;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class CatContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public CatContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getCategory(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CatContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CatContainsKeywordsPredicate) other).keywords)); // state check
    }

}
