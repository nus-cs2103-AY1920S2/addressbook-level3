package seedu.eylah.diettracker.model.food;

import java.util.List;
import java.util.function.Predicate;

import seedu.eylah.commons.util.StringUtil;

/**
 * Tests that a {@code Food}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Food> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Food Food) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(Food.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
