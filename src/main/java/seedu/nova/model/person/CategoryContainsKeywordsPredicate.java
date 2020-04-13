package seedu.nova.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Category} matches any of the keywords given.
 */
public class CategoryContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public CategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keywords -> person.getCategory().iterator().next().toString().contains(keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CategoryContainsKeywordsPredicate) other).keywords)); // state check
    }

}

