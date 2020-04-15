package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;

/**
 * Tests that a {@code Person}'s {@code Tag}s matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Person> {
    // for sarah's use
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
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
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTagsForPredicate().contains(keyword));
    }

    // what is this equals here for?
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }


}
