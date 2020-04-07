package seedu.address.model.restaurant;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Restaurant}'s {@code Location} matches any of the keywords given.
 */
public class LocationContainsKeywordsPredicate implements Predicate<Restaurant> {

    private final List<String> keywords;

    public LocationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Getter method for the number of keywords in the predicate.
     * @return Number of keywords in the Predicate
     */
    public int size() {
        return keywords.size();
    }

    @Override
    public boolean test(Restaurant restaurant) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(restaurant.getLocation().fullLocation, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.restaurant.LocationContainsKeywordsPredicate
                && keywords.equals(((seedu.address.model.restaurant.LocationContainsKeywordsPredicate) other)
                .keywords)); // state check
    }
}
