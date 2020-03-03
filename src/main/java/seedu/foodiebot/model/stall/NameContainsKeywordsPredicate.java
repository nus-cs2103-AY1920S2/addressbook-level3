package seedu.foodiebot.model.stall;

import java.util.List;
import java.util.function.Predicate;

import seedu.foodiebot.commons.util.StringUtil;
import seedu.foodiebot.model.canteen.Stall;

/** Tests that a {@code Block}'s {@code Name} matches any of the keywords given. */
public class NameContainsKeywordsPredicate implements Predicate<Stall> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Stall stall) {
        return keywords.stream()
            .anyMatch(
                keyword ->
                    StringUtil.containsWordIgnoreCase(
                        stall.getCanteenName().replaceAll("\\s+", ""), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                        && keywords.equals((
                                (NameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
